package com.jackmouse.system.iot.queue.common;

import com.jackmouse.system.iot.message.queue.TopicPartitionInfo;
import com.jackmouse.system.iot.queue.JmQueueConsumer;
import com.jackmouse.system.iot.queue.JmQueueMsg;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

/**
 * @ClassName AbstractJmQueueConsumerTemplate
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/2 09:46
 * @Version 1.0
 **/
@Slf4j
public abstract class AbstractJmQueueConsumerTemplate<R, T extends JmQueueMsg> implements JmQueueConsumer<T> {
    public static final long ONE_MILLISECOND_IN_NANOS = TimeUnit.MILLISECONDS.toNanos(1);
    private volatile boolean subscribed;
    private volatile boolean stopped;
    private volatile Set<TopicPartitionInfo> partitions;
    protected final ReentrantLock consumerLock = new ReentrantLock();
    final Queue<Set<TopicPartitionInfo>> subscribeQueue = new ConcurrentLinkedDeque<>();

    @Getter
    private final String topic;

    public AbstractJmQueueConsumerTemplate(String topic) {
        this.topic = topic;
    }

    @Override
    public void subscribe() {
        log.debug("enqueue topic subscribe {} ", topic);
        if (stopped) {
            log.error("trying subscribe, but consumer stopped for topic {}", topic);
            return;
        }
        subscribeQueue.add(Collections.singleton(new TopicPartitionInfo(topic, null, null, false)));
    }

    @Override
    public void subscribe(Set<TopicPartitionInfo> partitions) {
        log.debug("enqueue topic subscribe {} ", topic);
        if (stopped) {
            log.error("trying subscribe, but consumer stopped for topic {}", topic);
            return;
        }
        subscribeQueue.add(partitions);
    }

    @Override
    public List<T> poll(long durationInMillis) {
        List<R> records;
        long startNanos = System.nanoTime();
        if (stopped) {
            log.error("poll invoked but consumer stopped for topic {}", topic, new RuntimeException("stacktrace"));
            return emptyList();
        }
        if (!subscribed && partitions.isEmpty() && subscribeQueue.isEmpty()) {
            return sleepAndReturnEmpty(durationInMillis, startNanos);
        }
        if (consumerLock.isLocked()) {
            log.error("poll. consumerLock is locked. will wait with no timeout. it looks like a race conditions or deadlock topic {}", topic, new RuntimeException("stacktrace"));

        }
        consumerLock.lock();
        try {
            while (!subscribeQueue.isEmpty()) {
                subscribed = false;
                partitions = subscribeQueue.poll();
            }
            if (!subscribed) {
                log.info("Subscribing to {}", partitions);
                doSubscribe(partitions);
                subscribed = true;
            }
            records = partitions.isEmpty() ? emptyList() : doPoll(durationInMillis);
        } finally {
            consumerLock.unlock();
        }
        if (records.isEmpty() && !isLongPollingSupported()) {
            return sleepAndReturnEmpty(durationInMillis, startNanos);
        }
        return decodeRecords(records);
    }

    private List<T> decodeRecords(List<R> records) {
        ArrayList<T> result = new ArrayList<>(records.size());
        records.forEach(record -> {
            try {
                if (record != null) {
                    result.add(decode(record));
                }
            } catch (Exception e) {
                log.error("Failed to decode record {}", record, e);
                throw new RuntimeException("Failed to decode record " + record, e);
            }
        });
        return result;
    }

    @Override
    public void commit() {
        if (consumerLock.isLocked()) {
            if (stopped) {
                return;
            }
            log.error("commit. consumerLock is locked. will wait with no timeout. it looks like a race conditions or deadlock topic {}", topic, new RuntimeException("stacktrace"));
        }
        consumerLock.lock();
        try {
            doCommit();
        } finally {
            consumerLock.unlock();
        }
    }

    @Override
    public void stop() {
        stopped = true;
    }

    @Override
    public void unsubscribe() {
        log.info("Unsubscribing and stopping consumer for {}", partitions);
        stopped = true;
        consumerLock.lock();
        try {
            if (subscribed) {
                doUnsubscribe();
            }
        } finally {
            consumerLock.unlock();
        }
    }

    abstract protected T decode(R record) throws IOException;

    abstract protected List<R> doPoll(long durationInMillis);

    abstract protected void doSubscribe(Set<TopicPartitionInfo> partitions);
    abstract protected void doCommit();
    abstract protected void doUnsubscribe();

    private List<T> sleepAndReturnEmpty(long durationInMillis, long startNanos) {
        long duration = TimeUnit.MILLISECONDS.toNanos(durationInMillis);
        long spendNanos = System.nanoTime() - startNanos;
        long nanosLeft = duration - spendNanos;
        if (nanosLeft < ONE_MILLISECOND_IN_NANOS) {
            try {
                long sleepMs = TimeUnit.NANOSECONDS.toMillis(nanosLeft);
                log.trace("Going to sleep after poll: topic {} for {}ms", topic, sleepMs);
                Thread.sleep(sleepMs);
            } catch (InterruptedException e) {
                if (!stopped) {
                    log.error("Failed to wait", e);
                }
            }
        }
        return emptyList();
    }

    @Override
    public List<String> getFullTopicNames() {
        if (partitions == null) {
            return Collections.emptyList();
        }
        return partitions.stream().map(TopicPartitionInfo::getFullTopicName).collect(Collectors.toList());
    }

    protected boolean isLongPollingSupported() {
        return false;
    }
}
