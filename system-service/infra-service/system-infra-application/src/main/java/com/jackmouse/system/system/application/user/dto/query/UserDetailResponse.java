package com.jackmouse.system.system.application.user.dto.query;

import com.jackmouse.system.system.infra.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

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
public class UserDetailResponse {
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
    @Schema(description = "版本号", example = "1")
    private final Long version;
    @Schema(description = "创建时间", example = "2025-03-14T16:24:00Z")
    private final OffsetDateTime createdAt;
    @Schema(description = "创建人", example = "1")
    private final Long createdBy;
    @Schema(description = "更新时间", example = "2025-03-14T16:24:00Z")
    private final OffsetDateTime updatedAt;
    @Schema(description = "更新人", example = "1")
    private final Long updatedBy;

    public static UserDetailResponse fromUser(User user) {
        return UserDetailResponse.builder()
                .id(user.getId().getValue())
                .avatar(user.getAvatar().value())
                .username(user.getUsername().value())
                .email(user.getEmail().value())
                .mobile(user.getMobile().value())
                .status(user.getStatus().name())
                .userType(user.getUserType().name())
                .sex(user.getSex().name())
                .version(user.getVersion().getValue())
                .createdAt(user.getCreatedAt().value())
                .createdBy(user.getCreatedBy().value())
                .updatedAt(user.getUpdatedAt().value())
                .updatedBy(user.getUpdatedBy().value())
                .build();
    }
}
