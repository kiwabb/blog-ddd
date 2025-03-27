package com.jackmouse.system.system.application.rolemenu;

import com.jackmouse.system.response.PageResult;
import com.jackmouse.system.system.application.rolemenu.dto.create.AssignMenuCommand;
import com.jackmouse.system.system.application.rolemenu.dto.create.MenuCreateCommand;
import com.jackmouse.system.system.application.rolemenu.dto.create.RoleCreateCommand;
import com.jackmouse.system.system.application.rolemenu.dto.query.*;
import com.jackmouse.system.system.application.rolemenu.dto.remove.MenuRemoveCommand;
import com.jackmouse.system.system.application.rolemenu.dto.remove.RoleRemoveCommand;
import com.jackmouse.system.system.application.rolemenu.dto.update.MenuUpdateCommand;
import com.jackmouse.system.system.application.rolemenu.dto.update.RoleUpdateCommand;
import com.jackmouse.system.system.application.rolemenu.ports.input.service.SysInfraRoleMenuApplicationService;
import com.jackmouse.system.system.application.rolemenu.dto.create.AssignUserCommand;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName SysInfraRoleMenuApplicationServiceImpl
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/18 13:30
 * @Version 1.0
 **/
@Valid
@Service
public class SysInfraRoleMenuApplicationServiceImpl implements SysInfraRoleMenuApplicationService {

    private final SysInfraRoleMenuQueryCommandHandler sysInfraRoleMenuQueryCommandHandler;
    private final SysInfraRoleModifyCommandHandler sysInfraRoleMenuModifyCommandHandler;

    public SysInfraRoleMenuApplicationServiceImpl(SysInfraRoleMenuQueryCommandHandler sysInfraRoleMenuQueryCommandHandler, SysInfraRoleModifyCommandHandler sysInfraRoleMenuModifyCommandHandler) {
        this.sysInfraRoleMenuQueryCommandHandler = sysInfraRoleMenuQueryCommandHandler;
        this.sysInfraRoleMenuModifyCommandHandler = sysInfraRoleMenuModifyCommandHandler;
    }


    @Override
    public PageResult<RoleResponse> queryRolePage(RolePageQuery query) {
        return sysInfraRoleMenuQueryCommandHandler.queryRolePage(query);
    }

    @Override
    public void createRole(RoleCreateCommand command) {
        sysInfraRoleMenuModifyCommandHandler.createRole(command);
    }

    @Override
    public void updateRole(RoleUpdateCommand command) {
        sysInfraRoleMenuModifyCommandHandler.updateRole(command);
    }

    @Override
    public void removeRole(RoleRemoveCommand commands) {
        sysInfraRoleMenuModifyCommandHandler.removeRole(commands);
    }

    @Override
    public RoleDetailResponse queryRoleById(Long id) {
        return sysInfraRoleMenuQueryCommandHandler.queryRoleById(id);
    }

    @Override
    public PageResult<MenuResponse> queryMenuPage(MenuPageQuery query) {
        return sysInfraRoleMenuQueryCommandHandler.queryMenuPage(query);
    }

    @Override
    public void createMenu(MenuCreateCommand command) {
        sysInfraRoleMenuModifyCommandHandler.createMenu(command);
    }

    @Override
    public void updateMenu(MenuUpdateCommand command) {
        sysInfraRoleMenuModifyCommandHandler.updateMenu(command);
    }

    @Override
    public void removeMenu(MenuRemoveCommand commands) {
        sysInfraRoleMenuModifyCommandHandler.removeMenu(commands);
    }

    @Override
    public MenuDetailResponse queryMenuById(Long id) {
        return sysInfraRoleMenuQueryCommandHandler.queryMenuById(id);
    }

    @Override
    public List<MenuResponse> queryMenuByType(String type) {
        return sysInfraRoleMenuQueryCommandHandler.queryMenuByType(type);
    }

    @Override
    public List<MenuBindRoleResponse> queryMenuBindRole(Long roleId) {
        return sysInfraRoleMenuQueryCommandHandler.queryMenuBindRole(roleId);
    }

    @Override
    public void assignMenu(AssignMenuCommand command) {
        sysInfraRoleMenuModifyCommandHandler.assignMenu(command);
    }

    @Override
    public void assignUser(AssignUserCommand command) {
        sysInfraRoleMenuModifyCommandHandler.assignUser(command);
    }
}
