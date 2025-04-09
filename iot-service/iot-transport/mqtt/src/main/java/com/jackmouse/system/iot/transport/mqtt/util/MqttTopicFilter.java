package com.jackmouse.system.iot.transport.mqtt.util;

/**
 * @ClassName MqttTopicFilter
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/8 11:13
 * @Version 1.0
 **/
public interface MqttTopicFilter {
    boolean filter(String topic);
}
