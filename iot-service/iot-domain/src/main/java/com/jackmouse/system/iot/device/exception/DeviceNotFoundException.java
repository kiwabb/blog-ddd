package com.jackmouse.system.iot.device.exception;

import com.jackmouse.system.blog.domain.exception.DomainException;

import java.util.UUID;

/**
 * @ClassName DeviceNotFoundException
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/31 15:04
 * @Version 1.0
 **/
public class DeviceNotFoundException extends DomainException {
    public DeviceNotFoundException(UUID deviceId) {
        super("Device with id " + deviceId + " doesn't exist!");
    }
}
