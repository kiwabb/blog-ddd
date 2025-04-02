package com.jackmouse.system.iot.transport.auth;

import com.jackmouse.server.gen.transport.TransportProtos;
import com.jackmouse.system.iot.transport.TransportContext;

import java.util.UUID;

/**
 * @ClassName SessionInfoCreator
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/1 15:11
 * @Version 1.0
 **/
public class SessionInfoCreator {
    public static TransportProtos.SessionInfoProto create(ValidateDeviceCredentialsResponse msg, TransportContext context, UUID sessionId) {
        return getSessionInfoProto(msg, context.getNodeId(), sessionId);
    }
    private static TransportProtos.SessionInfoProto getSessionInfoProto(ValidateDeviceCredentialsResponse msg, String nodeId, UUID sessionId) {
        return TransportProtos.SessionInfoProto.newBuilder().setNodeId(nodeId)
                .setSessionIdMSB(sessionId.getMostSignificantBits())
                .setSessionIdLSB(sessionId.getLeastSignificantBits())
                .build();
    }
}
