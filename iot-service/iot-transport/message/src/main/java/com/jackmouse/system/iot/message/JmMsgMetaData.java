package com.jackmouse.system.iot.message;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName JmMsgMetaData
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/1 16:00
 * @Version 1.0
 **/
public class JmMsgMetaData implements Serializable {
    public static final JmMsgMetaData EMPTY = new JmMsgMetaData(0);

    private final Map<String, String> data;

    public JmMsgMetaData() {
        this.data = new ConcurrentHashMap<>();
    }

    public JmMsgMetaData(Map<String, String> data) {
        this.data = new ConcurrentHashMap<>();
        data.forEach(this::putValue);
    }
    private JmMsgMetaData(int ignored) {
        this.data = Collections.emptyMap();
    }

    public String getValue(String key) {
        return this.data.get(key);
    }

    public void putValue(String key, String value) {
        if (key != null && value != null) {
            this.data.put(key, value);
        }
    }

    public Map<String, String> values() {
        return new HashMap<>(this.data);
    }

    public JmMsgMetaData copy() {
        return new JmMsgMetaData(this.data);
    }
}
