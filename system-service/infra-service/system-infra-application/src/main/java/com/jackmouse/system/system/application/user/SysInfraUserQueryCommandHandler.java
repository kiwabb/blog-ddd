package com.jackmouse.system.system.application.user;

import com.jackmouse.system.blog.domain.valueobject.PageResult;
import com.jackmouse.system.system.application.user.dto.query.UserDetailResponse;
import com.jackmouse.system.system.application.user.dto.query.UserPageQuery;
import com.jackmouse.system.system.application.user.dto.query.UserResponse;
import com.jackmouse.system.system.application.user.mapper.SystemInfraUserDataMapper;
import com.jackmouse.system.system.infra.domain.exception.SysNotfoundException;
import com.jackmouse.system.system.infra.domain.user.entity.User;
import com.jackmouse.system.system.infra.domain.user.repository.SystemUserRepository;
import com.jackmouse.system.blog.domain.valueobject.UserId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @ClassName SysInfraQueryCommandHandler
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 17:24
 * @Version 1.0
 **/
@Component
public class SysInfraUserQueryCommandHandler {
    private final SystemInfraUserDataMapper systemInfraUserDataMapper;
    private final SystemUserRepository systemUserRepository;

    public SysInfraUserQueryCommandHandler(SystemInfraUserDataMapper systemInfraUserDataMapper, SystemUserRepository systemUserRepository) {
        this.systemInfraUserDataMapper = systemInfraUserDataMapper;
        this.systemUserRepository = systemUserRepository;
    }

    @Transactional(readOnly = true)
    public com.jackmouse.system.response.PageResult<UserResponse> queryUserPage(UserPageQuery query) {
        PageResult<User> page = systemUserRepository.findPage(systemInfraUserDataMapper.queryUserPageToUserPageQuerySpec(query));
        return new com.jackmouse.system.response.PageResult<>(page.totalPages(), page.currentPage(),
                UserResponse.fromUserList(page.data()));
    }

    public com.jackmouse.system.response.PageResult<UserResponse> queryRoleUser(UserPageQuery query) {
        PageResult<User> page = systemUserRepository.findAssignPage(systemInfraUserDataMapper.queryUserPageToUserPageQuerySpec(query));
        return new com.jackmouse.system.response.PageResult<>(page.totalPages(), page.currentPage(),
                UserResponse.fromUserList(page.data()));
    }

    public UserDetailResponse queryUserById(Long id) {
        Optional<User> user = systemUserRepository.findById(new UserId(id));
        if (user.isEmpty()) {
            throw new SysNotfoundException("Could not find user with id: " + id + "!");
        }
        return UserDetailResponse.fromUser(user.get());
    }


}
