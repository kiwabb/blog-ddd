package com.jackmouse.system.system.application.rolemenu.dto.query;

import com.jackmouse.system.system.infra.domain.rolemenu.entity.Role;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;

/**
 * @ClassName RoleDetailResponse
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/18 13:34
 * @Version 1.0
 **/
@Builder
@Data
@EqualsAndHashCode
public class RoleDetailResponse {
    private final Long id;
    private final String code;
    private final String name;
    private final Boolean enabled;
    private final String dataScope;
    private final Long version;
    private final OffsetDateTime createdAt;
    private final Long createdBy;
    private final OffsetDateTime updatedAt;
    private final Long updatedBy;

    public static RoleDetailResponse fromRole(Role role) {
        return RoleDetailResponse.builder()
                .id(role.getId().getValue())
                .code(role.getCode().value())
                .name(role.getName().value())
                .enabled(role.getEnabled().value())
                .dataScope(role.getDataScope().value())
                .version(role.getVersion().getValue())
                .createdAt(role.getCreatedAt().value())
                .createdBy(role.getCreatedBy().value())
                .build();
    }
}
