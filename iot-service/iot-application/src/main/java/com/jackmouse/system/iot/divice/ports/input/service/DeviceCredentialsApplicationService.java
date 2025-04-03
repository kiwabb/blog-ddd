package com.jackmouse.system.iot.divice.ports.input.service;

import com.jackmouse.system.iot.device.entity.DeviceCredentials;

/**
 * @ClassName DeviceCredentialsApplicationService
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/3 09:40
 * @Version 1.0
 **/
public interface DeviceCredentialsApplicationService {
    DeviceCredentials findDeviceCredentialByCredentialsId(String token);
}
