package com.jackmouse.system.blog.domain.valueobject;

import java.util.UUID;

/**
 * @ClassName OtaPackageId
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/28 15:06
 * @Version 1.0
 **/
public class OtaPackageId extends BaseId<UUID>{
    public OtaPackageId(UUID value) {
        super(value);
    }
}
