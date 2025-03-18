package com.jackmouse.system.system.application.user.dto.query;

import com.jackmouse.system.blog.domain.valueobject.Email;
import com.jackmouse.system.blog.domain.valueobject.ImageUrl;
import com.jackmouse.system.blog.domain.valueobject.Mobile;
import com.jackmouse.system.system.infra.domain.user.valueobject.UserStatus;
import com.jackmouse.system.system.infra.domain.user.valueobject.UserType;
import com.jackmouse.system.system.infra.domain.user.valueobject.Username;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @ClassName UserResponse
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 16:24
 * @Version 1.0
 **/
@Getter
@Builder
@AllArgsConstructor
public class UserResponse {
    @Schema(description = "用户ID", example = "1")
    private final Long id;
    @Schema(description = "用户类型", example = "ADMIN")
    private final String userType;
    @Schema(description = "用户名", example = "jackmouse")
    private final String email;
    @Schema(description = "手机号", example = "13800000000")
    private final String mobile;
    @Schema(description = "用户状态", example = "NORMAL")
    private final String status;
    @Schema(description = "头像", example = "https://example.com/avatar.jpg")
    private final String avatar;
    @Schema(description = "用户名", example = "jackmouse")
    private final String username;
    @Schema(description = "性别", example = "MALE")
    private final String sex;
}
