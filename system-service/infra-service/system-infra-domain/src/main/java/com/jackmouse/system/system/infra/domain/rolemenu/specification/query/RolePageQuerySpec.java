package com.jackmouse.system.system.infra.domain.rolemenu.specification.query;

import com.jackmouse.system.blog.domain.specification.PageSpec;
import com.jackmouse.system.blog.domain.specification.SortSpec;
import com.jackmouse.system.blog.domain.valueobject.PageParam;
import com.jackmouse.system.system.infra.domain.rolemenu.entity.Role;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.Enabled;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.RoleCode;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.RoleName;


import java.util.function.Predicate;

/**
 * @ClassName ArticlePageQuerySpec
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 09:19
 * @Version 1.0
 **/
public class RolePageQuerySpec implements PageSpec {

    private final RoleCode code;
    private final RoleName name;
    private final Enabled enabled;
    private final PageParam pageParam;

    public RoleCode getCode() {
        return code;
    }

    public RoleName getName() {
        return name;
    }

    public Enabled getEnabled() {
        return enabled;
    }

    public PageParam getPageParam() {
        return pageParam;
    }

    private RolePageQuerySpec(Builder builder) {
        code = builder.code;
        name = builder.name;
        enabled = builder.enabled;
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
        private RoleCode code;
        private RoleName name;
        private Enabled enabled;

        private Builder() {
        }

        public Builder pageParam(PageParam val) {
            pageParam = val;
            return this;
        }

        public RolePageQuerySpec build() {
            return new RolePageQuerySpec(this);
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
    }
}
