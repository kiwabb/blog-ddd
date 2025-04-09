package com.jackmouse.system.iot.transport.mqtt.adaptors;

import com.jackmouse.server.gen.transport.TransportProtos;
import com.jackmouse.system.iot.adaptor.AdaptorException;
import com.jackmouse.system.iot.transport.mqtt.session.DeviceSessionCtx;
import io.netty.handler.codec.mqtt.MqttPublishMessage;

/**
 * @ClassName MqttTransportAdaptor
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/7 16:06
 * @Version 1.0
 **/
public interface MqttTransportAdaptor {
    TransportProtos.PostTelemetryMsg convertToPostTelemetry(DeviceSessionCtx deviceSessionCtx, MqttPublishMessage msg) throws AdaptorException;
}
