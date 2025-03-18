package com.jackmouse.system.system.application.rolemenu.dto.query;

import com.jackmouse.system.system.infra.domain.rolemenu.entity.Menu;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.MenuType;
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
    private final Long id;
    private final Long parentId;
    private final String name;
    private final String path;
    private final String component;
    private final String componentName;
    private final MenuType type;
    private final String icon;
    private final Integer sort;
    private final OffsetDateTime createdAt;
    private final Long createdBy;
    private final OffsetDateTime updatedAt;
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
