package com.jackmouse.system.blog.application.comment.dto.query;

import com.jackmouse.system.blog.domain.comment.specification.query.CommentPageQuerySpec;
import com.jackmouse.system.blog.domain.interaction.valueobject.TargetId;
import com.jackmouse.system.blog.domain.valueobject.Depth;
import com.jackmouse.system.blog.domain.valueobject.PageParam;
import com.jackmouse.system.dto.query.PageQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

/**
 * @ClassName TagetIdQuery
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/25 14:06
 * @Version 1.0
 **/
@Getter
@Builder
@AllArgsConstructor
public class TargetIdQuery extends PageQuery {
    private UUID targetId;
    private int depth = 0;
    private int previewReplyCount = 3;

    public CommentPageQuerySpec toCommentQuerySpec() {
        return CommentPageQuerySpec.builder()
                        .targetId(new TargetId(targetId))
                        .pageParam(new PageParam(getPage(), getSize(), this))
                        .depth(new Depth(depth))
                .build();
    }
}
