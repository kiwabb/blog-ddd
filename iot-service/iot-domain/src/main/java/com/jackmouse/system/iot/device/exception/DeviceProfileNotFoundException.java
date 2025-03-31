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
public class DeviceProfileNotFoundException extends DomainException {
    public DeviceProfileNotFoundException(UUID deviceProfileId) {
        super("Device profile with id " + deviceProfileId + " doesn't exist!");
    }
}
