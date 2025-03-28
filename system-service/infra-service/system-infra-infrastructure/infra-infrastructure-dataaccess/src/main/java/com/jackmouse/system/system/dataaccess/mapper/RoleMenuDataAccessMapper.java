package com.jackmouse.system.system.dataaccess.mapper;

import com.jackmouse.system.blog.domain.valueobject.*;
import com.jackmouse.system.system.dataaccess.entity.SysMenuEntity;
import com.jackmouse.system.system.dataaccess.entity.SysRoleEntity;
import com.jackmouse.system.system.infra.domain.rolemenu.entity.Menu;
import com.jackmouse.system.system.infra.domain.rolemenu.entity.Role;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.*;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.MenuComponent;
import org.springframework.stereotype.Component;

import java.awt.*;

/**
 * @ClassName RoleDataAccessMapper
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/18 11:17
 * @Version 1.0
 **/
@Component
public class RoleMenuDataAccessMapper {
    public SysRoleEntity roleToRoleEntity(Role role) {
        return SysRoleEntity.builder()
                .id(role.getId() == null ? null : role.getId().getValue())
                .code(role.getCode().value())
                .name(role.getName().value())
                .dataScope(role.getDataScope().value())
                .enabled(role.getEnabled().value())
                .version(role.getVersion() == null ? null : role.getVersion().getValue())
                .tenantId(1L)
                .build();
    }


    public SysMenuEntity menuToMenuEntity(Menu menu) {
        return SysMenuEntity.builder()
                .id(menu.getId() == null ? null : menu.getId().getValue())
                .parentId(menu.getParentId().getValue())
                .name(menu.getName().value())
                .path(menu.getPath().value())
                .component(menu.getComponent().component())
                .componentName(menu.getComponent().componentName())
                .hidden(menu.getHidden().value())
                .icon(menu.getIcon().value())
                .sort(menu.getSort().value())
                .version(menu.getVersion() == null ? null : menu.getVersion().getValue())
                .tenantId(1L)
                .type(menu.getType())
                .build();
    }
}
