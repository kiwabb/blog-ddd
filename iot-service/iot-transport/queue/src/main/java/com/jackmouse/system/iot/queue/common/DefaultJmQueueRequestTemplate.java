package com.jackmouse.system.iot.queue.common;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.jackmouse.system.iot.message.queue.TopicPartitionInfo;
import com.jackmouse.system.iot.queue.*;
import com.jackmouse.system.iot.stats.MessagesStats;
import com.jackmouse.system.utils.JackmouseThreadFactory;
import com.jackmouse.system.utils.JmStopWatch;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName DefaultJmQueueRequestTemplate
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/2 10:57
 * @Version 1.0
 **/
@Slf4j
public class DefaultJmQueueRequestTemplate<Request extends JmQueueMsg, Response extends JmQueueMsg>
        extends AbstractJmQueueTemplate implements JmQueueRequestTemplate<Request, Response> {
    final ConcurrentHashMap<UUID, DefaultJmQueueRequestTemplate.ResponseMetaData<Response>> pendingRequests = new ConcurrentHashMap<>();
    private final TbQueueAdmin queueAdmin;
    private final JmQueueProducer<Request> requestTemplate;
    private final JmQueueConsumer<Response> responseTemplate;
    volatile boolean stopped = false;

    final boolean internalExecutor;
    final ExecutorService executor;
    final long maxRequestTimeoutNs;
    final long maxRequestTimeout;
    final long maxPendingRequests;
    final long pollInterval;
    long nextCleanupNs = 0L;
    private final Lock cleanerLock = new ReentrantLock();

    private MessagesStats messagesStats;

    @Builder
    public DefaultJmQueueRequestTemplate(TbQueueAdmin queueAdmin,
                                         JmQueueProducer<Request> requestTemplate,
                                         JmQueueConsumer<Response> responseTemplate,
                                         long maxRequestTimeout,
                                         long maxPendingRequests,
                                         long pollInterval,
                                         ExecutorService executor) {

        this.queueAdmin = queueAdmin;
        this.requestTemplate = requestTemplate;
        this.maxRequestTimeoutNs = TimeUnit.MILLISECONDS.toNanos(maxRequestTimeout);
        this.responseTemplate = responseTemplate;
        this.maxRequestTimeout = maxRequestTimeout;
        this.maxPendingRequests = maxPendingRequests;
        this.pollInterval = pollInterval;
        this.internalExecutor = (executor == null);
        this.executor = internalExecutor ? createExecutor() : executor;
    }

    private ExecutorService createExecutor() {
        return Executors.newSingleThreadExecutor(
                JackmouseThreadFactory.forName("tb-queue-request-template-" + responseTemplate.getTopic()));
    }

    @Override
    public void init() {
        queueAdmin.createTopicIfNotExists(responseTemplate.getTopic());
        requestTemplate.init();
        responseTemplate.subscribe();
        executor.submit(this::mainLoop);
    }

    void mainLoop() {
        while (!stopped) {
            JmStopWatch stopWatch = JmStopWatch.create();
            try {
                fetchAndProcessResponses();
            } catch (Throwable t) {
                long sleepNanos = TimeUnit.MILLISECONDS.toNanos(this.pollInterval) - stopWatch.stopAndGetTotalTimeMillis();
                log.warn("Failed to obtain and process responses from queue. Going to sleep {}ns", sleepNanos, t);
                sleep(sleepNanos);
            }
        }
    }

    void fetchAndProcessResponses() {
        final long pendingRequestsCount = pendingRequests.mappingCount();
        log.trace("Starting template pool topic {}, for pendingRequests {}", responseTemplate.getTopic(), pendingRequestsCount);
        List<Response> responses = doPoll();
        log.trace("Completed template poll topic {}, for pendingRequests [{}], received [{}] responses", responseTemplate.getTopic(), pendingRequestsCount, responses.size());
        responses.forEach(this::processResponse);
        responseTemplate.commit();
        tryCleanStaleRequests();
    }

    private boolean tryCleanStaleRequests() {
        if (!cleanerLock.tryLock()) {
            return false;
        }
        try {
            log.trace("tryCleanStaleRequest...");
            final long currentNs = getCurrentClockNs();
            if (nextCleanupNs < currentNs) {
                pendingRequests.forEach((key, value) -> {
                    if (value.expTime < currentNs) {
                        ResponseMetaData<Response> staleRequest = pendingRequests.remove(key);
                        if (staleRequest != null) {
                            setTimeoutException(key, staleRequest, currentNs);
                        }
                    }
                });
                setupNextCleanup();
            }
        } finally {
            cleanerLock.unlock();
        }
        return true;
    }

    void setupNextCleanup() {
        nextCleanupNs = getCurrentClockNs() + maxRequestTimeoutNs;
        log.trace("setupNextCleanup {}", nextCleanupNs);
    }

    private void setTimeoutException(UUID key, ResponseMetaData<Response> staleRequest, long currentNs) {
        if (currentNs >= staleRequest.getSubmitTime() + staleRequest.getTimeout()) {
            log.debug("Request timeout detected, currentNs [{}], {}, key [{}]", currentNs, staleRequest, key);
        } else {
            log.info("Request timeout detected, currentNs [{}], {}, key [{}]", currentNs, staleRequest, key);
        }
        staleRequest.future.setException(new TimeoutException());
    }

    private void processResponse(Response response) {
        byte[] requestIdHeader = response.getHeaders().get(REQUEST_ID_HEADER);
        UUID requestId;
        if (requestIdHeader == null) {
            log.error("[{}] Missing requestId in header and body", response);
        } else {
            requestId = bytesToUuid(requestIdHeader);
            log.trace("[{}] Response received: {}", requestId, response);
            ResponseMetaData<Response> expectedResponse = pendingRequests.remove(requestId);
            if (expectedResponse == null) {
                log.debug("[{}] Invalid or stale request, response: {}", requestId, String.valueOf(response).replace("\n", " "));
            } else {
                expectedResponse.future.set(response);
            }
        }
    }

    List<Response> doPoll() {
        return responseTemplate.poll(pollInterval);
    }

    void sleep(long nanos) {
        LockSupport.parkNanos(nanos);
    }

    @Override
    public ListenableFuture<Response> send(Request request) {
        return send(request, maxRequestTimeoutNs);
    }

    @Override
    public ListenableFuture<Response> send(Request request, long timeoutNs) {
        return send(request, timeoutNs, null);
    }

    @Override
    public ListenableFuture<Response> send(Request request, Integer partition) {
        return send(request, maxRequestTimeoutNs, partition);
    }

    private ListenableFuture<Response> send(Request request, long requestTimeoutNs, Integer partition) {
        if (pendingRequests.mappingCount() >= maxPendingRequests) {
            log.warn("Pending request map is full [{}]! Consider to increase maxPendingRequests or increase processing performance. Request is {}", maxPendingRequests, request);
            return Futures.immediateFailedFuture(new RuntimeException("Pending request map is full!"));
        }
        UUID requestId = UUID.randomUUID();
        request.getHeaders().put(REQUEST_ID_HEADER, uuidToBytes(requestId));
        request.getHeaders().put(RESPONSE_TOPIC_HEADER, stringToBytes(responseTemplate.getTopic()));
        request.getHeaders().put(EXPIRE_TS_HEADER, longToBytes(getCurrentTimeMs() + maxRequestTimeout));
        long currentClockNs = getCurrentClockNs();
        SettableFuture<Response> future = SettableFuture.create();
        ResponseMetaData<Response> responseMetaData = new ResponseMetaData<>( currentClockNs, requestTimeoutNs, currentClockNs + requestTimeoutNs, future);
        log.trace("pending {}", responseMetaData);
        if (pendingRequests.putIfAbsent(requestId, responseMetaData) != null) {
            log.warn("Pending request already exists [{}]!", requestId);
            return Futures.immediateFailedFuture(new RuntimeException("Pending request already exists !" + requestId));
        }
        sendToRequestTemplate(request, requestId, partition, future, responseMetaData);
        return future;
    }

    private void sendToRequestTemplate(Request request, UUID requestId, Integer partition, SettableFuture<Response> future, ResponseMetaData<Response> responseMetaData) {
        log.trace("[{}] Sending request, key [{}], expTime [{}], request {}", requestId, request.getKey(), responseMetaData.expTime, request);
        if (messagesStats != null) {
            messagesStats.incrementTotal();
        }
        TopicPartitionInfo topicPartitionInfo = TopicPartitionInfo.builder()
                .topic(requestTemplate.getDefaultTopic())
                .partition(partition)
                .useInternalPartition(partition!= null)
                .build();
        requestTemplate.send(topicPartitionInfo, request, new JmQueueCallback() {
            @Override
            public void onSuccess(JmQueueMsgMetadata metadata) {
                if (messagesStats != null) {
                    messagesStats.incrementSuccessful();
                }
                log.trace("[{}] Request sent: {}, request {}", requestId, metadata, request);
            }

            @Override
            public void onFailure(Throwable t) {
                if (messagesStats != null) {
                    messagesStats.incrementFailed();
                }
                pendingRequests.remove(requestId);
                future.setException(t);
            }
        });
    }

    long getCurrentTimeMs() {
        return System.currentTimeMillis();
    }
    long getCurrentClockNs() {
        return System.nanoTime();
    }
    @Override
    public void stop() {
        stopped = true;
        if (responseTemplate!= null) {
            responseTemplate.unsubscribe();
        }
        if (requestTemplate!= null) {
            requestTemplate.stop();
        }
        if (internalExecutor) {
            executor.shutdown();
        }
    }
    @Getter
    static class ResponseMetaData<T> {
        private final long submitTime;
        private final long timeout;
        private final long expTime;
        private final SettableFuture<T> future;

        ResponseMetaData( long submitTime, long timeout,long ts, SettableFuture<T> future) {
            this.submitTime = submitTime;
            this.timeout = timeout;
            this.expTime = ts;
            this.future = future;
        }

        @Override
        public String toString() {
            return "ResponseMetaData{" +
                    "submitTime=" + submitTime +
                    ", calculatedExpTime=" + (submitTime + timeout) +
                    ", deltaMs=" + (expTime - submitTime) +
                    ", expTime=" + expTime +
                    ", future=" + future +
                    '}';
        }
    }
}
