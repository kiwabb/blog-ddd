package com.jackmouse.system.system.application.ports.input.service;

import com.jackmouse.system.response.PageResult;
import com.jackmouse.system.system.application.dto.create.UserCreateCommand;
import com.jackmouse.system.system.application.dto.query.UserDetailResponse;
import com.jackmouse.system.system.application.dto.query.UserPageQuery;
import com.jackmouse.system.system.application.dto.query.UserResponse;
import com.jackmouse.system.system.application.dto.remove.UserRemoveCommand;
import com.jackmouse.system.system.application.dto.update.UserUpdateCommand;

/**
 * @ClassName SysInfraApplicationService
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 16:18
 * @Version 1.0
 **/
public interface SysInfraApplicationService {
    PageResult<UserResponse> queryUserPage(UserPageQuery query);

    void createUser(UserCreateCommand command);

    void updateUser(UserUpdateCommand command);

    void removeUser(UserRemoveCommand commands);

    UserDetailResponse queryUserById(Long id);
}
