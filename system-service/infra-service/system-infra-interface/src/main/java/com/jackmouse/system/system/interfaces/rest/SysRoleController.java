package com.jackmouse.system.system.interfaces.rest;

import com.jackmouse.system.response.PageResult;
import com.jackmouse.system.response.Result;
import com.jackmouse.system.system.application.rolemenu.dto.create.AssignMenuCommand;
import com.jackmouse.system.system.application.rolemenu.dto.create.RoleCreateCommand;
import com.jackmouse.system.system.application.rolemenu.dto.query.RoleDetailResponse;
import com.jackmouse.system.system.application.rolemenu.dto.query.RolePageQuery;
import com.jackmouse.system.system.application.rolemenu.dto.query.RoleResponse;
import com.jackmouse.system.system.application.rolemenu.dto.remove.RoleRemoveCommand;
import com.jackmouse.system.system.application.rolemenu.dto.update.RoleUpdateCommand;
import com.jackmouse.system.system.application.rolemenu.ports.input.service.SysInfraRoleMenuApplicationService;
import com.jackmouse.system.system.application.rolemenu.dto.create.AssignUserCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName SysRoleController
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/18 14:44
 * @Version 1.0
 **/
@Tag(name = "角色管理接口", description = "提供角色相关的所有操作接口")
@RestController
@RequestMapping( value = "/admin/role", produces = "application/json")
public class SysRoleController {
    private final SysInfraRoleMenuApplicationService sysInfraRoleMenuApplicationService;

    public SysRoleController(SysInfraRoleMenuApplicationService sysInfraRoleMenuApplicationService) {
        this.sysInfraRoleMenuApplicationService = sysInfraRoleMenuApplicationService;
    }

    @Operation(
            summary = "分页查询角色列表",
            description = "分页查询角色列表",
            method = "GET"
    )
    @GetMapping("/page")
    public PageResult<RoleResponse> queryRolePage(RolePageQuery query) {
        return sysInfraRoleMenuApplicationService.queryRolePage(query);
    }

    @Operation(
            summary = "查询角色详情",
            description = "查询角色详情",
            method = "GET"
    )
    @GetMapping("/{id}")
    public Result<RoleDetailResponse> queryRoleById(@PathVariable("id") Long id) {
        return Result.succeed(sysInfraRoleMenuApplicationService.queryRoleById(id));
    }

    @Operation(
            summary = "创建角色",
            description = "创建角色",
            method = "POST"
    )
    @PostMapping
    public Result<Void> createRole(@RequestBody @Valid RoleCreateCommand command) {
        sysInfraRoleMenuApplicationService.createRole(command);
        return Result.succeed(null);
    }

    @Operation(
            summary = "分配菜单",
            description = "分配菜单",
            method = "POST"
    )
    @PostMapping("/assignMenu")
    public Result<Void> assignMenu(@RequestBody @Valid AssignMenuCommand command) {
        sysInfraRoleMenuApplicationService.assignMenu(command);
        return Result.succeed(null);
    }

    @Operation(
            summary = "分配用户",
            description = "分配用户",
            method = "POST"
    )
    @PostMapping("/assignUser")
    public Result<Void> assignUser(@RequestBody @Valid AssignUserCommand command) {
        sysInfraRoleMenuApplicationService.assignUser(command);
        return Result.succeed(null);
    }

    @Operation(
            summary = "更新角色",
            description = "更新角色",
            method = "PUT"
    )
    @PutMapping
    public Result<Void> updateRole(@RequestBody @Valid RoleUpdateCommand command) {
        sysInfraRoleMenuApplicationService.updateRole(command);
        return Result.succeed(null);
    }

    @Operation(
            summary = "删除角色",
            description = "删除角色",
            method = "DELETE"
    )
    @DeleteMapping
    public Result<Void> removeRole(@RequestBody @Valid RoleRemoveCommand command) {
        sysInfraRoleMenuApplicationService.removeRole(command);
        return Result.succeed(null);
    }
}
