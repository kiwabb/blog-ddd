package com.jackmouse.system.iot.queue.kafka;

import com.jackmouse.system.iot.queue.JmQueueMsg;

import java.io.IOException;

/**
 * @ClassName JmKafkaDecoder
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/3 14:28
 * @Version 1.0
 **/
public interface JmKafkaDecoder<T> {
    T decode(JmQueueMsg msg) throws IOException;
}
