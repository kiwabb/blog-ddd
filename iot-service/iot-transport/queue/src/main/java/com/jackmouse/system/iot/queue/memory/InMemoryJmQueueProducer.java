package com.jackmouse.system.iot.queue.memory;

import com.jackmouse.system.iot.message.queue.TopicPartitionInfo;
import com.jackmouse.system.iot.queue.JmQueueCallback;
import com.jackmouse.system.iot.queue.JmQueueMsg;
import com.jackmouse.system.iot.queue.JmQueueProducer;
import lombok.Data;

/**
 * @ClassName InMemoryJmQueueProducer
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/2 08:48
 * @Version 1.0
 **/
@Data
public class InMemoryJmQueueProducer<T extends JmQueueMsg> implements JmQueueProducer<T> {

    private final InMemoryStorage<T> storage;
    private final String defaultTopic;

    public InMemoryJmQueueProducer(InMemoryStorage storage, String defaultTopic) {
        this.storage = storage;
        this.defaultTopic = defaultTopic;
    }

    @Override
    public void init() {

    }

    @Override
    public void send(TopicPartitionInfo topicPartitionInfo, T msg, JmQueueCallback callback) {
        boolean result = storage.put(topicPartitionInfo.getTopic(), msg);
        if (result) {
            if (callback != null) {
                callback.onSuccess(null);
            }
        } else {
            if (callback != null) {
                callback.onFailure(new RuntimeException("Failed to send message to queue"));
            }
        }
    }

    @Override
    public void stop() {

    }
}
