package com.jackmouse.system.system.application.rolemenu.dto.remove;

import com.jackmouse.system.system.infra.domain.rolemenu.entity.Menu;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.MenuId;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @ClassName MenuRemoveCommand
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/18 13:36
 * @Version 1.0
 **/
@Data
@Builder
@EqualsAndHashCode
public class MenuRemoveCommand {
    private final List<Long> ids;

    public List<Menu> toMenus() {
        return ids.stream()
                .map(MenuId::new)
                .map(id -> Menu.builder().id(id).build())
                .toList();
    }
}
