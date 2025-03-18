package com.jackmouse.system.system.application.user.mapper;

import com.jackmouse.system.blog.domain.valueobject.*;
import com.jackmouse.system.system.application.user.dto.create.UserCreateCommand;
import com.jackmouse.system.system.application.user.dto.query.UserDetailResponse;
import com.jackmouse.system.system.application.user.dto.query.UserPageQuery;
import com.jackmouse.system.system.application.user.dto.query.UserResponse;
import com.jackmouse.system.system.application.user.dto.remove.UserRemoveCommand;
import com.jackmouse.system.system.application.user.dto.update.UserUpdateCommand;
import com.jackmouse.system.system.infra.domain.user.entity.User;
import com.jackmouse.system.system.infra.domain.user.specification.query.UserPageQuerySpec;
import com.jackmouse.system.system.infra.domain.user.valueobject.UserId;
import com.jackmouse.system.system.infra.domain.user.valueobject.UserStatus;
import com.jackmouse.system.system.infra.domain.user.valueobject.UserType;
import com.jackmouse.system.system.infra.domain.user.valueobject.Username;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName SystemInfraDataMapper
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 17:25
 * @Version 1.0
 **/
@Component
public class SystemInfraUserDataMapper {
    public UserPageQuerySpec queryUserPageToUserPageQuerySpec(UserPageQuery query) {
        return UserPageQuerySpec.builder()
                .username(new Username(query.getUsername()))
                .mobile(new Mobile(query.getMobile()))
                .email(new Email(query.getEmail()))
                .status(query.getStatus() == null ? null : UserStatus.valueOf(query.getStatus()))
                .userType(query.getUserType() == null ? null : UserType.valueOf(query.getUserType()))
                .pageParam(new PageParam(query.getPage(), query.getSize(), query))
                .build();
    }

    public List<UserResponse> userListToUserResponseList(List<User> users) {
        return users.stream().map(this::userToUserResponse).toList();
    }

    private UserResponse userToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId().getValue())
                .avatar(user.getAvatar().value())
                .username(user.getUsername().value())
                .email(user.getEmail().value())
                .mobile(user.getMobile().value())
                .status(user.getStatus().name())
                .userType(user.getUserType().name())
                .sex(user.getSex().name())
                .build();
    }

    public User userCreateCommandToUser(UserCreateCommand command) {
        return User.builder()
                .sex(command.getSex() == null ? Sex.UNKNOWN : Sex.valueOf(command.getSex()))
                .username(new Username(command.getUsername()))
                .email(new Email(command.getEmail()))
                .mobile(new Mobile(command.getMobile()))
                .userType(UserType.valueOf(command.getUserType()))
                .status(UserStatus.NORMAL)
                .password(new Password("123456"))
                .avatar(new ImageUrl(command.getAvatar()))
                .build();
    }

    public User userUpdateCommandToUser(UserUpdateCommand command) {
        return User.builder()
                .id(new UserId(command.getId()))
                .sex(command.getSex() == null ? Sex.UNKNOWN : Sex.valueOf(command.getSex()))
                .username(new Username(command.getUsername()))
                .email(new Email(command.getEmail()))
                .mobile(new Mobile(command.getMobile()))
                .userType(UserType.valueOf(command.getUserType()))
                .status(UserStatus.NORMAL)
                .avatar(new ImageUrl(command.getAvatar()))
                .version(new Version(command.getVersion()))
                .build();
    }

    public List<User> userRemoveCommandToUser(UserRemoveCommand commands) {
        return commands.getIds().stream().map(command -> User.builder()
                .id(new UserId(command)).build()).toList();
    }

    public UserDetailResponse userToUserDetailResponse(User user) {
        return UserDetailResponse.builder()
                .id(user.getId().getValue())
                .avatar(user.getAvatar().value())
                .username(user.getUsername().value())
                .email(user.getEmail().value())
                .mobile(user.getMobile().value())
                .status(user.getStatus().name())
                .userType(user.getUserType().name())
                .sex(user.getSex().name())
                .build();
    }
}
