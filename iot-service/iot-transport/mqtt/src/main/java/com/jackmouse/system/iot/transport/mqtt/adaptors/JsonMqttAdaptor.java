package com.jackmouse.system.iot.transport.mqtt.adaptors;

import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.jackmouse.server.gen.transport.TransportProtos;
import com.jackmouse.system.iot.adaptor.AdaptorException;
import com.jackmouse.system.iot.adaptor.JsonConverter;
import com.jackmouse.system.iot.transport.mqtt.session.DeviceSessionCtx;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @ClassName JsonMqttAdaptor
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/7 16:13
 * @Version 1.0
 **/
@Slf4j
@Component
public class JsonMqttAdaptor implements MqttTransportAdaptor{
    @Override
    public TransportProtos.PostTelemetryMsg convertToPostTelemetry(DeviceSessionCtx deviceSessionCtx, MqttPublishMessage inbound) throws AdaptorException {
        String payload = validatePayload(deviceSessionCtx.getSessionId(), inbound.payload(), false);
        try {
            return JsonConverter.convertToTelemetryProto(JsonParser.parseString(payload));
        } catch (IllegalStateException | JsonSyntaxException ex) {
            log.debug("Failed to decode post telemetry request", ex);
            throw new AdaptorException(ex);
        }
    }

    private String validatePayload(UUID sessionId, ByteBuf payloadData, boolean isEmptyPayloadAllowed) throws AdaptorException {
        String payload = payloadData.toString(StandardCharsets.UTF_8);
        if (payload == null) {
            log.debug("[{}] Payload is empty!", sessionId);
            if (!isEmptyPayloadAllowed) {
                throw new AdaptorException(new IllegalArgumentException("Payload is empty!"));
            }
        }
        return payload;
    }
}
