package com.jackmouse.system.system.application.rolemenu.dto.query;

import com.jackmouse.system.system.infra.domain.rolemenu.entity.Menu;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * @ClassName MenuBindRoleResponse
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/19 10:31
 * @Version 1.0
 **/
@Builder
@Data
@EqualsAndHashCode
public class MenuBindRoleResponse {
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
    private final String type;
    @Schema(description = "菜单图标", example = "菜单图标")
    private final String icon;
    @Schema(description = "菜单排序", example = "菜单排序")
    private final Integer sort;
    @Schema(description = "是否隐藏", example = "true")
    private final Boolean hidden;
    @Schema(description = "是否选中", example = "true")
    private final Boolean checked;


    public static MenuBindRoleResponse fromMenu(Menu menu) {
        return MenuBindRoleResponse.builder()
                .id(menu.getId().getValue())
                .parentId(menu.getParentId().getValue())
                .name(menu.getName().value())
                .path(menu.getPath().value())
                .component(menu.getComponent().component())
                .componentName(menu.getComponent().componentName())
                .type(menu.getType().name())
                .icon(menu.getIcon().value())
                .sort(menu.getSort().value())
                .hidden(menu.getHidden().value())
                .checked(menu.getMenuChecked().value())
                .build();
    }

    public static List<MenuBindRoleResponse> fromMenuList(List<Menu> data) {
        return data.stream().map(MenuBindRoleResponse::fromMenu).toList();
    }
}
