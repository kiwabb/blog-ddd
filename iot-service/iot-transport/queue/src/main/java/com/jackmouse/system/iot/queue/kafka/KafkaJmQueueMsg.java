package com.jackmouse.system.iot.queue.kafka;

import com.jackmouse.system.iot.queue.JmQueueMsg;
import com.jackmouse.system.iot.queue.JmQueueMsgHeaders;
import com.jackmouse.system.iot.queue.common.DefaultTbQueueMsgHeaders;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.UUID;

/**
 * @ClassName KafkaJmQueueMsg
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/3 15:23
 * @Version 1.0
 **/
public class KafkaJmQueueMsg implements JmQueueMsg {

    private static final int UUID_LENGTH = 36;

    private final UUID key;
    private final JmQueueMsgHeaders headers;
    private final byte[] data;

    public KafkaJmQueueMsg(ConsumerRecord<String, byte[]> record) {
        if (record.key().length() <= UUID_LENGTH) {
            this.key = UUID.fromString(record.key());
        } else {
            this.key = UUID.randomUUID();
        }
        JmQueueMsgHeaders headers = new DefaultTbQueueMsgHeaders();
        record.headers().forEach(header -> {
            headers.put(header.key(), header.value());
        });
        this.headers = headers;
        this.data = record.value();
    }

    @Override
    public UUID getKey() {
        return key;
    }

    @Override
    public JmQueueMsgHeaders getHeaders() {
        return headers;
    }

    @Override
    public byte[] getData() {
        return data;
    }
}
