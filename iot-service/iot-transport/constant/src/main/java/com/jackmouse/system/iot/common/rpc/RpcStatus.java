package com.jackmouse.system.iot.common.rpc;

import lombok.Getter;

/**
 * @ClassName RpcStatus
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/8 10:49
 * @Version 1.0
 **/
public enum RpcStatus {
    QUEUED(true),
    SENT(true),
    DELIVERED(true),
    SUCCESSFUL(false),
    TIMEOUT(false),
    EXPIRED(false),
    FAILED(false),
    DELETED(false);

    @Getter
    private final boolean pushDeleteNotificationToCore;

    RpcStatus(boolean pushDeleteNotificationToCore) {
        this.pushDeleteNotificationToCore = pushDeleteNotificationToCore;
    }
}
