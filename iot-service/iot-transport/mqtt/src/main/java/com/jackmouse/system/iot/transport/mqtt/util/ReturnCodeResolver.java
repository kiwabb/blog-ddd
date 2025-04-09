package com.jackmouse.system.iot.transport.mqtt.util;

import io.netty.handler.codec.mqtt.MqttConnectReturnCode;
import io.netty.handler.codec.mqtt.MqttVersion;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName ReturnCodeResolver
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/7 16:46
 * @Version 1.0
 **/
@Slf4j
public class ReturnCodeResolver {
    public static MqttConnectReturnCode getConnectionReturnCode(MqttVersion mqttVersion, MqttConnectReturnCode returnCode) {
        if (!MqttVersion.MQTT_5.equals(mqttVersion) && !MqttConnectReturnCode.CONNECTION_ACCEPTED.equals(returnCode)) {
            switch (returnCode) {
                case CONNECTION_REFUSED_BAD_USERNAME_OR_PASSWORD:
                case CONNECTION_REFUSED_NOT_AUTHORIZED_5:
                    return MqttConnectReturnCode.CONNECTION_REFUSED_NOT_AUTHORIZED;
                case CONNECTION_REFUSED_CLIENT_IDENTIFIER_NOT_VALID:
                    return MqttConnectReturnCode.CONNECTION_REFUSED_IDENTIFIER_REJECTED;
                case CONNECTION_REFUSED_SERVER_UNAVAILABLE_5:
                case CONNECTION_REFUSED_CONNECTION_RATE_EXCEEDED:
                    return MqttConnectReturnCode.CONNECTION_REFUSED_SERVER_UNAVAILABLE;
                default:
                    log.warn("Unknown return code for conversion: {}", returnCode.name());
            }
        }
        return MqttConnectReturnCode.valueOf(returnCode.byteValue());
    }

}
