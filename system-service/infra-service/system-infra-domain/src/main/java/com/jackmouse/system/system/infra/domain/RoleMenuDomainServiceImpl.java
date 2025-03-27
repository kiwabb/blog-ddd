package com.jackmouse.system.system.infra.domain;

import com.jackmouse.system.system.infra.domain.rolemenu.RoleMenuDomainService;
import com.jackmouse.system.system.infra.domain.rolemenu.entity.Menu;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.MenuId;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName RoleMenuDomainServiceImpl
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/19 10:45
 * @Version 1.0
 **/
public class RoleMenuDomainServiceImpl implements RoleMenuDomainService {
    @Override
    public List<Menu> generateMenuCheckList(List<Menu> menuList, List<Menu> checkedMenu) {
        Set<MenuId> ids = checkedMenu.stream().map(Menu::getId).collect(Collectors.toSet());
        return menuList.stream().peek(menu -> {
            if (ids.contains(menu.getId())) {
                menu.setChecked();
            }
        }).toList();
    }
}
