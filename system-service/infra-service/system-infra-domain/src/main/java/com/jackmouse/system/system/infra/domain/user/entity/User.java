package com.jackmouse.system.system.infra.domain.user.entity;

import com.jackmouse.system.blog.domain.entity.AggregateRoot;
import com.jackmouse.system.blog.domain.valueobject.*;
import com.jackmouse.system.system.infra.domain.user.valueobject.Username;
import com.jackmouse.system.system.infra.domain.user.valueobject.UserId;
import com.jackmouse.system.system.infra.domain.user.valueobject.UserStatus;
import com.jackmouse.system.system.infra.domain.user.valueobject.UserType;

import java.util.List;

/**
 * @ClassName User
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/13 14:56
 * @Version 1.0
 **/
public class User extends AggregateRoot<UserId> {
    private final Password password;
    private final UserType userType;
    private final Email email;
    private final Mobile mobile;
    private final UserStatus status;
    private final ImageUrl avatar;
    private final Username username;
    private final Version version;
    private final Sex sex;

    private final CreatedAt createdAt;
    private final CreatedBy createdBy;
    private final UpdatedAt updatedAt;
    private final UpdatedBy updatedBy;

    private List<Long> roleIds;
    private List<Long> deptIds;

    private User(Builder builder) {
        setId(builder.userId);
        password = builder.password;
        userType = builder.userType;
        email = builder.email;
        mobile = builder.mobile;
        status = builder.status;
        avatar = builder.avatar;
        username = builder.username;
        version = builder.version;
        sex = builder.sex;
        createdAt = builder.createdAt;
        createdBy = builder.createdBy;
        updatedAt = builder.updatedAt;
        updatedBy = builder.updatedBy;
        roleIds = builder.roleIds;
        deptIds = builder.deptIds;
    }

    public static Builder builder() {
        return new Builder();
    }

    public CreatedAt getCreatedAt() {
        return createdAt;
    }

    public CreatedBy getCreatedBy() {
        return createdBy;
    }

    public UpdatedAt getUpdatedAt() {
        return updatedAt;
    }

    public UpdatedBy getUpdatedBy() {
        return updatedBy;
    }

    public Password getPassword() {
        return password;
    }

    public Version getVersion() {
        return version;
    }

    public Sex getSex() {
        return sex;
    }

    public UserType getUserType() {
        return userType;
    }

    public Email getEmail() {
        return email;
    }

    public Mobile getMobile() {
        return mobile;
    }

    public UserStatus getStatus() {
        return status;
    }

    public ImageUrl getAvatar() {
        return avatar;
    }

    public Username getUsername() {
        return username;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public List<Long> getDeptIds() {
        return deptIds;
    }

    public static final class Builder {
        public Version version;
        private UserId userId;
        private Password password;
        private UserType userType;
        private Email email;
        private Mobile mobile;
        private UserStatus status;
        private ImageUrl avatar;
        private Username username;
        private Sex sex;
        private CreatedAt createdAt;
        private CreatedBy createdBy;
        private UpdatedAt updatedAt;
        private UpdatedBy updatedBy;
        private List<Long> roleIds;
        private List<Long> deptIds;

        private Builder() {
        }

        public Builder id(UserId val) {
            userId = val;
            return this;
        }

        public Builder password(Password val) {
            password = val;
            return this;
        }

        public Builder userType(UserType val) {
            userType = val;
            return this;
        }

        public Builder email(Email val) {
            email = val;
            return this;
        }

        public Builder mobile(Mobile val) {
            mobile = val;
            return this;
        }

        public Builder status(UserStatus val) {
            status = val;
            return this;
        }

        public Builder avatar(ImageUrl val) {
            avatar = val;
            return this;
        }

        public Builder username(Username val) {
            username = val;
            return this;
        }

        public Builder sex(Sex val) {
            sex = val;
            return this;
        }

        public Builder createdAt(CreatedAt val) {
            createdAt = val;
            return this;
        }

        public Builder createdBy(CreatedBy val) {
            createdBy = val;
            return this;
        }

        public Builder updatedAt(UpdatedAt val) {
            updatedAt = val;
            return this;
        }

        public Builder updatedBy(UpdatedBy val) {
            updatedBy = val;
            return this;
        }

        public Builder roleIds(List<Long> val) {
            roleIds = val;
            return this;
        }

        public Builder deptIds(List<Long> val) {
            deptIds = val;
            return this;
        }

        public Builder version(Version val) {
            version = val;
            return this;
        }

        public User build() {
            return new User(this);
        }

    }
}
