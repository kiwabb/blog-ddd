package com.jackmouse.system.system.application.rolemenu;

import com.jackmouse.system.response.PageResult;
import com.jackmouse.system.system.application.rolemenu.dto.create.MenuCreateCommand;
import com.jackmouse.system.system.application.rolemenu.dto.create.RoleCreateCommand;
import com.jackmouse.system.system.application.rolemenu.dto.query.*;
import com.jackmouse.system.system.application.rolemenu.dto.remove.MenuRemoveCommand;
import com.jackmouse.system.system.application.rolemenu.dto.remove.RoleRemoveCommand;
import com.jackmouse.system.system.application.rolemenu.dto.update.MenuUpdateCommand;
import com.jackmouse.system.system.application.rolemenu.dto.update.RoleUpdateCommand;
import com.jackmouse.system.system.infra.domain.exception.SysNotfoundException;
import com.jackmouse.system.system.infra.domain.rolemenu.RoleMenuDomainService;
import com.jackmouse.system.system.infra.domain.rolemenu.entity.Menu;
import com.jackmouse.system.system.infra.domain.rolemenu.entity.Role;
import com.jackmouse.system.system.infra.domain.rolemenu.repository.SystemMenuRepository;
import com.jackmouse.system.system.infra.domain.rolemenu.repository.SystemRoleRepository;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.MenuId;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.MenuType;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.RoleId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName SysInfraRoleMenuQueryCommandHandler
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/18 14:02
 * @Version 1.0
 **/
@Component
public class SysInfraRoleMenuQueryCommandHandler {

    private final RoleMenuDomainService roleMenuDomainService;
    private final SystemMenuRepository systemMenuRepository;
    private final SystemRoleRepository systemRoleRepository;

    public SysInfraRoleMenuQueryCommandHandler(RoleMenuDomainService roleMenuDomainService, SystemMenuRepository systemMenuRepository, SystemRoleRepository systemRoleRepository) {
        this.roleMenuDomainService = roleMenuDomainService;
        this.systemMenuRepository = systemMenuRepository;
        this.systemRoleRepository = systemRoleRepository;
    }

    @Transactional(readOnly = true)
    public PageResult<RoleResponse> queryRolePage(RolePageQuery query) {
        com.jackmouse.system.blog.domain.valueobject.PageResult<Role> page =
                systemRoleRepository.findPage(query.toRolePageQuery());
        return new PageResult<>(page.totalPages(), page.currentPage(), RoleResponse.fromRoleList(page.data()));
    }

    @Transactional(readOnly = true)
    public RoleDetailResponse queryRoleById(Long id) {
        Optional<Role> role = systemRoleRepository.findById(new RoleId(id));
        if (role.isEmpty()) {
            throw new SysNotfoundException("Could not find role with id: " + id + "!");
        }
        return RoleDetailResponse.fromRole(role.get());
    }
    @Transactional(readOnly = true)
    public PageResult<MenuResponse> queryMenuPage(MenuPageQuery query) {
        com.jackmouse.system.blog.domain.valueobject.PageResult<com.jackmouse.system.system.infra.domain.rolemenu.entity.Menu> page =
                systemMenuRepository.findPage(query.toMenuPageQuery());
        return new PageResult<>(page.totalPages(), page.currentPage(), MenuResponse.fromMenuList(page.data()));
    }

    @Transactional(readOnly = true)
    public MenuDetailResponse queryMenuById(Long id) {
        Optional<Menu> menu = systemMenuRepository.findById(new MenuId(id));
        if (menu.isEmpty()) {
            throw new SysNotfoundException("Could not find menu with id: " + id + "!");
        }
        return MenuDetailResponse.fromMenu(menu.get());
    }
    @Transactional(readOnly = true)
    public List<MenuResponse> queryMenuByType(String type) {
        return MenuResponse.fromMenuList(systemMenuRepository.findByType(MenuType.valueOf(type)));
    }
    @Transactional(readOnly = true)
    public List<MenuBindRoleResponse> queryMenuBindRole(Long roleId) {
        List<Menu> checkMenus = systemMenuRepository.findByRoleId(new RoleId(roleId));
        List<Menu> allMenus = systemMenuRepository.findAll();
        return MenuBindRoleResponse.fromMenuList(
                roleMenuDomainService.generateMenuCheckList(allMenus, checkMenus));
    }
}
