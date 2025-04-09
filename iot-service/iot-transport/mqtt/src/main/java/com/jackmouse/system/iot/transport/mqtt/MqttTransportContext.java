package com.jackmouse.system.iot.transport.mqtt;

import com.jackmouse.system.iot.transport.TransportContext;
import com.jackmouse.system.iot.transport.mqtt.adaptors.JsonMqttAdaptor;
import jakarta.annotation.Resource;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName MqttTransportContext
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/7 14:53
 * @Version 1.0
 **/
@Component
public class MqttTransportContext extends TransportContext {

    private final AtomicInteger connectionsCounter = new AtomicInteger();

    @Getter
    @Resource
    private JsonMqttAdaptor jsonMqttAdaptor;

    @Getter
    @Value("${transport.mqtt.disconnect_timeout:1000}")
    private long disconnectTimeout;

    @Getter
    @Value("${transport.mqtt.msg_queue_size_per_device_limit:100}")
    private int messageQueueSizePerDeviceLimit;

    public void channelRegistered() {
        connectionsCounter.incrementAndGet();
    }

    public void channelUnregistered() {
        connectionsCounter.decrementAndGet();
    }

    public void onAuthFailure(InetSocketAddress address) {
        // TODO rateLimit
    }

    public void onAuthSuccess(InetSocketAddress address) {
        // TODO rateLimit
    }
}
