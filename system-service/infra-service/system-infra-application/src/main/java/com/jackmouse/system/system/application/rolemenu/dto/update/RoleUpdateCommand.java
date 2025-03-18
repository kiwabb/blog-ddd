package com.jackmouse.system.system.application.rolemenu.dto.update;

import com.jackmouse.system.blog.domain.valueobject.Version;
import com.jackmouse.system.system.infra.domain.rolemenu.entity.Role;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName RoleUpdateCommand
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/18 13:35
 * @Version 1.0
 **/
@Builder
@Data
@EqualsAndHashCode
public class RoleUpdateCommand {
    private final Long id;
    private final String code;
    private final String name;
    private final Boolean enabled;
    private final String dataScope;
    private final Integer version;

    public Role toRole() {
        return Role.builder()
                .id(new RoleId(id))
                .code(new RoleCode(code))
                .name(new RoleName(name))
                .enabled(new Enabled(enabled))
                .dataScope(new RoleDataScope(dataScope))
                .version(new Version(version))
                .build();
    }
}
