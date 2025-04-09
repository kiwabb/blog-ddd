package com.jackmouse.system.iot.transport.mqtt.util;

/**
 * @ClassName AlwaysTrueTopicFilter
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/8 11:15
 * @Version 1.0
 **/
public class AlwaysTrueTopicFilter implements MqttTopicFilter{
    @Override
    public boolean filter(String topic) {
        return true;
    }
}
