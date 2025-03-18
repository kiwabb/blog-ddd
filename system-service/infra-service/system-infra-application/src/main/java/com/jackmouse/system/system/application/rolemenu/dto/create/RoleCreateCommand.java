package com.jackmouse.system.system.application.rolemenu.dto.create;

import com.jackmouse.system.system.infra.domain.rolemenu.entity.Role;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.Enabled;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.RoleCode;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.RoleDataScope;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.RoleName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName RoleCreateCommand
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/18 13:35
 * @Version 1.0
 **/
@Data
@Builder
@EqualsAndHashCode
public class RoleCreateCommand {
    private final String code;
    private final String name;
    private final Boolean enabled;
    private final String dataScope;

    public Role toRole() {
        return Role.builder()
                .code(new RoleCode(code))
                .name(new RoleName(name))
                .enabled(new Enabled(enabled))
                .dataScope(new RoleDataScope("TODO"))
                .build();
    }
}
