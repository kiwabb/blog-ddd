package com.jackmouse.system.iot.transport.mqtt.session;

import com.jackmouse.system.iot.transport.session.DeviceAwareSessionContext;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

/**
 * @ClassName MqttDeviceAwareSessionContext
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/7 15:48
 * @Version 1.0
 **/
public abstract class MqttDeviceAwareSessionContext extends DeviceAwareSessionContext {
    private final ConcurrentMap<MqttTopicMatcher, Integer> mqttQoSMap;

    public MqttDeviceAwareSessionContext(UUID sessionId, ConcurrentMap<MqttTopicMatcher, Integer> mqttQoSMap) {
        super(sessionId);
        this.mqttQoSMap = mqttQoSMap;
    }
}
