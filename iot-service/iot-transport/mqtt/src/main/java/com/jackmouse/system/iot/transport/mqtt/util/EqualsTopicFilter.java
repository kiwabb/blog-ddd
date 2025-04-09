package com.jackmouse.system.iot.transport.mqtt.util;

import lombok.Data;

/**
 * @ClassName EqualsTopicFilter
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/8 11:16
 * @Version 1.0
 **/
@Data
public class EqualsTopicFilter implements MqttTopicFilter{
    private final String filter;

    @Override
    public boolean filter(String topic) {
        return filter.equals(topic);
    }
}
