package com.jackmouse.system.blog.dataaccess.comment.entity;

import com.jackmouse.system.blog.domain.comment.entity.Comment;
import com.jackmouse.system.blog.domain.comment.valueobject.CommentId;
import com.jackmouse.system.blog.domain.comment.valueobject.CommentTargetType;
import com.jackmouse.system.blog.domain.interaction.valueobject.TargetId;
import com.jackmouse.system.blog.domain.valueobject.Content;
import com.jackmouse.system.blog.domain.valueobject.Depth;
import com.jackmouse.system.blog.domain.valueobject.Path;
import com.jackmouse.system.blog.domain.valueobject.UserId;
import com.jackmouse.system.entity.ToData;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName Comment
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/25 16:39
 * @Version 1.0
 **/
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_deleted = false") // 软删除过滤
@Table(name = "comment", schema = "blog")
@SQLDelete(sql = "UPDATE 'blog'.comment SET is_deleted = true WHERE id = ?") // 软删除处理
public class CommentEntity implements ToData<Comment> {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "target_id", nullable = false)
    private UUID targetId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "comment_target_type", nullable = false)
    private CommentTargetType targetType;

    @Column(name = "parent_comment_id")
    private UUID parentCommentId;

    @Column(nullable = false, length = 2000)
    private String content;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "depth")
    private Integer depth;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Version
    private Long version;

    @Column(name = "is_deleted")
    @Builder.Default
    private Boolean isDeleted = false;

    @Column(name = "path")
    private String path;

    public static CommentEntity from(Comment comment) {
        return CommentEntity.builder()
                .targetId(comment.getTargetId().value())
                .userId(comment.getUserId().getValue())
                .targetType(comment.getTargetType())
                .parentCommentId(comment.getParentCommentId() == null ? null : comment.getParentCommentId().getValue())
                .content(comment.getContent().value())
                .path(comment.getPath().getPath())
                .depth(comment.getDepth().value())
                .build();
    }

    @Override
    public Comment toData() {
        return Comment.builder()
                .id(new CommentId(id))
                .targetId(new TargetId(targetId))
                .userId(new UserId(userId))
                .targetType(targetType)
                .parentCommentId(parentCommentId == null ? null : new CommentId(parentCommentId))
                .content(new Content(content))
                .path(new Path(path))
                .depth(new Depth(depth))
                .build();
    }
}
