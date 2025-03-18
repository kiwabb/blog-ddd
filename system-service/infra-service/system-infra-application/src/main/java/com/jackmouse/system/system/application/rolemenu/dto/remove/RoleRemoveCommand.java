package com.jackmouse.system.system.application.rolemenu.dto.remove;

import com.jackmouse.system.system.infra.domain.rolemenu.entity.Role;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.RoleId;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @ClassName RoleRemoveCommand
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/18 13:36
 * @Version 1.0
 **/
@Data
@Builder
@EqualsAndHashCode
public class RoleRemoveCommand {
    private final List<Long> ids;

    public List<Role> toRoles() {
        return ids.stream().map(RoleId::new).map(id -> Role.builder().id(id).build()).toList();
    }
}
