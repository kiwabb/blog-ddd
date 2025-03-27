package com.jackmouse.system.system.application.user.dto.query;

import com.jackmouse.system.blog.domain.valueobject.Email;
import com.jackmouse.system.blog.domain.valueobject.Mobile;
import com.jackmouse.system.blog.domain.valueobject.PageParam;
import com.jackmouse.system.dto.query.PageQuery;
import com.jackmouse.system.system.infra.domain.user.valueobject.UserStatus;
import com.jackmouse.system.system.infra.domain.user.valueobject.UserType;
import com.jackmouse.system.system.infra.domain.user.valueobject.Username;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @ClassName UserPageQuery
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 17:12
 * @Version 1.0
 **/
@Getter
@Setter
@Builder
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class UserPageQuery extends PageQuery {
    @Schema(description = "用户名", example = "jackmouse")
    private final String username;
    @Schema(description = "手机号", example = "12345678901")
    private final String mobile;
    @Schema(description = "邮箱", example = "jackmouse@qq.com")
    private final String email;
    @Schema(description = "用户状态", example = "NORMAL")
    private final String status;
    @Schema(description = "用户类型", example = "ADMIN")
    private final String userType;
    @Schema(description = "角色ID", example = "1")
    private final Long roleId;
    @Schema(description = "是否绑定角色", example = "true")
    private Boolean isBindRole;
}
