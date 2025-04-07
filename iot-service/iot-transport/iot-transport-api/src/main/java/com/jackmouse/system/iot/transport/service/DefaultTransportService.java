package com.jackmouse.system.iot.transport.service;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.gson.JsonObject;
import com.jackmouse.server.gen.transport.TransportProtos;
import com.jackmouse.system.blog.domain.valueobject.DeviceTransportType;
import com.jackmouse.system.iot.device.entity.DeviceProfile;
import com.jackmouse.system.iot.device.valueobject.*;
import com.jackmouse.system.iot.message.JmMsgMetaData;
import com.jackmouse.system.iot.queue.JmQueueCallback;
import com.jackmouse.system.iot.queue.JmQueueMsgMetadata;
import com.jackmouse.system.iot.queue.JmQueueRequestTemplate;
import com.jackmouse.system.iot.queue.common.AsyncCallbackTemplate;
import com.jackmouse.system.iot.queue.common.JmProtoQueueMsg;
import com.jackmouse.system.iot.queue.provider.JmTransportQueueFactory;
import com.jackmouse.system.iot.transport.TransportDeviceProfileCache;
import com.jackmouse.system.iot.transport.TransportService;
import com.jackmouse.system.iot.transport.TransportServiceCallback;
import com.jackmouse.system.iot.transport.auth.TransportDeviceInfo;
import com.jackmouse.system.iot.transport.auth.ValidateDeviceCredentialsResponse;
import com.jackmouse.system.iot.transport.utils.JsonUtils;
import com.jackmouse.system.utils.JackmouseExecutor;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName DefaultTransportService
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/1 15:53
 * @Version 1.0
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultTransportService implements TransportService {

    private final TransportDeviceProfileCache deviceProfileCache;
    private final JmTransportQueueFactory queueProvider;

    protected JmQueueRequestTemplate<
            JmProtoQueueMsg<TransportProtos.TransportApiRequestMsg>,
            JmProtoQueueMsg<TransportProtos.TransportApiResponseMsg>> transportApiRequestTemplate;

    protected ExecutorService transportCallbackExecutor;

    @PostConstruct
    public void init() {
        this.transportCallbackExecutor = JackmouseExecutor.newWorkStealingPool(20, getClass());
        transportApiRequestTemplate = queueProvider.createTransportApiRequestTemplate();
        transportApiRequestTemplate.init();
    }

    @Override
    public void process(TransportProtos.SessionInfoProto sessionInfo, TransportProtos.SessionEventMsg msg, TransportServiceCallback<Void> callback) {

    }

    @Override
    public void process(TransportProtos.SessionInfoProto sessionInfo, TransportProtos.PostTelemetryMsg msg, TransportServiceCallback<Void> callback) {
        process(sessionInfo, msg, null, callback);
    }

    @Override
    public void process(DeviceTransportType transportType, TransportProtos.ValidateDeviceTokenRequestMsg msg, TransportServiceCallback<ValidateDeviceCredentialsResponse> callback) {
        log.trace("Processing msg: {}", msg);
        JmProtoQueueMsg<TransportProtos.TransportApiRequestMsg> protoQueueMsg = new JmProtoQueueMsg<>(UUID.randomUUID(),
                TransportProtos.TransportApiRequestMsg.newBuilder().setValidateTokenRequestMsg(msg).build());
        doProcess(transportType, protoQueueMsg, callback);
    }

    @Override
    public void process(TransportProtos.SessionInfoProto sessionInfo, TransportProtos.PostTelemetryMsg msg, JmMsgMetaData md, TransportServiceCallback<Void> callback) {
        int dataPoints = 0;
        for (TransportProtos.TsKvListProto tsKvListProto : msg.getTsKvListList()) {
            dataPoints += tsKvListProto.getKvCount();
        }
        if (checkLimits(sessionInfo, msg, callback, dataPoints)) {
            recordActivityInternal(sessionInfo);
            DeviceId deviceId = new DeviceId(new UUID(sessionInfo.getDeviceIdMSB(), sessionInfo.getDeviceIdLSB()));
            CustomerId customerId = getCustomerId(sessionInfo);
            MsgPackCallback packCallback = new MsgPackCallback(msg.getTsKvListCount(), new ApiStatsProxyCallback<>(null, customerId, dataPoints, callback));
            for (TransportProtos.TsKvListProto tsKvListProto : msg.getTsKvListList()) {
                JmMsgMetaData metaData = md != null ? md.copy() : new JmMsgMetaData();
                metaData.putValue("deviceName", sessionInfo.getDeviceName());
                metaData.putValue("deviceType", sessionInfo.getDeviceType());
                metaData.putValue("ts", tsKvListProto.getTs() + "");
                JsonObject json = JsonUtils.getJsonObject(tsKvListProto.getKvList());
                sendToRuleEngine(null, deviceId, customerId, sessionInfo, json, metaData, JmMsgType.POST_TELEMETRY_REQUEST, packCallback);
            }
        }
    }
    private void doProcess(DeviceTransportType transportType, JmProtoQueueMsg<TransportProtos.TransportApiRequestMsg> protoMsg,
                           TransportServiceCallback<ValidateDeviceCredentialsResponse> callback) {
        ListenableFuture<ValidateDeviceCredentialsResponse> response = Futures.transform(
                transportApiRequestTemplate.send(protoMsg), tmp -> {
                    TransportProtos.ValidateDeviceCredentialsResponseMsg msg = tmp.getValue().getValidateCredResponseMsg();
                    ValidateDeviceCredentialsResponse.ValidateDeviceCredentialsResponseBuilder result = ValidateDeviceCredentialsResponse.builder();
                    if (msg.hasDeviceInfo()) {
                        result.credentials(msg.getCredentialsBody());
                        TransportDeviceInfo deviceInfo = getTransportDeviceInfo(msg.getDeviceInfo());
                        result.deviceInfo(deviceInfo);
                        if (msg.hasDeviceProfile()) {
                            DeviceProfile profile = deviceProfileCache.getOrCreate(deviceInfo.getDeviceProfileId(), msg.getDeviceProfile());
                            if (transportType != DeviceTransportType.DEFAULT
                                    && profile!= null && profile.getTransportType() != DeviceTransportType.DEFAULT && profile.getTransportType() != transportType) {
                                log.debug("[{}] Device profile [{}] has different transport type: {}, expected: {}", deviceInfo.getDeviceId(), deviceInfo.getDeviceProfileId(), profile.getTransportType(), transportType);
                                throw new IllegalStateException("Device profile has different transport type: " + profile.getTransportType() + ". Expected: " + transportType);
                            }
                            result.deviceProfile(profile);
                        }
                    }
                    return result.build();
                }, MoreExecutors.directExecutor());
        AsyncCallbackTemplate.withCallback(response, callback::onSuccess, callback::onFailure, transportCallbackExecutor);

    }

    private TransportDeviceInfo getTransportDeviceInfo(TransportProtos.DeviceInfoProto deviceInfo) {
        TransportDeviceInfo transportDeviceInfo = new TransportDeviceInfo();
        transportDeviceInfo.setCustomerId(new CustomerId(new UUID(deviceInfo.getCustomerIdMSB(), deviceInfo.getCustomerIdLSB())));
        transportDeviceInfo.setDeviceId(new DeviceId(new UUID(deviceInfo.getDeviceIdMSB(), deviceInfo.getDeviceIdLSB())));
        transportDeviceInfo.setDeviceProfileId(new DeviceProfileId(new UUID(deviceInfo.getDeviceProfileIdMSB(), deviceInfo.getDeviceProfileIdLSB())));
        transportDeviceInfo.setAdditionalInfo(deviceInfo.getAdditionalInfo());
        transportDeviceInfo.setDeviceName(deviceInfo.getDeviceName());
        transportDeviceInfo.setDeviceType(deviceInfo.getDeviceType());
        transportDeviceInfo.setGateway(deviceInfo.getIsGateway());
        if (StringUtils.isNotEmpty(deviceInfo.getPowerMode())) {
            transportDeviceInfo.setPowerMode(PowerMode.valueOf(deviceInfo.getPowerMode()));
            transportDeviceInfo.setEdrxCycle(deviceInfo.getEdrxCycle());
            transportDeviceInfo.setPsmActivityTimer(deviceInfo.getPsmActivityTimer());
            transportDeviceInfo.setPagingTransmissionWindow(deviceInfo.getPagingTransmissionWindow());
        }
        return transportDeviceInfo;
    }

    private void sendToRuleEngine(Object tenantId, DeviceId deviceId, CustomerId customerId, TransportProtos.SessionInfoProto sessionInfo, JsonObject json,
                                  JmMsgMetaData metaData, JmMsgType jmMsgType, MsgPackCallback packCallback) {
        DeviceProfileId deviceProfileId = new DeviceProfileId(new UUID(sessionInfo.getDeviceProfileIdMSB(), sessionInfo.getDeviceProfileIdLSB()));
        log.info("[{}] Sending msg to rule engine: {}", deviceId, json);
        packCallback.onSuccess(null);

    }

    private void recordActivityInternal(TransportProtos.SessionInfoProto sessionInfo) {
        if (sessionInfo != null) {
            log.info("[{}][{}] Session activity", toSessionId(sessionInfo), sessionInfo.getDeviceName());
        } else {
            log.warn("Session info is missing, unable to record activity");
        }
    }

    private boolean checkLimits(TransportProtos.SessionInfoProto sessionInfo, TransportProtos.PostTelemetryMsg msg, TransportServiceCallback<Void> callback, int dataPoints) {
        if (log.isTraceEnabled()) {
            log.trace("[{}] Processing msg: {}", toSessionId(sessionInfo), msg);
        }
        DeviceId deviceId = new DeviceId(new UUID(sessionInfo.getDeviceIdMSB(), sessionInfo.getDeviceIdLSB()));
        DeviceId gatewayId = null;
        if (sessionInfo.hasGatewayIdMSB() && sessionInfo.hasGatewayIdLSB()) {
            gatewayId = new DeviceId(new UUID(sessionInfo.getGatewayIdMSB(), sessionInfo.getGatewayIdLSB()));
        }
        return checkLimits(null, gatewayId, deviceId, sessionInfo.getDeviceName(), msg, callback, dataPoints, sessionInfo.getIsGateway());
    }
    private boolean checkLimits(Object tenantId, DeviceId gatewayId, DeviceId deviceId, String deviceName, Object msg, TransportServiceCallback<?> callback, int dataPoints, boolean isGateway) {
        if (log.isTraceEnabled()) {
            log.trace("[{}][{}] Processing msg: {}", tenantId, deviceName, msg);
        }
        return true;
    }
    protected UUID toSessionId(TransportProtos.SessionInfoProto sessionInfo) {
        return new UUID(sessionInfo.getSessionIdMSB(), sessionInfo.getSessionIdLSB());
    }
    protected CustomerId getCustomerId(TransportProtos.SessionInfoProto sessionInfo) {
        long msb = sessionInfo.getCustomerIdMSB();
        long lsb = sessionInfo.getCustomerIdLSB();
        if (msb != 0 && lsb != 0) {
            return new CustomerId(new UUID(msb, lsb));
        } else {
            return new CustomerId( UUID.fromString("13814000-1dd2-11b2-8080-808080808080"));
        }
    }
    protected long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    private class MsgPackCallback implements JmQueueCallback {
        private final AtomicInteger msgCount;
        private final TransportServiceCallback<Void> callback;

        private MsgPackCallback(Integer msgCount, TransportServiceCallback<Void> callback) {
            this.msgCount = new AtomicInteger(msgCount);
            this.callback = callback;
        }

        @Override
        public void onSuccess(JmQueueMsgMetadata metadata) {
            if (msgCount.decrementAndGet() <= 0) {
                DefaultTransportService.this.transportCallbackExecutor.submit(() -> callback.onSuccess(null));
            }
        }

        @Override
        public void onFailure(Throwable t) {
            DefaultTransportService.this.transportCallbackExecutor.submit(() -> callback.onFailure(t));
        }
    }

    private class ApiStatsProxyCallback<T> implements TransportServiceCallback<T> {
        private final Object tenantId;
        private final CustomerId customerId;
        private final int dataPoints;
        private final TransportServiceCallback<T> callback;

        private ApiStatsProxyCallback(Object tenantId, CustomerId customerId, int dataPoints, TransportServiceCallback<T> callback) {
            this.tenantId = tenantId;
            this.customerId = customerId;
            this.dataPoints = dataPoints;
            this.callback = callback;
        }

        @Override
        public void onSuccess(T msg) {
            callback.onSuccess(msg);
        }

        @Override
        public void onFailure(Throwable t) {
            callback.onFailure(t);
        }
    }
}
