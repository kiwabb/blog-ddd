package com.jackmouse.system.iot.device.valueobject;

import com.jackmouse.system.blog.domain.valueobject.BaseId;

import java.util.UUID;

/**
 * @ClassName DeviceId
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/28 17:22
 * @Version 1.0
 **/
public class DeviceId extends BaseId<UUID> {
    public DeviceId(UUID value) {
        super(value);
    }
}
