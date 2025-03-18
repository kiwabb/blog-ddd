package com.jackmouse.system.system.application.rolemenu.dto.query;

import com.jackmouse.system.blog.domain.valueobject.PageParam;
import com.jackmouse.system.dto.query.PageQuery;
import com.jackmouse.system.system.infra.domain.rolemenu.specification.query.MenuPageQuerySpec;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.MenuName;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.MenuType;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.RoleCode;
import com.jackmouse.system.system.infra.domain.rolemenu.valueobject.RoleName;
import io.swagger.v3.oas.annotations.media.Schema;
import jdk.jfr.Enabled;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName MenuPageQuery
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/18 13:35
 * @Version 1.0
 **/
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuPageQuery extends PageQuery {
    @Schema(description = "菜单名称", example = "菜单名称")
    private final String menuName;
    @Schema(description = "菜单类型", example = "菜单类型")
    private final String menuType;

    public MenuPageQuerySpec toMenuPageQuery() {
        this.setSize(Integer.MAX_VALUE);
        return MenuPageQuerySpec.builder()
                .menuName(new MenuName(menuName))
                .menuType(MenuType.valueOf(menuType))
                .pageParam(new PageParam(getPage(), getSize(), this))
                .build();
    }
}
