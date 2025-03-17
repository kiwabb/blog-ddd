package com.jackmouse.system.system.application.dto.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * @ClassName UserCreateCommand
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/17 11:10
 * @Version 1.0
 **/
@Getter
@Setter
@Builder
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class UserCreateCommand {
    @Schema(description = "用户类型", example = "ADMIN")
    @NotNull
    private final String userType;
    @Schema(description = "邮箱", example = "jackmouse@qq.com")
    @NotNull
    private final String email;
    @Schema(description = "手机号", example = "13800000000")
    @NotNull
    private final String mobile;
    @Schema(description = "头像", example = "https://example.com/avatar.jpg")
    @NotNull
    private final String avatar;
    @Schema(description = "用户名", example = "jackmouse")
    @NotNull
    private final String username;
    @Schema(description = "性别", example = "MALE")
    private final String sex;
}
