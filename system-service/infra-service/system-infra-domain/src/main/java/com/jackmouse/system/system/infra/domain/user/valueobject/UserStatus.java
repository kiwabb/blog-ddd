package com.jackmouse.system.system.infra.domain.user.valueobject;

/**
 * @ClassName UserStatus
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/13 15:55
 * @Version 1.0
 **/
public enum UserStatus {
    NORMAL,
    LOCKED,
    DISABLED,
    DELETED,
    PENDING_APPROVAL,
    PENDING_ACTIVATION,
    PENDING_RESET_PASSWORD,
    PENDING_CHANGE_PASSWORD,
    PENDING_RESET_EMAIL,
    PENDING_CHANGE_EMAIL,
    PENDING_RESET_PHONE,
    PENDING_CHANGE_PHONE,
    PENDING_RESET_USERNAME,
}
