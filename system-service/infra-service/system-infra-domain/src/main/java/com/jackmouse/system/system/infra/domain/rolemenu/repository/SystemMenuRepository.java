package com.jackmouse.system.system.infra.domain.rolemenu.repository;

import com.jackmouse.system.blog.domain.valueobject.PageResult;
import com.jackmouse.system.system.infra.domain.rolemenu.entity.Menu;
import com.jackmouse.system.system.infra.domain.rolemenu.specification.query.MenuPageQuerySpec;
import com.jackmouse.system.system.infra.domain.rolemenu.specification.query.RolePageQuerySpec;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.MenuId;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.RoleId;

import javax.management.relation.Role;
import java.util.List;
import java.util.Optional;


/**
 * @ClassName SystemRoleRepository
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/18 10:19
 * @Version 1.0
 **/
public interface SystemMenuRepository {
    PageResult<Menu> findPage(MenuPageQuerySpec query);

    Optional<Menu> findById(MenuId roleId);

    void save(Menu menu);

    void remove(List<Menu> menu);
}
