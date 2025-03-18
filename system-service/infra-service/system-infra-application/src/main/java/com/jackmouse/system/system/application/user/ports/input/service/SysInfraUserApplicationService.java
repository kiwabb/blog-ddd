package com.jackmouse.system.system.application.user.ports.input.service;

import com.jackmouse.system.response.PageResult;
import com.jackmouse.system.system.application.user.dto.create.UserCreateCommand;
import com.jackmouse.system.system.application.user.dto.query.UserDetailResponse;
import com.jackmouse.system.system.application.user.dto.query.UserPageQuery;
import com.jackmouse.system.system.application.user.dto.query.UserResponse;
import com.jackmouse.system.system.application.user.dto.remove.UserRemoveCommand;
import com.jackmouse.system.system.application.user.dto.update.UserUpdateCommand;

/**
 * @ClassName SysInfraApplicationService
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 16:18
 * @Version 1.0
 **/
public interface SysInfraUserApplicationService {
    PageResult<UserResponse> queryUserPage(UserPageQuery query);

    void createUser(UserCreateCommand command);

    void updateUser(UserUpdateCommand command);

    void removeUser(UserRemoveCommand commands);

    UserDetailResponse queryUserById(Long id);
}
