package com.jackmouse.system.system.infra.domain.user.valueobject;

import com.jackmouse.system.blog.domain.valueobject.BaseId;

/**
 * @ClassName UserId
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/13 15:44
 * @Version 1.0
 **/
public class UserId extends BaseId<Long> {
    public UserId(Long value) {
        super(value);
    }
}
