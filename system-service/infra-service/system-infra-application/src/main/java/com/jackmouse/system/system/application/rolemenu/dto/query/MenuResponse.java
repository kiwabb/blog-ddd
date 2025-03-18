package com.jackmouse.system.system.application.rolemenu.dto.query;

import com.jackmouse.system.system.infra.domain.rolemenu.entity.Menu;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.MenuType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * @ClassName MenuResponse
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/18 13:34
 * @Version 1.0
 **/
@Builder
@Data
@EqualsAndHashCode
public class MenuResponse {
    @Schema(description = "菜单ID", example = "1")
    private final Long id;
    @Schema(description = "父菜单ID", example = "1")
    private final Long parentId;
    @Schema(description = "菜单名称", example = "菜单名称")
    private final String name;
    @Schema(description = "菜单路径", example = "菜单路径")
    private final String path;
    @Schema(description = "组件路径", example = "组件路径")
    private final String component;
    @Schema(description = "组件名称", example = "组件名称")
    private final String componentName;
    @Schema(description = "菜单类型", example = "菜单类型")
    private final MenuType type;
    @Schema(description = "菜单图标", example = "菜单图标")
    private final String icon;
    @Schema(description = "菜单排序", example = "菜单排序")
    private final Integer sort;
    @Schema(description = "创建时间", example = "2025-03-18 13:34:00")
    private final OffsetDateTime createdAt;
    @Schema(description = "创建人")
    private final Long createdBy;
    @Schema(description = "更新时间", example = "2025-03-18 13:34:00")
    private final OffsetDateTime updatedAt;
    @Schema(description = "更新人")
    private final Long updatedBy;

    public static MenuResponse fromMenu(Menu menu) {
        return MenuResponse.builder()
                .id(menu.getId().getValue())
                .parentId(menu.getParentId().getValue())
                .name(menu.getName().value())
                .path(menu.getPath().value())
                .component(menu.getComponent().component())
                .componentName(menu.getComponent().componentName())
                .type(menu.getType())
                .icon(menu.getIcon().value())
                .sort(menu.getSort().value())
                .createdAt(menu.getCreatedAt().value())
                .createdBy(menu.getCreatedBy().value())
                .updatedAt(menu.getUpdatedAt().value())
                .updatedBy(menu.getUpdatedBy().value())
                .build();
    }

    public static List<MenuResponse> fromMenuList(List<Menu> data) {
        return data.stream().map(MenuResponse::fromMenu).toList();
    }
}
