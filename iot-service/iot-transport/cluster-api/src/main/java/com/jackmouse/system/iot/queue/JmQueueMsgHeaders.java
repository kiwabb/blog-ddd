package com.jackmouse.system.iot.queue;

import java.util.Map;

/**
 * @ClassName TbQueueMsgHeaders
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/1 17:16
 * @Version 1.0
 **/
public interface JmQueueMsgHeaders {
    byte[] put(String key, byte[] value);

    byte[] get(String key);

    Map<String, byte[]> getData();
}
