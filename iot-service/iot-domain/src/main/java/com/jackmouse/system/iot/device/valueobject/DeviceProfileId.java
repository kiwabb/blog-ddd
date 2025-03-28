package com.jackmouse.system.iot.device.valueobject;

import com.jackmouse.system.blog.domain.valueobject.BaseId;

import java.util.UUID;

/**
 * @ClassName DeviceProfileId
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/28 14:53
 * @Version 1.0
 **/
public class DeviceProfileId extends BaseId<UUID> {
    public DeviceProfileId(UUID value) {
        super(value);
    }
}
