package com.jackmouse.system.iot.transport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.jackmouse.server.gen.transport.TransportProtos;
import com.jackmouse.system.blog.domain.valueobject.DeviceTransportType;
import com.jackmouse.system.iot.device.entity.Device;
import com.jackmouse.system.iot.device.entity.DeviceCredentials;
import com.jackmouse.system.iot.device.entity.DeviceProfile;
import com.jackmouse.system.iot.device.valueobject.*;
import com.jackmouse.system.iot.divice.ports.input.service.DeviceApplicationService;
import com.jackmouse.system.iot.divice.ports.input.service.DeviceCredentialsApplicationService;
import com.jackmouse.system.iot.divice.ports.input.service.DeviceProfileApplicationService;
import com.jackmouse.system.iot.message.EncryptionUtil;
import com.jackmouse.system.iot.queue.common.JmProtoQueueMsg;
import com.jackmouse.system.iot.transport.ports.input.service.TransportApiService;
import com.jackmouse.system.iot.util.ProtoUtils;
import com.jackmouse.system.utils.JackmouseExecutor;
import com.jackmouse.system.utils.JacksonUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.jackmouse.system.iot.device.valueobject.BasicCredentialsValidationResult.VALID;

/**
 * @ClassName DefaultTransportApiService
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/3 09:12
 * @Version 1.0
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultTransportApiService implements TransportApiService {

    private final DeviceCredentialsApplicationService deviceCredentialsApplicationService;
    private final DeviceApplicationService deviceApplicationService;
    private final DeviceProfileApplicationService deviceProfileApplicationService;


    ListeningExecutorService handlerExecutor;

    @Value("${queue.transport_api.max_core_handler_threads:16}")
    private int maxCoreHandlerThreads;

    @PostConstruct
    public void init() {
        handlerExecutor = MoreExecutors.listeningDecorator(JackmouseExecutor.newWorkStealingPool(maxCoreHandlerThreads, "transport-api-service-core-handler"));
    }

    @PreDestroy
    public void destroy() {
        if (handlerExecutor != null) {
            handlerExecutor.shutdownNow();
        }
    }

    @Override
    public ListenableFuture<JmProtoQueueMsg<TransportProtos.TransportApiResponseMsg>> handle(JmProtoQueueMsg<TransportProtos.TransportApiRequestMsg> jmProtoQueueMsg) {
        TransportProtos.TransportApiRequestMsg transportApiRequestMsg = jmProtoQueueMsg.getValue();
        return handlerExecutor.submit(() -> {
            TransportProtos.TransportApiResponseMsg result = handle(transportApiRequestMsg);
            return new JmProtoQueueMsg<>(jmProtoQueueMsg.getKey(), result, jmProtoQueueMsg.getHeaders());
        });
    }

    private TransportProtos.TransportApiResponseMsg handle(TransportProtos.TransportApiRequestMsg transportApiRequestMsg) {
        if (transportApiRequestMsg.hasValidateTokenRequestMsg()) {
            log.info("handle validateTokenRequestMsg");
            final String token = transportApiRequestMsg.getValidateTokenRequestMsg().getToken();
            return validateCredentials(token, DeviceCredentialsType.ACCESS_TOKEN);
        } else if (transportApiRequestMsg.hasValidateBasicMqttCredRequestMsg()) {
            log.info("handle validateBasicMqttCredRequestMsg");
            TransportProtos.ValidateBasicMqttCredRequestMsg msg = transportApiRequestMsg.getValidateBasicMqttCredRequestMsg();
            return validateCredentials(msg);
        }
        return getEmptyTransportApiResponseMsg();
    }

    private TransportProtos.TransportApiResponseMsg validateCredentials(TransportProtos.ValidateBasicMqttCredRequestMsg mqtt) {
        DeviceCredentials credentials;
        if (StringUtils.isEmpty(mqtt.getUserName())) {
            credentials = checkMqttCredentials(mqtt, EncryptionUtil.getSha3Hash(mqtt.getClientId()));
            if (credentials != null) {
                return getDeviceInfo(credentials);
            }
        }
        return getEmptyTransportApiResponseMsg();
    }

    private DeviceCredentials checkMqttCredentials(TransportProtos.ValidateBasicMqttCredRequestMsg clientCred, String credId) {
        return checkMqttCredentials(clientCred, deviceCredentialsApplicationService.findDeviceCredentialsByCredentialsId(credId));
    }

    private DeviceCredentials checkMqttCredentials(TransportProtos.ValidateBasicMqttCredRequestMsg clientCred, DeviceCredentials deviceCredentials) {
        if (deviceCredentials != null && deviceCredentials.getCredentialsType() == DeviceCredentialsType.MQTT_BASIC) {
            if (VALID.equals(validateMqttCredentials(clientCred, deviceCredentials))) {
                return deviceCredentials;
            }
        }
        return null;
    }

    private BasicCredentialsValidationResult validateMqttCredentials(TransportProtos.ValidateBasicMqttCredRequestMsg clientCred, DeviceCredentials deviceCredentials) {
        return VALID;
    }

    private TransportProtos.TransportApiResponseMsg validateCredentials(String token, DeviceCredentialsType credentialsType) {
        DeviceCredentials credentials = deviceCredentialsApplicationService.findDeviceCredentialByCredentialsId(token);
        if (credentials != null && credentials.getCredentialsType() == credentialsType) {
            return getDeviceInfo(credentials);
        }
        return getEmptyTransportApiResponseMsg();
    }

    private TransportProtos.TransportApiResponseMsg getDeviceInfo(DeviceCredentials credentials) {
        Device device = deviceApplicationService.findDeviceById(credentials.getDeviceId());
        if (device == null) {
            device = Device.builder()
                    .id(credentials.getDeviceId())
                    .customerId(new CustomerId(UUID.randomUUID()))
                    .deviceProfileId(new DeviceProfileId(UUID.randomUUID()))
                    .name("unknown")
                    .type("unknown")
                    .additionalInfo(JacksonUtil.toString(credentials))
                    .build();
            //return getEmptyTransportApiResponseMsg();
        }
        try {
            TransportProtos.ValidateDeviceCredentialsResponseMsg.Builder builder = TransportProtos.ValidateDeviceCredentialsResponseMsg.newBuilder();
            builder.setDeviceInfo(ProtoUtils.toDeviceInfoProto(device));
            DeviceProfile deviceProfile = deviceProfileApplicationService.findDeviceProfileById(device.getDeviceProfileId());
            deviceProfile = DeviceProfile.builder()
                    .deviceProfileId(device.getDeviceProfileId())
                    .name("name")
                    .type(DeviceProfileType.DEFAULT)
                    .isDefault(true)
                    .transportType(DeviceTransportType.DEFAULT)
                    .provisionType(DeviceProfileProvisionType.ALLOW_CREATE_NEW_DEVICES)
                    .build();
            if (deviceProfile != null) {
                builder.setDeviceProfile(ProtoUtils.toProto(deviceProfile));
            } else {
                log.warn("[{}] Failed to find device profile [{}] for device. ", device.getId(), device.getDeviceProfileId());
            }
            if (!StringUtils.isEmpty(credentials.getCredentialsValue())) {
                builder.setCredentialsBody(credentials.getCredentialsValue());
            }
            return TransportProtos.TransportApiResponseMsg.newBuilder()
                    .setValidateCredResponseMsg(builder.build())
                    .build();
        } catch (JsonProcessingException e) {
            return getEmptyTransportApiResponseMsg();
        }
    }

    private TransportProtos.TransportApiResponseMsg getEmptyTransportApiResponseMsg() {
        return TransportProtos.TransportApiResponseMsg.newBuilder()
                .setValidateCredResponseMsg(TransportProtos.ValidateDeviceCredentialsResponseMsg.getDefaultInstance())
                .build();
    }
}
