package com.jackmouse.system.system.infra.domain.rolemenu.specification.query;

import com.jackmouse.system.blog.domain.specification.PageSpec;
import com.jackmouse.system.blog.domain.specification.SortSpec;
import com.jackmouse.system.blog.domain.valueobject.Email;
import com.jackmouse.system.blog.domain.valueobject.Mobile;
import com.jackmouse.system.blog.domain.valueobject.PageParam;
import com.jackmouse.system.system.infra.domain.rolemenu.entity.Menu;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.MenuName;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.MenuType;
import com.jackmouse.system.system.infra.domain.user.entity.User;
import com.jackmouse.system.system.infra.domain.user.valueobject.UserStatus;
import com.jackmouse.system.system.infra.domain.user.valueobject.UserType;
import com.jackmouse.system.system.infra.domain.user.valueobject.Username;

import java.util.function.Predicate;

/**
 * @ClassName ArticlePageQuerySpec
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 09:19
 * @Version 1.0
 **/
public class MenuPageQuerySpec implements PageSpec {

    private final MenuName menuName;
    private final MenuType menuType;
    private final PageParam pageParam;

    public MenuName getMenuName() {
        return menuName;
    }

    public MenuType getMenuType() {
        return menuType;
    }

    public PageParam getPageParam() {
        return pageParam;
    }

    private MenuPageQuerySpec(Builder builder) {
        menuName = builder.menuName;
        menuType = builder.menuType;
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


    public static final class Builder {
        private PageParam pageParam;
        private MenuName menuName;
        private MenuType menuType;

        private Builder() {
        }

        public Builder pageParam(PageParam val) {
            pageParam = val;
            return this;
        }

        public MenuPageQuerySpec build() {
            return new MenuPageQuerySpec(this);
        }

        public Builder menuName(MenuName val) {
            menuName = val;
            return this;
        }

        public Builder menuType(MenuType val) {
            menuType = val;
            return this;
        }
    }
}
