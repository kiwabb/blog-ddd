package com.jackmouse.system.system.infra.domain.rolemenu.entity;

import com.jackmouse.system.blog.domain.entity.AggregateRoot;
import com.jackmouse.system.blog.domain.valueobject.*;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.*;
import com.jackmouse.system.system.infra.domain.user.entity.User;

import java.util.List;


/**
 * @ClassName Role
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/18 10:24
 * @Version 1.0
 **/
public class Role extends AggregateRoot<RoleId> {
    private final RoleCode code;
    private final RoleName name;
    private final Enabled enabled;
    private final RoleDataScope dataScope;
    private final Version version;
    private final CreatedAt createdAt;
    private final CreatedBy createdBy;
    private final UpdatedAt updatedAt;
    private final UpdatedBy updatedBy;
    private final List<Menu> menus;
    private final List<User> users;

    private Role(Builder builder) {
        setId(builder.roleId);
        code = builder.code;
        name = builder.name;
        enabled = builder.enabled;
        dataScope = builder.dataScope;
        version = builder.version;
        createdAt = builder.createdAt;
        createdBy = builder.createdBy;
        updatedAt = builder.updatedAt;
        updatedBy = builder.updatedBy;
        menus = builder.menus;
        users = builder.users;
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public RoleCode getCode() {
        return code;
    }

    public RoleName getName() {
        return name;
    }

    public Enabled getEnabled() {
        return enabled;
    }

    public RoleDataScope getDataScope() {
        return dataScope;
    }

    public Version getVersion() {
        return version;
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

    public static final class Builder {
        private RoleId roleId;
        private RoleCode code;
        private RoleName name;
        private Enabled enabled;
        private RoleDataScope dataScope;
        private Version version;
        private CreatedAt createdAt;
        private CreatedBy createdBy;
        private UpdatedAt updatedAt;
        private UpdatedBy updatedBy;
        private List<Menu> menus;
        private List<User> users;

        private Builder() {
        }

        public Builder id(RoleId val) {
            roleId = val;
            return this;
        }

        public Builder code(RoleCode val) {
            code = val;
            return this;
        }

        public Builder name(RoleName val) {
            name = val;
            return this;
        }

        public Builder enabled(Enabled val) {
            enabled = val;
            return this;
        }

        public Builder dataScope(RoleDataScope val) {
            dataScope = val;
            return this;
        }

        public Builder version(Version val) {
            version = val;
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

        public Builder menus(List<Menu> val) {
            menus = val;
            return this;
        }

        public Builder users(List<User> val) {
            users = val;
            return this;
        }

        public Role build() {
            return new Role(this);
        }

    }
}
