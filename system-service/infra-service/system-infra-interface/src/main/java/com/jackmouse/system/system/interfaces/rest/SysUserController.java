package com.jackmouse.system.system.interfaces.rest;

import com.jackmouse.system.response.Result;
import com.jackmouse.system.response.PageResult;
import com.jackmouse.system.system.application.dto.create.UserCreateCommand;
import com.jackmouse.system.system.application.dto.query.UserDetailResponse;
import com.jackmouse.system.system.application.dto.query.UserPageQuery;
import com.jackmouse.system.system.application.dto.query.UserResponse;
import com.jackmouse.system.system.application.dto.remove.UserRemoveCommand;
import com.jackmouse.system.system.application.dto.update.UserUpdateCommand;
import com.jackmouse.system.system.application.ports.input.service.SysInfraApplicationService;
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

    private final SysInfraApplicationService sysInfraApplicationService;

    public SysUserController(SysInfraApplicationService sysInfraApplicationService) {
        this.sysInfraApplicationService = sysInfraApplicationService;
    }

    @Operation(
            summary = "分页查询用户列表",
            description = "分页查询用户列表",
            method = "GET"
    )
    @GetMapping("/page")
    public PageResult<UserResponse> queryUserPage(UserPageQuery query) {
        return sysInfraApplicationService.queryUserPage(query);
    }

    @Operation(
            summary = "查询用户详情",
            description = "查询用户详情",
            method = "GET"
    )
    @GetMapping("/{id}")
    public Result<UserDetailResponse> queryUserById(@PathVariable("id") Long id) {
        return Result.succeed(sysInfraApplicationService.queryUserById(id));
    }

    @Operation(
            summary = "创建用户",
            description = "创建用户",
            method = "POST"
    )
    @PostMapping
    public Result<Void> createUser(@RequestBody @Valid UserCreateCommand command) {
        sysInfraApplicationService.createUser(command);
        return Result.succeed(null);
    }

    @Operation(
            summary = "更新用户",
            description = "更新用户",
            method = "PUT"
    )
    @PutMapping
    public Result<Void> updateUser(@RequestBody @Valid UserUpdateCommand command) {
        sysInfraApplicationService.updateUser(command);
        return Result.succeed(null);
    }

    @Operation(
            summary = "删除用户",
            description = "删除用户",
            method = "DELETE"
    )
    @DeleteMapping
    public Result<Void> removeUser(@RequestBody @Valid UserRemoveCommand command) {
        sysInfraApplicationService.removeUser(command);
        return Result.succeed(null);
    }
}
