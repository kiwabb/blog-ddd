package com.jackmouse.system.iot.queue.common;

import com.jackmouse.system.iot.queue.JmQueueMsgHeaders;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName DefaultTbQueueMsgHeaders
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/1 17:28
 * @Version 1.0
 **/
public class DefaultTbQueueMsgHeaders implements JmQueueMsgHeaders {
    protected final Map<String, byte[]> data = new HashMap<>();
    @Override
    public byte[] put(String key, byte[] value) {
        return data.put(key, value);
    }

    @Override
    public byte[] get(String key) {
        return data.get(key);
    }

    @Override
    public Map<String, byte[]> getData() {
        return data;
    }
}
