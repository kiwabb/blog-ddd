package com.jackmouse.system.iot.queue;

import com.jackmouse.system.iot.message.queue.TopicPartitionInfo;

/**
 * @ClassName TbQueueProducer
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/2 08:50
 * @Version 1.0
 **/
public interface JmQueueProducer<T extends JmQueueMsg> {
    void init();

    String getDefaultTopic();

    void send(TopicPartitionInfo topicPartitionInfo, T msg, JmQueueCallback callback);

    void stop();
}
