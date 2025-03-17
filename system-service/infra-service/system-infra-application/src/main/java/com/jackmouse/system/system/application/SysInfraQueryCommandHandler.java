package com.jackmouse.system.system.application;

import com.jackmouse.system.blog.domain.valueobject.PageResult;
import com.jackmouse.system.system.application.dto.query.UserDetailResponse;
import com.jackmouse.system.system.application.dto.query.UserPageQuery;
import com.jackmouse.system.system.application.dto.query.UserResponse;
import com.jackmouse.system.system.application.mapper.SystemInfraDataMapper;
import com.jackmouse.system.system.infra.domain.exception.SysNotfoundException;
import com.jackmouse.system.system.infra.domain.user.entity.User;
import com.jackmouse.system.system.infra.domain.user.repository.SystemUserRepository;
import com.jackmouse.system.system.infra.domain.user.valueobject.UserId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName SysInfraQueryCommandHandler
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 17:24
 * @Version 1.0
 **/
@Component
public class SysInfraQueryCommandHandler {
    private final SystemInfraDataMapper systemInfraDataMapper;
    private final SystemUserRepository systemUserRepository;

    public SysInfraQueryCommandHandler(SystemInfraDataMapper systemInfraDataMapper, SystemUserRepository systemUserRepository) {
        this.systemInfraDataMapper = systemInfraDataMapper;
        this.systemUserRepository = systemUserRepository;
    }

    @Transactional(readOnly = true)
    public com.jackmouse.system.response.PageResult<UserResponse> queryUserPage(UserPageQuery query) {
        PageResult<User> page = systemUserRepository.findPage(systemInfraDataMapper.queryUserPageToUserPageQuerySpec(query));
        List<UserResponse> userResponses = systemInfraDataMapper.userListToUserResponseList(page.data());
        return new com.jackmouse.system.response.PageResult<>(page.totalPages(), page.currentPage(), userResponses);
    }

    public UserDetailResponse queryUserById(Long id) {
        Optional<User> user = systemUserRepository.findById(new UserId(id));
        if (user.isEmpty()) {
            throw new SysNotfoundException("Could not find user with id: " + id + "!");
        }
        return systemInfraDataMapper.userToUserDetailResponse(user.get());
    }
}
