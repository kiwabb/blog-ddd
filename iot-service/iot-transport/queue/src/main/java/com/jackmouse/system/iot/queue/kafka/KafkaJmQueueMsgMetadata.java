package com.jackmouse.system.iot.queue.kafka;

import com.jackmouse.system.iot.queue.JmQueueMsgMetadata;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * @ClassName KafkaJmQueueMsgMetadata
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/3 14:16
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
public class KafkaJmQueueMsgMetadata implements JmQueueMsgMetadata {
    private RecordMetadata recordMetadata;
}
