package com.jackmouse.system.system.infra.domain.rolemenu;

import com.jackmouse.system.system.infra.domain.rolemenu.entity.Menu;

import java.util.List;

/**
 * @ClassName RoleMenuDomianService
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/19 10:44
 * @Version 1.0
 **/
public interface RoleMenuDomainService {
    List<Menu> generateMenuCheckList(List<Menu> menuList, List<Menu> checkedMenu);
}
