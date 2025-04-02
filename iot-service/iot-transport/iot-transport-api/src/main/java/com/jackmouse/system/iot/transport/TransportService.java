package com.jackmouse.system.iot.transport;

import com.jackmouse.server.gen.transport.TransportProtos;
import com.jackmouse.server.gen.transport.TransportProtos.*;
import com.jackmouse.system.blog.domain.valueobject.DeviceTransportType;
import com.jackmouse.system.iot.message.JmMsgMetaData;
import com.jackmouse.system.iot.transport.auth.ValidateDeviceCredentialsResponse;

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
}
