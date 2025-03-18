package com.jackmouse.system.system.infra.domain.rolemenu.repository;

import com.jackmouse.system.blog.domain.valueobject.PageResult;
import com.jackmouse.system.system.infra.domain.rolemenu.entity.Role;
import com.jackmouse.system.system.infra.domain.rolemenu.specification.query.RolePageQuerySpec;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.RoleId;


import java.util.List;
import java.util.Optional;


/**
 * @ClassName SystemRoleRepository
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/18 10:19
 * @Version 1.0
 **/
public interface SystemRoleRepository {
    PageResult<Role> findPage(RolePageQuerySpec query);

    Optional<Role> findById(RoleId roleId);

    void save(Role role);

    void remove(List<Role> roles);
}
