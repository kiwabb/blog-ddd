package com.jackmouse.system.iot.device.valueobject;

import lombok.Getter;

/**
 * @ClassName TbMsgType
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/1 17:01
 * @Version 1.0
 **/
public enum JmMsgType {
    POST_TELEMETRY_REQUEST("Post telemetry");

    @Getter
    private final String ruleNodeConnection;

    @Getter
    private final boolean tellSelfOnly;
    JmMsgType(String ruleNodeConnection, boolean tellSelfOnly) {
        this.ruleNodeConnection = ruleNodeConnection;
        this.tellSelfOnly = tellSelfOnly;
    }

    JmMsgType(String ruleNodeConnection) {
        this(ruleNodeConnection, false);
    }

    JmMsgType() {
        this(null, false);
    }
}
