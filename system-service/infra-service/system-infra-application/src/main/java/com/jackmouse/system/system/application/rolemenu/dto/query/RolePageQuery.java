package com.jackmouse.system.system.application.rolemenu.dto.query;

import com.jackmouse.system.blog.domain.valueobject.PageParam;
import com.jackmouse.system.dto.query.PageQuery;
import com.jackmouse.system.system.infra.domain.rolemenu.specification.query.RolePageQuerySpec;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName RolePageQuery
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/18 13:34
 * @Version 1.0
 **/
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class RolePageQuery extends PageQuery {
    private final String code;
    private final String name;
    private final Boolean enabled;

    public RolePageQuerySpec toRolePageQuery() {
        return RolePageQuerySpec.builder()
               .code(new RoleCode(code))
               .name(new RoleName(name))
               .enabled(new Enabled(enabled))
               .pageParam(new PageParam(getPage(), getSize(), this))
               .build();
    }
}
