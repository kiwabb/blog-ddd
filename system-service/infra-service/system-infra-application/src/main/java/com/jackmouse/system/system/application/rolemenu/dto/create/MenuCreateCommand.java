package com.jackmouse.system.system.application.rolemenu.dto.create;

import com.jackmouse.system.blog.domain.valueobject.*;
import com.jackmouse.system.system.infra.domain.rolemenu.entity.Menu;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @ClassName MenuCreateCommand
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/18 13:35
 * @Version 1.0
 **/
@Data
@Builder
@EqualsAndHashCode
public class MenuCreateCommand {
    private final Long parentId;
    private final String name;
    private final String path;
    private final String component;
    private final String componentName;
    private final MenuType type;
    private final String icon;
    private final Integer sort;

    public Menu toMenu() {
        return Menu.builder()
                .parentId(new MenuId(parentId))
                .name(new MenuName(name))
                .path(new MenuPath(path))
                .component(new MenuComponent(component, componentName))
                .type(type)
                .icon(new MenuIcon(icon))
                .sort(new MenuSort(sort))
                .build();
    }
}
