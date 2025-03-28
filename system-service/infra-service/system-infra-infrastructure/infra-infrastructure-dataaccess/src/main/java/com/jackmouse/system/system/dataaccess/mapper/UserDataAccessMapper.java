package com.jackmouse.system.system.dataaccess.mapper;

import com.jackmouse.system.blog.domain.valueobject.*;
import com.jackmouse.system.system.dataaccess.entity.SysUserEntity;
import com.jackmouse.system.system.infra.domain.user.entity.User;
import com.jackmouse.system.blog.domain.valueobject.UserId;
import com.jackmouse.system.system.infra.domain.user.valueobject.Username;
import org.springframework.stereotype.Component;

/**
 * @ClassName UserDataAccessMapper
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 17:04
 * @Version 1.0
 **/
@Component
public class UserDataAccessMapper {

    public SysUserEntity userToUpdateUserEntity(User user) {
        SysUserEntity.SysUserEntityBuilder builder = SysUserEntity.builder()
                .id(user.getId().getValue())
                .username(user.getUsername().value())
                .phone(user.getMobile().value())
                .email(user.getEmail().value())
                .nickname(user.getUsername().value())
                .sex(user.getSex())
                .avatar(user.getAvatar().value())
                .status(user.getStatus())
                .userType(user.getUserType())
                .tenantId(1L)
                .version(user.getVersion().getValue());
        return builder.build();
    }

    public SysUserEntity userToCreateUserEntity(User user) {
        SysUserEntity.SysUserEntityBuilder builder = SysUserEntity.builder()
                .username(user.getUsername().value())
                .password(user.getPassword().getPassword())
                .phone(user.getMobile().value())
                .email(user.getEmail().value())
                .nickname(user.getUsername().value())
                .sex(user.getSex())
                .avatar(user.getAvatar().value())
                .status(user.getStatus())
                .tenantId(1L)
                .userType(user.getUserType());
        return builder.build();
    }

}
