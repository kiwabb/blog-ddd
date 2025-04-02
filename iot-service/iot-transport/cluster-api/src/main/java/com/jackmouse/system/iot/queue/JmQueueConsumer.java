package com.jackmouse.system.iot.queue;

import com.jackmouse.system.iot.message.queue.TopicPartitionInfo;

import java.util.List;
import java.util.Set;

/**
 * @ClassName JmQueueConsumer
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/2 09:44
 * @Version 1.0
 **/
public interface JmQueueConsumer<T extends JmQueueMsg> {
    String getTopic();

    void subscribe();

    void subscribe(Set<TopicPartitionInfo> partitions);

    void stop();

    void unsubscribe();

    List<T> poll(long durationInMillis);

    void commit();

    boolean isStopped();

    List<String> getFullTopicNames();
}
