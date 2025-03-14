package com.jackmouse.system.system.infra.domain.user.specification.query;

import com.jackmouse.system.blog.domain.specification.PageSpec;
import com.jackmouse.system.blog.domain.specification.SortSpec;
import com.jackmouse.system.blog.domain.valueobject.Email;
import com.jackmouse.system.blog.domain.valueobject.Mobile;
import com.jackmouse.system.blog.domain.valueobject.PageParam;
import com.jackmouse.system.system.infra.domain.user.valueobject.Username;
import com.jackmouse.system.system.infra.domain.user.entity.User;
import com.jackmouse.system.system.infra.domain.user.valueobject.UserStatus;
import com.jackmouse.system.system.infra.domain.user.valueobject.UserType;

import java.util.function.Predicate;

/**
 * @ClassName ArticlePageQuerySpec
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 09:19
 * @Version 1.0
 **/
public class UserPageQuerySpec implements PageSpec<User> {

    private final Username username;
    private final Mobile mobile;
    private final Email email;
    private final UserStatus status;
    private final UserType userType;
    private final PageParam pageParam;

    private UserPageQuerySpec(Builder builder) {
        username = builder.username;
        mobile = builder.mobile;
        email = builder.email;
        status = builder.status;
        userType = builder.userType;
        pageParam = builder.pageParam;
    }


    public static Builder builder() {
        return new Builder();
    }


    @Override
    public int getPage() {
        return pageParam.page();
    }

    @Override
    public int getSize() {
        return pageParam.size();
    }

    @Override
    public SortSpec getSort() {
        return pageParam.sort();
    }

    @Override
    public Predicate<User> getPredicate() {
        return null;
    }

    public static final class Builder {
        private Username username;
        private Mobile mobile;
        private Email email;
        private UserStatus status;
        private UserType userType;
        private PageParam pageParam;

        private Builder() {
        }

        public Builder username(Username val) {
            username = val;
            return this;
        }

        public Builder mobile(Mobile val) {
            mobile = val;
            return this;
        }

        public Builder email(Email val) {
            email = val;
            return this;
        }

        public Builder status(UserStatus val) {
            status = val;
            return this;
        }

        public Builder userType(UserType val) {
            userType = val;
            return this;
        }

        public Builder pageParam(PageParam val) {
            pageParam = val;
            return this;
        }

        public UserPageQuerySpec build() {
            return new UserPageQuerySpec(this);
        }
    }
}
