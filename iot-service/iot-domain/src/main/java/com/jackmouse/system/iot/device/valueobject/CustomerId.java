package com.jackmouse.system.iot.device.valueobject;

import com.jackmouse.system.blog.domain.valueobject.BaseId;

import java.util.UUID;

/**
 * @ClassName CustomerId
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/28 17:20
 * @Version 1.0
 **/
public class CustomerId extends BaseId<UUID> {
    public CustomerId(UUID value) {
        super(value);
    }
}
