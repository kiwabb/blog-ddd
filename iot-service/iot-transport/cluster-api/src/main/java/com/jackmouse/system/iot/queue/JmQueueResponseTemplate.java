package com.jackmouse.system.iot.queue;

import com.jackmouse.system.iot.message.queue.TopicPartitionInfo;

import java.util.Set;

/**
 * @ClassName JmQueueResponseTemplate
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/2 16:40
 * @Version 1.0
 **/
public interface JmQueueResponseTemplate<Request extends JmQueueMsg, Response extends JmQueueMsg> {
    void subscribe();

    void subscribe(Set<TopicPartitionInfo> partitions);

    void launch(JmQueueHandler<Request, Response> handler);

    void stop();
}
