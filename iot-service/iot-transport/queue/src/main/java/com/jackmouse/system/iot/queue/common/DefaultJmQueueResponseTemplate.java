package com.jackmouse.system.iot.queue.common;

import com.jackmouse.system.iot.message.queue.TopicPartitionInfo;
import com.jackmouse.system.iot.queue.*;
import com.jackmouse.system.utils.JackmouseExecutor;
import com.jackmouse.system.utils.JackmouseThreadFactory;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName DefaultTbQueueResponseTemplate
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/2 16:47
 * @Version 1.0
 **/
@Slf4j
public class DefaultJmQueueResponseTemplate<Request extends JmQueueMsg, Response extends JmQueueMsg>
        extends AbstractJmQueueTemplate implements JmQueueResponseTemplate<Request, Response> {

    private final JmQueueConsumer<Request> requestTemplate;
    private final JmQueueProducer<Response> responseTemplate;
    private final ConcurrentMap<UUID, String> pendingRequests;
    private final ScheduledExecutorService timeoutExecutor;
    private final ExecutorService callbackExecutor;
    private final ExecutorService looperExecutor;
    private volatile boolean stopped = false;
    private final int maxPendingRequests;
    private final long requestTimeout;
    private final long pollInterval;
    private final AtomicInteger pendingRequestCount = new AtomicInteger();

    @Builder
    public DefaultJmQueueResponseTemplate(JmQueueConsumer<Request> requestTemplate,
                                          JmQueueProducer<Response> responseTemplate,
                                          int maxPendingRequests,
                                          long requestTimeout,
                                          ExecutorService executor,
                                          long pollInterval) {
        this.requestTemplate = requestTemplate;
        this.responseTemplate = responseTemplate;
        this.pollInterval = pollInterval;
        this.pendingRequests = new ConcurrentHashMap<>();
        this.requestTimeout = requestTimeout;
        this.maxPendingRequests = maxPendingRequests;
        this.callbackExecutor = executor;
        this.timeoutExecutor = JackmouseExecutor.newSingleThreadScheduledExecutor("tb-queue-response-template-timeout-" + requestTemplate.getTopic());
        this.looperExecutor = Executors.newSingleThreadExecutor(JackmouseThreadFactory.forName("tb-queue-response-template-loop-" + requestTemplate.getTopic()));
    }

    @Override
    public void subscribe() {
        requestTemplate.subscribe();
    }

    @Override
    public void subscribe(Set<TopicPartitionInfo> partitions) {
        requestTemplate.subscribe(partitions);
    }

    @Override
    public void launch(JmQueueHandler<Request, Response> handler) {
        this.responseTemplate.init();
        looperExecutor.submit(() -> {
            while (!stopped) {
                try {
                    while (pendingRequestCount.get() > maxPendingRequests) {
                        try {
                            Thread.sleep(pollInterval);
                        } catch (InterruptedException e) {
                            log.trace("Failed to wait until the server has capacity to handle new requests", e);

                        }
                    }
                    List<Request> requests = requestTemplate.poll(pollInterval);
                    if (requests.isEmpty()) {
                        continue;
                    }
                    requests.forEach(request -> {
                        long currentTime = System.currentTimeMillis();
                        long expireTs = bytesToLong(request.getHeaders().get(EXPIRE_TS_HEADER));
                        if (expireTs > currentTime) {
                            byte[] requestIdHeader = request.getHeaders().get(REQUEST_ID_HEADER);
                            if (requestIdHeader == null) {
                                log.error("[{}] Missing requestId in header", request);
                                return;
                            }
                            byte[] responseTopicHead = request.getHeaders().get(RESPONSE_TOPIC_HEADER);
                            if (responseTopicHead == null) {
                                log.error("[{}] Missing response topic in header", request);
                                return;
                            }
                            UUID requestId = bytesToUuid(requestIdHeader);
                            String responseTopic = bytesToString(responseTopicHead);
                            try {
                                pendingRequestCount.getAndIncrement();
                                AsyncCallbackTemplate.withCallbackAndTimeout(handler.handle(request), response -> {
                                    pendingRequestCount.decrementAndGet();
                                    response.getHeaders().put(REQUEST_ID_HEADER, uuidToBytes(requestId));
                                    responseTemplate.send(TopicPartitionInfo.builder().topic(responseTopic).build(), response, null);
                                }, e -> {
                                    pendingRequestCount.decrementAndGet();
                                    if (e.getCause() != null && e.getCause() instanceof TimeoutException) {
                                        log.warn("[{}] Timeout to process the request: {}", requestId, request, e);
                                    } else {
                                        log.trace("[{}] Failed to process the request: {}", requestId, request, e);
                                    }
                                }, requestTimeout, timeoutExecutor, callbackExecutor);
                            } catch (Throwable e) {
                                pendingRequestCount.decrementAndGet();
                                log.warn("[{}] Failed to process the request: {}", requestId, request, e);
                            }
                        }
                    });
                    requestTemplate.commit();
                } catch (Throwable e) {
                    log.warn("Failed to obtain messages from queue.", e);
                    try {
                        Thread.sleep(pollInterval);
                    } catch (InterruptedException e2) {
                        log.trace("Failed to wait until the server has capacity to handle new requests", e2);
                    }
                }
            }
        });
    }

    @Override
    public void stop() {
        stopped = true;
        if (requestTemplate!= null) {
            requestTemplate.unsubscribe();
        }
        if (responseTemplate!= null) {
            responseTemplate.stop();
        }
        if (looperExecutor!= null) {
            looperExecutor.shutdown();
        }
    }
}
