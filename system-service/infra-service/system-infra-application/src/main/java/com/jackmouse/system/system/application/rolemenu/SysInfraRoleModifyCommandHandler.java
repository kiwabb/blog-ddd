package com.jackmouse.system.system.application.rolemenu;

import com.jackmouse.system.system.application.rolemenu.dto.create.AssignMenuCommand;
import com.jackmouse.system.system.application.rolemenu.dto.create.MenuCreateCommand;
import com.jackmouse.system.system.application.rolemenu.dto.create.RoleCreateCommand;
import com.jackmouse.system.system.application.rolemenu.dto.remove.MenuRemoveCommand;
import com.jackmouse.system.system.application.rolemenu.dto.remove.RoleRemoveCommand;
import com.jackmouse.system.system.application.rolemenu.dto.update.MenuUpdateCommand;
import com.jackmouse.system.system.application.rolemenu.dto.update.RoleUpdateCommand;
import com.jackmouse.system.system.application.rolemenu.dto.create.AssignUserCommand;
import com.jackmouse.system.system.infra.domain.rolemenu.repository.SystemMenuRepository;
import com.jackmouse.system.system.infra.domain.rolemenu.repository.SystemRoleRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName SysInfraRoleMenuQueryCommandHandler
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/18 14:02
 * @Version 1.0
 **/
@Component
public class SysInfraRoleModifyCommandHandler {

    private final SystemMenuRepository systemMenuRepository;
    private final SystemRoleRepository systemRoleRepository;

    public SysInfraRoleModifyCommandHandler(SystemMenuRepository systemMenuRepository, SystemRoleRepository systemRoleRepository) {
        this.systemMenuRepository = systemMenuRepository;
        this.systemRoleRepository = systemRoleRepository;
    }

    @Transactional
    public void removeMenu(MenuRemoveCommand commands) {
        systemMenuRepository.remove(commands.toMenus());
    }
    @Transactional
    public void updateMenu(MenuUpdateCommand command) {
        systemMenuRepository.save(command.toMenu());
    }
    @Transactional
    public void createMenu(MenuCreateCommand command) {
        systemMenuRepository.save(command.toMenu());
    }
    @Transactional
    public void removeRole(RoleRemoveCommand commands) {
        systemRoleRepository.remove(commands.toRoles());
    }
    @Transactional
    public void updateRole(RoleUpdateCommand command) {
        systemRoleRepository.save(command.toRole());
    }
    @Transactional
    public void createRole(RoleCreateCommand command) {
        systemRoleRepository.save(command.toRole());
    }

    @Transactional
    public void assignMenu(AssignMenuCommand command) {
        systemRoleRepository.assignMenu(command.toRole());
    }
    @Transactional
    public void assignUser(AssignUserCommand command) {
        systemRoleRepository.assignUser(command.toRole());
    }
}
