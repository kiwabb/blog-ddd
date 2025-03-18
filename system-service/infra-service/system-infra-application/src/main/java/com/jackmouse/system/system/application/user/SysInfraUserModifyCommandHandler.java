package com.jackmouse.system.system.application.user;

import com.jackmouse.system.system.application.user.dto.create.UserCreateCommand;
import com.jackmouse.system.system.application.user.dto.remove.UserRemoveCommand;
import com.jackmouse.system.system.application.user.dto.update.UserUpdateCommand;
import com.jackmouse.system.system.application.user.mapper.SystemInfraUserDataMapper;
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
public class SysInfraUserModifyCommandHandler {

    private final SystemUserRepository systemUserRepository;
    private final SystemInfraUserDataMapper systemInfraUserDataMapper;

    public SysInfraUserModifyCommandHandler(SystemUserRepository systemUserRepository, SystemInfraUserDataMapper systemInfraUserDataMapper) {
        this.systemUserRepository = systemUserRepository;
        this.systemInfraUserDataMapper = systemInfraUserDataMapper;
    }


    @Transactional
    public void createUser(UserCreateCommand command) {
        log.info("Received CreateUserCommand: {}", command);
        User user = systemInfraUserDataMapper.userCreateCommandToUser(command);
        systemUserRepository.save(user);
    }

    @Transactional
    public void updateUser(UserUpdateCommand command) {
        log.info("Received UpdateUserCommand: {}", command);
        User user = systemInfraUserDataMapper.userUpdateCommandToUser(command);
        systemUserRepository.save(user);
    }

    @Transactional
    public void removeUser(UserRemoveCommand commands) {
        log.info("Received RemoveUserCommand: {}", commands);
        systemUserRepository.remove(systemInfraUserDataMapper.userRemoveCommandToUser(commands));
    }
}
