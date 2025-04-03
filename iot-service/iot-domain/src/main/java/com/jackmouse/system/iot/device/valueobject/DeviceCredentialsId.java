package com.jackmouse.system.iot.device.valueobject;

import com.jackmouse.system.blog.domain.valueobject.BaseId;

import java.util.UUID;

/**
 * @ClassName DeviceCredentialsId
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/3 09:32
 * @Version 1.0
 **/
public class DeviceCredentialsId extends BaseId<UUID> {
    public DeviceCredentialsId(UUID value) {
        super(value);
    }
}
