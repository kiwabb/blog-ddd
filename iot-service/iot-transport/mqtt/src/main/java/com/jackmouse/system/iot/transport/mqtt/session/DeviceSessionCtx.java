package com.jackmouse.system.iot.transport.mqtt.session;

import com.jackmouse.system.iot.device.entity.DeviceProfile;
import com.jackmouse.system.iot.transport.mqtt.adaptors.MqttTransportAdaptor;
import com.jackmouse.system.iot.transport.mqtt.MqttTransportContext;
import com.jackmouse.system.iot.transport.mqtt.util.MqttTopicFilter;
import com.jackmouse.system.iot.transport.mqtt.util.MqttTopicFilterFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttVersion;
import io.netty.util.ReferenceCountUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/**
 * @ClassName DeviceSessionCtx
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/7 15:39
 * @Version 1.0
 **/
public class DeviceSessionCtx extends MqttDeviceAwareSessionContext{

    @Getter
    @Setter
    private ChannelHandlerContext channel;

    private final MqttTransportContext context;
    private volatile MqttTransportAdaptor adapter;
    @Getter
    @Setter
    private boolean provisionOnly = false;
    @Setter
    @Getter
    private MqttVersion mqttVersion;
    private volatile MqttTopicFilter telemetryTopicFilter = MqttTopicFilterFactory.getDefaultTelemetryFilter();

    private final AtomicInteger msgQueueSize = new AtomicInteger(0);
    private final ConcurrentLinkedQueue<MqttMessage> msgQueue = new ConcurrentLinkedQueue<>();
    @Getter
    private volatile boolean sendAckOnValidationException;
    @Getter
    private final Lock msgQueueProcessorLock = new ReentrantLock();

    @Getter
    private volatile boolean deviceProfileMqttTransportType;



    public DeviceSessionCtx(UUID sessionId, ConcurrentMap<MqttTopicMatcher, Integer> mqttQoSMap, MqttTransportContext context) {
        super(sessionId, mqttQoSMap);
        this.context = context;
        this.adapter = context.getJsonMqttAdaptor();
    }

    public void setDeviceProfile(DeviceProfile deviceProfile) {
        super.setDeviceProfile(deviceProfile);
        updateDeviceSessionConfiguration(deviceProfile);
    }

    private void updateDeviceSessionConfiguration(DeviceProfile deviceProfile) {
        // TODO 配置更新
    }

    public int getMsgQueueSize() {
        return msgQueueSize.get();
    }

    public void addToQueue(MqttMessage mqttMessage) {
        msgQueueSize.incrementAndGet();
        // 增加引用计数，避免释放
        ReferenceCountUtil.retain(mqttMessage);
        msgQueue.add(mqttMessage);
    }

    public void tryProcessQueueMsgs(Consumer<MqttMessage> msgProcessor) {
        while (!msgQueue.isEmpty()) {
            if (msgQueueProcessorLock.tryLock()) {
                try {
                    MqttMessage msg;
                    while ((msg = msgQueue.poll()) != null) {
                        try {
                            msgQueueSize.decrementAndGet();
                            msgProcessor.accept(msg);
                        } finally {
                            // 释放引用计数
                            ReferenceCountUtil.release(msg);
                        }
                    }
                } finally {
                    msgQueueProcessorLock.unlock();
                }
            } else {
                return;
            }
        }
    }

    public MqttTransportAdaptor getPayloadAdaptor() {
        return adapter;
    }

    public boolean isDeviceTelemetryTopic(String topicName) {
        return telemetryTopicFilter.filter(topicName);
    }

    public Collection<MqttMessage> getMsgQueueSnapshot() {
        return Collections.unmodifiableCollection(msgQueue);
    }
}
