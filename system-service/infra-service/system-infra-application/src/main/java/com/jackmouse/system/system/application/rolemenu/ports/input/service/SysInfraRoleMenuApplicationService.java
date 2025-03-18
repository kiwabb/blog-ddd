package com.jackmouse.system.system.application.rolemenu.ports.input.service;

import com.jackmouse.system.response.PageResult;
import com.jackmouse.system.system.application.rolemenu.dto.create.MenuCreateCommand;
import com.jackmouse.system.system.application.rolemenu.dto.create.RoleCreateCommand;
import com.jackmouse.system.system.application.rolemenu.dto.query.*;
import com.jackmouse.system.system.application.rolemenu.dto.remove.MenuRemoveCommand;
import com.jackmouse.system.system.application.rolemenu.dto.remove.RoleRemoveCommand;
import com.jackmouse.system.system.application.rolemenu.dto.update.MenuUpdateCommand;
import com.jackmouse.system.system.application.rolemenu.dto.update.RoleUpdateCommand;
import com.jackmouse.system.system.application.user.dto.create.UserCreateCommand;
import com.jackmouse.system.system.application.user.dto.query.UserDetailResponse;
import com.jackmouse.system.system.application.user.dto.query.UserPageQuery;
import com.jackmouse.system.system.application.user.dto.query.UserResponse;
import com.jackmouse.system.system.application.user.dto.remove.UserRemoveCommand;
import com.jackmouse.system.system.application.user.dto.update.UserUpdateCommand;

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

    void createRole(MenuCreateCommand command);

    void updateRole(MenuUpdateCommand command);

    void removeRole(MenuRemoveCommand commands);

    MenuDetailResponse queryMenuById(Long id);
}
