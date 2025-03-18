package com.jackmouse.system.system.application.rolemenu.dto.query;

import com.jackmouse.system.blog.domain.valueobject.*;
import com.jackmouse.system.system.infra.domain.rolemenu.entity.Menu;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.MenuType;
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
    private final Long id;
    private final Long parentId;
    private final String name;
    private final String path;
    private final String component;
    private final String componentName;
    private final MenuType type;
    private final String icon;
    private final Integer sort;
    private final Long version;
    private final OffsetDateTime createdAt;
    private final Long createdBy;
    private final OffsetDateTime updatedAt;
    private final Long updatedBy;

    public static MenuDetailResponse fromMenu(Menu menu) {
        return MenuDetailResponse.builder()
                .id(menu.getId().getValue())
                .parentId(menu.getParentId().getValue())
                .name(menu.getName().value())
                .path(menu.getPath().value())
                .component(menu.getComponent().component())
                .componentName(menu.getComponent().componentName())
                .type(menu.getType())
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
