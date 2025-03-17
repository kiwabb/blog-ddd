package com.jackmouse.system.system.dataaccess.mapper;

import com.jackmouse.system.blog.domain.valueobject.Email;
import com.jackmouse.system.blog.domain.valueobject.ImageUrl;
import com.jackmouse.system.blog.domain.valueobject.Mobile;
import com.jackmouse.system.system.dataaccess.entity.UserEntity;
import com.jackmouse.system.system.infra.domain.user.entity.User;
import com.jackmouse.system.system.infra.domain.user.valueobject.UserId;
import com.jackmouse.system.system.infra.domain.user.valueobject.Username;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName UserDataAccessMapper
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 17:04
 * @Version 1.0
 **/
@Component
public class UserDataAccessMapper {
    public User userEntityToUser(UserEntity userEntity) {
        return User.builder()
                .id(new UserId(userEntity.getId()))
                .username(new Username(userEntity.getUsername()))
                .userType(userEntity.getUserType())
                .status(userEntity.getStatus())
                .avatar(new ImageUrl(userEntity.getAvatar()))
                .email(new Email(userEntity.getEmail()))
                .mobile(new Mobile(userEntity.getPhone()))
                .sex(userEntity.getSex())
                .build();
    }

    public UserEntity userToUpdateUserEntity(User user) {
        UserEntity.UserEntityBuilder builder = UserEntity.builder()
                .id(user.getId().getValue())
                .username(user.getUsername().value())
                .phone(user.getMobile().value())
                .email(user.getEmail().value())
                .nickname(user.getUsername().value())
                .sex(user.getSex())
                .avatar(user.getAvatar().value())
                .status(user.getStatus())
                .userType(user.getUserType())
                .version(user.getVersion().getValue());
        return builder.build();
    }

    public UserEntity userToCreateUserEntity(User user) {
        UserEntity.UserEntityBuilder builder = UserEntity.builder()
                .username(user.getUsername().value())
                .password(user.getPassword().getPassword())
                .phone(user.getMobile().value())
                .email(user.getEmail().value())
                .nickname(user.getUsername().value())
                .sex(user.getSex())
                .avatar(user.getAvatar().value())
                .status(user.getStatus())
                .userType(user.getUserType());
        return builder.build();
    }

    public List<UserEntity> userListIdToUserEntityList(List<User> users) {
        return users.stream().map(user ->
                UserEntity.builder()
                        .id(user.getId().getValue())
                        .build()).toList();
    }
}
