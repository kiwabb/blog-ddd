package com.jackmouse.system.iot.device.valueobject;

import com.jackmouse.system.blog.domain.valueobject.BaseId;

import java.util.UUID;

/**
 * @ClassName DefaultRuleChainId
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/28 14:56
 * @Version 1.0
 **/
public class DefaultRuleChainId extends BaseId<UUID> {
    public DefaultRuleChainId(UUID value) {
        super(value);
    }
}
