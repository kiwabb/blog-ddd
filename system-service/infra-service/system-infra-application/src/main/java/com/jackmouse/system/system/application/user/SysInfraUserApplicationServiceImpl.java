package com.jackmouse.system.system.application.user;

import com.jackmouse.system.response.PageResult;
import com.jackmouse.system.system.application.user.dto.create.UserCreateCommand;
import com.jackmouse.system.system.application.user.dto.query.UserDetailResponse;
import com.jackmouse.system.system.application.user.dto.query.UserPageQuery;
import com.jackmouse.system.system.application.user.dto.query.UserResponse;
import com.jackmouse.system.system.application.user.dto.remove.UserRemoveCommand;
import com.jackmouse.system.system.application.user.dto.update.UserUpdateCommand;
import com.jackmouse.system.system.application.user.ports.input.service.SysInfraUserApplicationService;
import org.springframework.stereotype.Service;

/**
 * @ClassName SysInfraApplicationServiceImpl
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 16:18
 * @Version 1.0
 **/
@Service
public class SysInfraUserApplicationServiceImpl implements SysInfraUserApplicationService {
    private final SysInfraUserQueryCommandHandler sysInfraUserQueryCommandHandler;
    private final SysInfraUserModifyCommandHandler sysInfraUserModifyCommandHandler;

    public SysInfraUserApplicationServiceImpl(SysInfraUserQueryCommandHandler sysInfraUserQueryCommandHandler, SysInfraUserModifyCommandHandler sysInfraUserModifyCommandHandler) {
        this.sysInfraUserQueryCommandHandler = sysInfraUserQueryCommandHandler;
        this.sysInfraUserModifyCommandHandler = sysInfraUserModifyCommandHandler;
    }

    @Override
    public PageResult<UserResponse> queryUserPage(UserPageQuery query) {
        return sysInfraUserQueryCommandHandler.queryUserPage(query);
    }

    @Override
    public PageResult<UserResponse> queryAssignUser(UserPageQuery query) {
        return sysInfraUserQueryCommandHandler.queryRoleUser(query);
    }

    @Override
    public PageResult<UserResponse> queryUnAssignUser(UserPageQuery query) {
        return sysInfraUserQueryCommandHandler.queryRoleUser(query);
    }

    @Override
    public UserDetailResponse queryUserById(Long id) {
        return sysInfraUserQueryCommandHandler.queryUserById(id);
    }
    @Override
    public void createUser(UserCreateCommand command) {
        sysInfraUserModifyCommandHandler.createUser(command);
    }

    @Override
    public void updateUser(UserUpdateCommand command) {
        sysInfraUserModifyCommandHandler.updateUser(command);
    }

    @Override
    public void removeUser(UserRemoveCommand commands) {
        sysInfraUserModifyCommandHandler.removeUser(commands);
    }



}
