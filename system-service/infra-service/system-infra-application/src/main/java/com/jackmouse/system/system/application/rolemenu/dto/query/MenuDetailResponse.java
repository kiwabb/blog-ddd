package com.jackmouse.system.system.application.rolemenu.dto.query;

import com.jackmouse.system.blog.domain.valueobject.*;
import com.jackmouse.system.system.infra.domain.rolemenu.entity.Menu;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.MenuType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;

/**
 * @ClassName MenuDetailResponse
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/18 13:35
 * @Version 1.0
 **/
@Builder
@Data
@EqualsAndHashCode
public class MenuDetailResponse {
    @Schema(description = "菜单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private final Long id;
    @Schema(description = "父菜单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private final Long parentId;
    @Schema(description = "菜单名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "菜单名称")
    private final String name;
    @Schema(description = "菜单路径", requiredMode = Schema.RequiredMode.REQUIRED, example = "菜单路径")
    private final String path;
    @Schema(description = "组件路径", requiredMode = Schema.RequiredMode.REQUIRED, example = "组件路径")
    private final String component;
    @Schema(description = "组件名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "组件名称")
    private final String componentName;
    @Schema(description = "菜单类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "菜单类型")
    private final String type;
    @Schema(description = "菜单图标", requiredMode = Schema.RequiredMode.REQUIRED, example = "菜单图标")
    private final String icon;
    @Schema(description = "菜单排序", requiredMode = Schema.RequiredMode.REQUIRED, example = "菜单排序")
    private final Integer sort;
    @Schema(description = "版本号", requiredMode = Schema.RequiredMode.REQUIRED, example = "版本号")
    private final Long version;
    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED, example = "创建时间")
    private final OffsetDateTime createdAt;
    @Schema(description = "创建人", requiredMode = Schema.RequiredMode.REQUIRED, example = "创建人")
    private final Long createdBy;
    @Schema(description = "更新时间", requiredMode = Schema.RequiredMode.REQUIRED, example = "更新时间")
    private final OffsetDateTime updatedAt;
    @Schema(description = "更新人", requiredMode = Schema.RequiredMode.REQUIRED, example = "更新人")
    private final Long updatedBy;

    public static MenuDetailResponse fromMenu(Menu menu) {
        return MenuDetailResponse.builder()
                .id(menu.getId().getValue())
                .parentId(menu.getParentId().getValue())
                .name(menu.getName().value())
                .path(menu.getPath().value())
                .component(menu.getComponent().component())
                .componentName(menu.getComponent().componentName())
                .type(menu.getType().name())
                .icon(menu.getIcon().value())
                .sort(menu.getSort().value())
                .version(menu.getVersion().getValue())
                .createdAt(menu.getCreatedAt().value())
                .createdBy(menu.getCreatedBy().value())
                .updatedAt(menu.getUpdatedAt().value())
                .updatedBy(menu.getUpdatedBy().value())
                .build();
    }
}
