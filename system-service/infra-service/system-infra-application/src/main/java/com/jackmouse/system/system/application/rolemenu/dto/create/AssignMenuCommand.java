package com.jackmouse.system.system.application.rolemenu.dto.create;

import com.jackmouse.system.system.infra.domain.rolemenu.entity.Menu;
import com.jackmouse.system.system.infra.domain.rolemenu.entity.Role;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.MenuId;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.RoleId;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @ClassName AssignMenuCommand
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/27 14:44
 * @Version 1.0
 **/
@Data
@Builder
@EqualsAndHashCode
public class AssignMenuCommand {
    private final Long roleId;
    private final List<Long> menuIds;

    public Role toRole() {
        return Role.builder()
                .id(new RoleId(roleId))
                .menus(menuIds.stream()
                        .map(menuId -> Menu.builder().id(new MenuId(menuId)).build()).toList())
                .build();
    }
}
