package com.jackmouse.system.blog.application.interaction.dto;

import com.jackmouse.system.blog.domain.interaction.valueobject.TargetType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @ClassName LikeArticleCommand
 * @Description 文章/评论点赞/取消点赞命令
 * @Author zhoujiaangyao
 * @Date 2025/3/21 09:25
 * @Version 1.0
 **/
@Getter
@Builder
@AllArgsConstructor
public class InteractionCommand {
    private UUID targetId;
    private Boolean isActive;
    private Long userId;
    @Setter
    private TargetType targetType;
}
