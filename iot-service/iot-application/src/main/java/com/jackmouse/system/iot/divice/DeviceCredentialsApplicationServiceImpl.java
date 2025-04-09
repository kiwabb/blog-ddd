package com.jackmouse.system.iot.divice;

import com.jackmouse.system.iot.device.entity.DeviceCredentials;
import com.jackmouse.system.iot.device.valueobject.DeviceCredentialsType;
import com.jackmouse.system.iot.device.valueobject.DeviceId;
import com.jackmouse.system.iot.divice.ports.input.service.DeviceCredentialsApplicationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @ClassName DeviceCredentialsApplicationServiceImpl
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/3 09:44
 * @Version 1.0
 **/
@Service
public class DeviceCredentialsApplicationServiceImpl implements DeviceCredentialsApplicationService {
    @Override
    public DeviceCredentials findDeviceCredentialByCredentialsId(String token) {
        // TODO: 2025/4/3 模拟数据库查询
        return DeviceCredentials.builder()
                .credentialsId(token)
                .credentialsType(DeviceCredentialsType.ACCESS_TOKEN)
                .credentialsValue(token)
                .deviceId(new DeviceId(UUID.fromString("00000000-0000-0000-0000-000000000002")))
                .build();
    }

    @Override
    public DeviceCredentials findDeviceCredentialsByCredentialsId(String credId) {
        // TODO: 2025/4/3 模拟数据库查询
        return DeviceCredentials.builder()
                .credentialsId(credId)
                .credentialsType(DeviceCredentialsType.MQTT_BASIC)
                .credentialsValue(credId)
                .deviceId(new DeviceId(UUID.fromString("00000000-0000-0000-0000-000000000002")))
                .build();
    }
}
