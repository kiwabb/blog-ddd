package com.jackmouse.system.iot.queue;

import java.util.UUID;

/**
 * @ClassName JmQueueMsg
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/1 17:15
 * @Version 1.0
 **/
public interface JmQueueMsg {
    UUID getKey();
    JmQueueMsgHeaders getHeaders();

    byte[] getData();
}
