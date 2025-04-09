package com.jackmouse.system.iot.transport;

import com.jackmouse.server.gen.transport.TransportProtos;
import com.jackmouse.server.gen.transport.TransportProtos.*;
import com.jackmouse.system.blog.domain.valueobject.DeviceTransportType;
import com.jackmouse.system.iot.common.rpc.RpcStatus;
import com.jackmouse.system.iot.message.JmMsgMetaData;
import com.jackmouse.system.iot.transport.auth.ValidateDeviceCredentialsResponse;
import com.jackmouse.system.iot.transport.service.SessionMetaData;

import java.util.concurrent.ExecutorService;

/**
 * @ClassName TransportService
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/1 11:08
 * @Version 1.0
 **/
public interface TransportService {
    void process(SessionInfoProto sessionInfo, SessionEventMsg msg, TransportServiceCallback<Void> callback);

    void process(SessionInfoProto sessionInfo, PostTelemetryMsg postTelemetryMsg, TransportServiceCallback<Void> callback);

    void process(DeviceTransportType transportType, ValidateDeviceTokenRequestMsg msg,
                 TransportServiceCallback<ValidateDeviceCredentialsResponse> callback);

    void process(SessionInfoProto sessionInfo, PostTelemetryMsg msg, JmMsgMetaData md, TransportServiceCallback<Void> callback);

    void process(DeviceTransportType deviceTransportType, ValidateBasicMqttCredRequestMsg build, TransportServiceCallback<ValidateDeviceCredentialsResponse> transportServiceCallback);

    SessionMetaData registerAsyncSession(SessionInfoProto sessionInfo, SessionMsgLister mqttTransportHandler);

    ExecutorService getCallbackExecutor();

    void recordActivity(SessionInfoProto sessionInfo);

    void process(SessionInfoProto sessionInfo, ToDeviceRpcRequestMsg rpcRequestMsg, RpcStatus rpcStatus, boolean reportActivity, TransportServiceCallback<Void> callback);

    void process(SessionInfoProto sessionInfo, SubscribeToAttributeUpdatesMsg msg,  TransportServiceCallback<Void> callback);
}
