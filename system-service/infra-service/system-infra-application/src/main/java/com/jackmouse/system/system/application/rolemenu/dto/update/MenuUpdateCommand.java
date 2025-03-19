package com.jackmouse.system.system.application.rolemenu.dto.update;

import com.jackmouse.system.blog.domain.valueobject.Version;
import com.jackmouse.system.system.infra.domain.rolemenu.entity.Menu;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName MenuUpdateCommand
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/18 13:36
 * @Version 1.0
 **/
@Data
@Builder
@EqualsAndHashCode
public class MenuUpdateCommand {
    private final Long id;
    private final Long parentId;
    private final String name;
    private final String path;
    private final String component;
    private final String componentName;
    private final MenuType type;
    private final String icon;
    private final Integer sort;
    private final Boolean hidden;
    private final Long version;

    public Menu toMenu() {
        return Menu.builder()
                .id(new MenuId(id))
                .parentId(new MenuId(parentId))
                .name(new MenuName(name))
                .path(new MenuPath(path))
                .component(new MenuComponent(component, componentName))
                .type(type)
                .icon(new MenuIcon(icon))
                .sort(new MenuSort(sort))
                .hidden(new MenuHidden(hidden))
                .version(new Version(version))
                .build();
    }
}
