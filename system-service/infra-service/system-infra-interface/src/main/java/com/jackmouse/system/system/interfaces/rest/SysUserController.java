package com.jackmouse.system.system.interfaces.rest;

import com.jackmouse.system.response.Result;
import com.jackmouse.system.response.PageResult;
import com.jackmouse.system.system.application.user.dto.create.UserCreateCommand;
import com.jackmouse.system.system.application.user.dto.query.UserDetailResponse;
import com.jackmouse.system.system.application.user.dto.query.UserPageQuery;
import com.jackmouse.system.system.application.user.dto.query.UserResponse;
import com.jackmouse.system.system.application.user.dto.remove.UserRemoveCommand;
import com.jackmouse.system.system.application.user.dto.update.UserUpdateCommand;
import com.jackmouse.system.system.application.user.ports.input.service.SysInfraUserApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName SysUserController
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/17 09:24
 * @Version 1.0
 **/
@Tag(name = "用户管理接口", description = "提供用户相关的所有操作接口")
@Slf4j
@RestController
@RequestMapping( value = "/admin/user", produces = "application/json")
public class SysUserController {

    private final SysInfraUserApplicationService sysInfraUserApplicationService;

    public SysUserController(SysInfraUserApplicationService sysInfraUserApplicationService) {
        this.sysInfraUserApplicationService = sysInfraUserApplicationService;
    }

    @Operation(
            summary = "分页查询用户列表",
            description = "分页查询用户列表",
            method = "GET"
    )
    @GetMapping("/page")
    public PageResult<UserResponse> queryUserPage(UserPageQuery query) {
        return sysInfraUserApplicationService.queryUserPage(query);
    }

    @Operation(
            summary = "查询已分配用户列表",
            description = "查询已分配用户列表",
            method = "GET"
    )
    @GetMapping("/getAssignUser")
    public PageResult<UserResponse> getAssignUser(UserPageQuery query) {
        query.setIsBindRole(true);
        return sysInfraUserApplicationService.queryAssignUser(query);
    }

    @Operation(
            summary = "查询未分配用户列表",
            description = "查询未分配用户列表",
            method = "GET"
    )
    @GetMapping("/getUnAssignUser")
    public PageResult<UserResponse> getUnAssignUser(UserPageQuery query) {
        query.setIsBindRole(false);
        return sysInfraUserApplicationService.queryUnAssignUser(query);
    }

    @Operation(
            summary = "查询用户详情",
            description = "查询用户详情",
            method = "GET"
    )
    @GetMapping("/{id}")
    public Result<UserDetailResponse> queryUserById(@PathVariable("id") Long id) {
        return Result.succeed(sysInfraUserApplicationService.queryUserById(id));
    }

    @Operation(
            summary = "创建用户",
            description = "创建用户",
            method = "POST"
    )
    @PostMapping
    public Result<Void> createUser(@RequestBody @Valid UserCreateCommand command) {
        sysInfraUserApplicationService.createUser(command);
        return Result.succeed(null);
    }

    @Operation(
            summary = "更新用户",
            description = "更新用户",
            method = "PUT"
    )
    @PutMapping
    public Result<Void> updateUser(@RequestBody @Valid UserUpdateCommand command) {
        sysInfraUserApplicationService.updateUser(command);
        return Result.succeed(null);
    }

    @Operation(
            summary = "删除用户",
            description = "删除用户",
            method = "DELETE"
    )
    @DeleteMapping
    public Result<Void> removeUser(@RequestBody @Valid UserRemoveCommand command) {
        sysInfraUserApplicationService.removeUser(command);
        return Result.succeed(null);
    }
}
