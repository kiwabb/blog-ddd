package com.jackmouse.system.system.infra.domain.user.repository;

import com.jackmouse.system.blog.domain.valueobject.PageResult;
import com.jackmouse.system.system.infra.domain.user.entity.User;
import com.jackmouse.system.system.infra.domain.user.specification.query.UserPageQuerySpec;

/**
 * @ClassName SystemUserRepository
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/13 14:56
 * @Version 1.0
 **/
public interface SystemUserRepository {

    PageResult<User> findPage(UserPageQuerySpec query);
}
