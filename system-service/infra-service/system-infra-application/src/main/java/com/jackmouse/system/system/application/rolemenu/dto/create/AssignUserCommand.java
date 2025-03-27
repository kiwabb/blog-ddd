package com.jackmouse.system.system.application.rolemenu.dto.create;

import com.jackmouse.system.blog.domain.valueobject.UserId;
import com.jackmouse.system.system.infra.domain.rolemenu.entity.Role;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.RoleId;
import com.jackmouse.system.system.infra.domain.user.entity.User;
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
public class AssignUserCommand {
    private final Long roleId;
    private final List<Long> userIds;

    public Role toRole() {
        return Role.builder()
                .id(new RoleId(roleId))
                .users(userIds.stream()
                        .map(menuId -> User.builder().id(new UserId(menuId)).build()).toList())
                .build();
    }
}
