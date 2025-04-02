package com.jackmouse.system.iot.queue.common;

import com.google.protobuf.GeneratedMessage;
import com.jackmouse.system.iot.queue.JmQueueMsg;
import com.jackmouse.system.iot.queue.JmQueueMsgHeaders;
import lombok.Data;

import java.util.UUID;

/**
 * @ClassName JmProtoQueueMsg
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/1 17:13
 * @Version 1.0
 **/
@Data
public class JmProtoQueueMsg<T extends GeneratedMessage> implements JmQueueMsg {

    private final UUID key;
    protected final T value;
    private final JmQueueMsgHeaders headers;

    public JmProtoQueueMsg(UUID key, T value) {
        this(key, value, new DefaultTbQueueMsgHeaders());
    }

    public JmProtoQueueMsg(UUID key, T value, JmQueueMsgHeaders headers) {
        this.key = key;
        this.value = value;
        this.headers = headers;
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
        return value != null ? value.toByteArray() : null;
    }
}
