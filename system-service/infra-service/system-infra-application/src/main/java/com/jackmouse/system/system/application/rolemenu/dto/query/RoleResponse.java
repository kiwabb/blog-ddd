package com.jackmouse.system.system.application.rolemenu.dto.query;

import com.jackmouse.system.system.infra.domain.rolemenu.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "角色ID", example = "1")
    private final Long id;
    @Schema(description = "角色编码", example = "1")
    private final String code;
    @Schema(description = "角色名称", example = "1")
    private final String name;
    @Schema(description = "是否启用", example = "1")
    private final Boolean enabled;
    @Schema(description = "数据范围", example = "1")
    private final String dataScope;
    @Schema(description = "创建时间", example = "2025-03-18 13:34:00")
    private final OffsetDateTime createdAt;
    @Schema(description = "创建人")
    private final Long createdBy;
    @Schema(description = "更新时间", example = "2025-03-18 13:34:00")
    private final OffsetDateTime updatedAt;
    @Schema(description = "更新人")
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
