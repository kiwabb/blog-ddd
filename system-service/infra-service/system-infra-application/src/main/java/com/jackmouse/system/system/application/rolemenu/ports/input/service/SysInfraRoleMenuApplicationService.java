package com.jackmouse.system.system.application.rolemenu.ports.input.service;

import com.jackmouse.system.response.PageResult;
import com.jackmouse.system.system.application.rolemenu.dto.create.AssignMenuCommand;
import com.jackmouse.system.system.application.rolemenu.dto.create.MenuCreateCommand;
import com.jackmouse.system.system.application.rolemenu.dto.create.RoleCreateCommand;
import com.jackmouse.system.system.application.rolemenu.dto.query.*;
import com.jackmouse.system.system.application.rolemenu.dto.remove.MenuRemoveCommand;
import com.jackmouse.system.system.application.rolemenu.dto.remove.RoleRemoveCommand;
import com.jackmouse.system.system.application.rolemenu.dto.update.MenuUpdateCommand;
import com.jackmouse.system.system.application.rolemenu.dto.update.RoleUpdateCommand;
import com.jackmouse.system.system.application.rolemenu.dto.create.AssignUserCommand;

import java.util.List;

/**
 * @ClassName SysInfraRoleMenuApplicationService
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/18 13:25
 * @Version 1.0
 **/
public interface SysInfraRoleMenuApplicationService {
    PageResult<RoleResponse> queryRolePage(RolePageQuery query);

    void createRole(RoleCreateCommand command);

    void updateRole(RoleUpdateCommand command);

    void removeRole(RoleRemoveCommand commands);

    RoleDetailResponse queryRoleById(Long id);

    PageResult<MenuResponse> queryMenuPage(MenuPageQuery query);

    void createMenu(MenuCreateCommand command);

    void updateMenu(MenuUpdateCommand command);

    void removeMenu(MenuRemoveCommand commands);

    MenuDetailResponse queryMenuById(Long id);

    List<MenuResponse> queryMenuByType(String type);

    List<MenuBindRoleResponse> queryMenuBindRole(Long roleId);

    void assignMenu(AssignMenuCommand command);

    void assignUser(AssignUserCommand command);
}
