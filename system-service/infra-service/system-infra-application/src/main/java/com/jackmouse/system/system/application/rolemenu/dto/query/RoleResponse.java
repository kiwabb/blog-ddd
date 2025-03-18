package com.jackmouse.system.system.application.rolemenu.dto.query;

import com.jackmouse.system.system.infra.domain.rolemenu.entity.Role;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * @ClassName RoleResponse
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/18 13:34
 * @Version 1.0
 **/
@Builder
@Data
@EqualsAndHashCode
public class RoleResponse {
    private final Long id;
    private final String code;
    private final String name;
    private final Boolean enabled;
    private final String dataScope;
    private final OffsetDateTime createdAt;
    private final Long createdBy;
    private final OffsetDateTime updatedAt;
    private final Long updatedBy;

    public static RoleResponse fromRole(Role role) {
        return RoleResponse.builder()
                .id(role.getId().getValue())
                .code(role.getCode().value())
                .name(role.getName().value())
                .enabled(role.getEnabled().value())
                .dataScope(role.getDataScope().value())
                .createdAt(role.getCreatedAt().value())
                .createdBy(role.getCreatedBy().value())
                .build();
    }

    public static List<RoleResponse> fromRoleList(List<Role> data) {
        return data.stream().map(RoleResponse::fromRole).toList();
    }
}
