package com.jackmouse.system.system.interfaces.rest;

import com.jackmouse.system.response.PageResult;
import com.jackmouse.system.response.Result;
import com.jackmouse.system.system.application.rolemenu.dto.create.MenuCreateCommand;
import com.jackmouse.system.system.application.rolemenu.dto.query.MenuBindRoleResponse;
import com.jackmouse.system.system.application.rolemenu.dto.query.MenuDetailResponse;
import com.jackmouse.system.system.application.rolemenu.dto.query.MenuPageQuery;
import com.jackmouse.system.system.application.rolemenu.dto.query.MenuResponse;
import com.jackmouse.system.system.application.rolemenu.dto.remove.MenuRemoveCommand;
import com.jackmouse.system.system.application.rolemenu.dto.update.MenuUpdateCommand;
import com.jackmouse.system.system.application.rolemenu.ports.input.service.SysInfraRoleMenuApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName SysMenuController
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/18 14:44
 * @Version 1.0
 **/
@Tag(name = "菜单管理接口", description = "提供菜单相关的所有操作接口")
@RestController
@RequestMapping( value = "/admin/menu", produces = "application/json")
public class SysMenuController {
    private final SysInfraRoleMenuApplicationService sysInfraRoleMenuApplicationService;

    public SysMenuController(SysInfraRoleMenuApplicationService sysInfraRoleMenuApplicationService) {
        this.sysInfraRoleMenuApplicationService = sysInfraRoleMenuApplicationService;
    }

    @Operation(
            summary = "分页查询菜单列表",
            description = "分页查询菜单列表",
            method = "GET"
    )
    @GetMapping("/page")
    public PageResult<MenuResponse> queryMenuPage(MenuPageQuery query) {
        return sysInfraRoleMenuApplicationService.queryMenuPage(query);
    }

    @Operation(
            summary = "查询菜单详情",
            description = "查询菜单详情",
            method = "GET"
    )
    @GetMapping("/{id}")
    public Result<MenuDetailResponse> queryMenuById(@PathVariable("id") Long id) {
        return Result.succeed(sysInfraRoleMenuApplicationService.queryMenuById(id));
    }

    @Operation(
            summary = "根据菜单类型查询菜单",
            description = "根据菜单类型查询菜单",
            method = "GET"
    )
    @GetMapping("/type")
    public Result<List<MenuResponse>> queryMenuByType(@RequestParam("type") @NotNull String type) {
        return Result.succeed(sysInfraRoleMenuApplicationService.queryMenuByType(type));
    }

    @Operation(
            summary = "查询菜单树",
            description = "查询菜单树对应角色id的绑定菜单",
            method = "GET"
    )
    @GetMapping("/bindRole/{roleId}")
    public Result<List<MenuBindRoleResponse>> queryMenuBindRole(@PathVariable("roleId") @NotNull Long roleId) {
        return Result.succeed(sysInfraRoleMenuApplicationService.queryMenuBindRole(roleId));
    }

    @Operation(
            summary = "创建菜单",
            description = "创建菜单",
            method = "POST"
    )
    @PostMapping
    public Result<Void> createMenu(@RequestBody @Valid MenuCreateCommand command) {
        sysInfraRoleMenuApplicationService.createMenu(command);
        return Result.succeed(null);
    }

    @Operation(
            summary = "更新菜单",
            description = "更新菜单",
            method = "PUT"
    )
    @PutMapping
    public Result<Void> updateMenu(@RequestBody @Valid MenuUpdateCommand command) {
        sysInfraRoleMenuApplicationService.updateMenu(command);
        return Result.succeed(null);
    }

    @Operation(
            summary = "删除菜单",
            description = "删除菜单",
            method = "DELETE"
    )
    @DeleteMapping
    public Result<Void> removeMenu(@RequestBody @Valid MenuRemoveCommand command) {
        sysInfraRoleMenuApplicationService.removeMenu(command);
        return Result.succeed(null);
    }
}
