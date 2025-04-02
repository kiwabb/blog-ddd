package com.jackmouse.system.iot.queue.memory;

import com.jackmouse.system.iot.message.queue.TopicPartitionInfo;
import com.jackmouse.system.iot.queue.JmQueueConsumer;
import com.jackmouse.system.iot.queue.JmQueueMsg;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName InMemoryJmQueueComsumer
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/2 09:42
 * @Version 1.0
 **/
@Slf4j
public class InMemoryJmQueueConsumer<T extends JmQueueMsg> implements JmQueueConsumer<T> {

    private final InMemoryStorage<T> storage;
    private volatile Set<TopicPartitionInfo> partitions;
    private volatile boolean stopped;
    private volatile boolean subscribed;
    private final String topic;

    public InMemoryJmQueueConsumer(InMemoryStorage storage, String topic) {
        this.storage = storage;
        this.topic = topic;
        stopped = false;
    }

    @Override
    public String getTopic() {
        return topic;
    }

    @Override
    public void subscribe() {
        partitions = Collections.singleton(new TopicPartitionInfo(topic, null, null, false));
        subscribed = true;
    }

    @Override
    public void subscribe(Set<TopicPartitionInfo> partitions) {
        this.partitions = partitions;
        subscribed = true;
    }

    @Override
    public void stop() {
        stopped = true;
    }

    @Override
    public void unsubscribe() {
        stopped = true;
        subscribed = false;
    }

    @Override
    public List<T> poll(long durationInMillis) {
        if (subscribed) {
            @SuppressWarnings("unchecked")
            List<T> messages = partitions.stream().map(topicPartitionInfo -> {
                try {
                    return storage.get(topicPartitionInfo.getFullTopicName());
                } catch (InterruptedException e) {
                    log.error("Queue was interrupted.", e);
                }
                return Collections.emptyList();
            }).flatMap(List::stream).map(msg -> (T) msg).toList();
            if (!messages.isEmpty()) {
                return messages;
            }
        }
        try {
            Thread.sleep(durationInMillis);
        } catch (InterruptedException e) {
            if (!stopped) {
                log.error("Failed to sleep.", e);
            }
        }
        return Collections.emptyList();
    }

    @Override
    public void commit() {

    }

    @Override
    public boolean isStopped() {
        return stopped;
    }

    @Override
    public List<String> getFullTopicNames() {
        return partitions.stream().map(TopicPartitionInfo::getFullTopicName).collect(Collectors.toList());
    }
}
