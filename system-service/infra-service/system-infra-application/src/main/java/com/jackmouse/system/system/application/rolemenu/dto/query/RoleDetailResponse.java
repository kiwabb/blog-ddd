package com.jackmouse.system.system.application.rolemenu.dto.query;

import com.jackmouse.system.system.infra.domain.rolemenu.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "角色ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private final Long id;
    @Schema(description = "角色编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private final String code;
    @Schema(description = "角色名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "name")
    private final String name;
    @Schema(description = "是否启用", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private final Boolean enabled;
    @Schema(description = "数据范围", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private final String dataScope;
    @Schema(description = "版本号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private final Long version;
    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED, example = "2025-03-18 13:34:00")
    private final OffsetDateTime createdAt;
    @Schema(description = "创建人", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private final Long createdBy;
    @Schema(description = "更新时间", requiredMode = Schema.RequiredMode.REQUIRED, example = "2025-03-18 13:34:00")
    private final OffsetDateTime updatedAt;
    @Schema(description = "更新人", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
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
