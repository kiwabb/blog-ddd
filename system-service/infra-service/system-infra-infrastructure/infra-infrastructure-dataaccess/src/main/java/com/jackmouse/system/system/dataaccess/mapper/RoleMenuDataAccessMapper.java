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
    public Role roleEntityToRole(SysRoleEntity sysRoleEntity) {
        return Role.builder()
                .id(new RoleId(sysRoleEntity.getId()))
                .code(new RoleCode(sysRoleEntity.getCode()))
                .name(new RoleName(sysRoleEntity.getName()))
                .enabled(new Enabled(sysRoleEntity.getEnabled()))
                .dataScope(new RoleDataScope(sysRoleEntity.getDataScope()))
                .version(new Version(sysRoleEntity.getVersion()))
                .createdAt(new CreatedAt(sysRoleEntity.getCreatedAt()))
                .createdBy(new CreatedBy(sysRoleEntity.getCreatedBy()))
                .updatedAt(new UpdatedAt(sysRoleEntity.getUpdatedAt()))
                .updatedBy(new UpdatedBy(sysRoleEntity.getUpdatedBy()))
                .build();
    }

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

    public Menu menuEntityToMenu(SysMenuEntity sysMenuEntity) {
        return Menu.builder()
                .id(new MenuId(sysMenuEntity.getId()))
                .parentId(new MenuId(sysMenuEntity.getParentId()))
                .name(new MenuName(sysMenuEntity.getName()))
                .path(new MenuPath(sysMenuEntity.getPath()))
                .component(new MenuComponent(sysMenuEntity.getComponent(), sysMenuEntity.getComponentName()))
                .hidden(new MenuHidden(sysMenuEntity.getHidden()))
                .icon(new MenuIcon(sysMenuEntity.getIcon()))
                .sort(new MenuSort(sysMenuEntity.getSort()))
                .hidden(new MenuHidden(sysMenuEntity.getHidden()))
                .version(new Version(sysMenuEntity.getVersion()))
                .createdAt(new CreatedAt(sysMenuEntity.getCreatedAt()))
                .createdBy(new CreatedBy(sysMenuEntity.getCreatedBy()))
                .updatedAt(new UpdatedAt(sysMenuEntity.getUpdatedAt()))
                .updatedBy(new UpdatedBy(sysMenuEntity.getUpdatedBy()))
                .type(sysMenuEntity.getType())
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
