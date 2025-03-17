package com.jackmouse.system.system.application;

import com.jackmouse.system.system.application.dto.create.UserCreateCommand;
import com.jackmouse.system.system.application.dto.remove.UserRemoveCommand;
import com.jackmouse.system.system.application.dto.update.UserUpdateCommand;
import com.jackmouse.system.system.application.mapper.SystemInfraDataMapper;
import com.jackmouse.system.system.infra.domain.user.entity.User;
import com.jackmouse.system.system.infra.domain.user.repository.SystemUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName SysInfraModifyCommandHandler
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/17 11:24
 * @Version 1.0
 **/
@Slf4j
@Component
public class SysInfraModifyCommandHandler {

    private final SystemUserRepository systemUserRepository;
    private final SystemInfraDataMapper systemInfraDataMapper;

    public SysInfraModifyCommandHandler(SystemUserRepository systemUserRepository, SystemInfraDataMapper systemInfraDataMapper) {
        this.systemUserRepository = systemUserRepository;
        this.systemInfraDataMapper = systemInfraDataMapper;
    }


    @Transactional
    public void createUser(UserCreateCommand command) {
        log.info("Received CreateUserCommand: {}", command);
        User user = systemInfraDataMapper.userCreateCommandToUser(command);
        systemUserRepository.save(user);
    }

    @Transactional
    public void updateUser(UserUpdateCommand command) {
        log.info("Received UpdateUserCommand: {}", command);
        User user = systemInfraDataMapper.userUpdateCommandToUser(command);
        systemUserRepository.save(user);
    }

    @Transactional
    public void removeUser(UserRemoveCommand commands) {
        log.info("Received RemoveUserCommand: {}", commands);
        systemUserRepository.remove(systemInfraDataMapper.userRemoveCommandToUser(commands));
    }
}
