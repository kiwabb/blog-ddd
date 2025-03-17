package com.jackmouse.system.system.application;

import com.jackmouse.system.response.PageResult;
import com.jackmouse.system.system.application.dto.create.UserCreateCommand;
import com.jackmouse.system.system.application.dto.query.UserDetailResponse;
import com.jackmouse.system.system.application.dto.query.UserPageQuery;
import com.jackmouse.system.system.application.dto.query.UserResponse;
import com.jackmouse.system.system.application.dto.remove.UserRemoveCommand;
import com.jackmouse.system.system.application.dto.update.UserUpdateCommand;
import com.jackmouse.system.system.application.ports.input.service.SysInfraApplicationService;
import org.springframework.stereotype.Service;

/**
 * @ClassName SysInfraApplicationServiceImpl
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 16:18
 * @Version 1.0
 **/
@Service
public class SysInfraApplicationServiceImpl implements SysInfraApplicationService {
    private final SysInfraQueryCommandHandler sysInfraQueryCommandHandler;
    private final SysInfraModifyCommandHandler sysInfraModifyCommandHandler;

    public SysInfraApplicationServiceImpl(SysInfraQueryCommandHandler sysInfraQueryCommandHandler, SysInfraModifyCommandHandler sysInfraModifyCommandHandler) {
        this.sysInfraQueryCommandHandler = sysInfraQueryCommandHandler;
        this.sysInfraModifyCommandHandler = sysInfraModifyCommandHandler;
    }

    @Override
    public PageResult<UserResponse> queryUserPage(UserPageQuery query) {
        return sysInfraQueryCommandHandler.queryUserPage(query);
    }
    @Override
    public UserDetailResponse queryUserById(Long id) {
        return sysInfraQueryCommandHandler.queryUserById(id);
    }
    @Override
    public void createUser(UserCreateCommand command) {
        sysInfraModifyCommandHandler.createUser(command);
    }

    @Override
    public void updateUser(UserUpdateCommand command) {
        sysInfraModifyCommandHandler.updateUser(command);
    }

    @Override
    public void removeUser(UserRemoveCommand commands) {
        sysInfraModifyCommandHandler.removeUser(commands);
    }



}
