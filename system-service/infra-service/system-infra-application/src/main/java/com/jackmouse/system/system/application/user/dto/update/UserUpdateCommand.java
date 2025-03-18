package com.jackmouse.system.system.application.user.dto.update;

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
public class UserUpdateCommand {
    @Schema(description = "用户ID", example = "1")
    @NotNull
    private final Long id;
    @Schema(description = "用户类型", example = "ADMIN")
    @NotNull
    private final String userType;
    @Schema(description = "用户名", example = "jackmouse")
    @NotNull
    private final String email;
    @Schema(description = "邮件", example = "13800000000@qq.com")
    @NotNull
    private final String mobile;
    @Schema(description = "用户状态", example = "NORMAL")
    @NotNull
    private final String status;
    @Schema(description = "头像", example = "https://example.com/avatar.jpg")
    @NotNull
    private final String avatar;
    @Schema(description = "用户名", example = "jackmouse")
    @NotNull
    private final String username;
    @Schema(description = "版本号", example = "1")
    private final long version;
    @Schema(description = "性别", example = "MALE")
    private final String sex;
}
