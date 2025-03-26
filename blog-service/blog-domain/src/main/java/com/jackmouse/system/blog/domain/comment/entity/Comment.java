package com.jackmouse.system.blog.domain.comment.entity;

import com.jackmouse.system.blog.domain.comment.valueobject.CommentId;
import com.jackmouse.system.blog.domain.comment.valueobject.CommentTargetType;
import com.jackmouse.system.blog.domain.entity.AggregateRoot;
import com.jackmouse.system.blog.domain.interaction.valueobject.TargetId;
import com.jackmouse.system.blog.domain.valueobject.Content;
import com.jackmouse.system.blog.domain.valueobject.Path;
import com.jackmouse.system.blog.domain.valueobject.UserId;

import java.util.Optional;


/**
 * @ClassName Comment
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/26 08:45
 * @Version 1.0
 **/
public class Comment extends AggregateRoot<CommentId> {
    private final TargetId targetId;
    private final CommentTargetType targetType;
    private final UserId userId;
    private final Content content;
    private final CommentId parentCommentId;
    private Path path;

    private Comment(Builder builder) {
        setId(builder.id);
        targetId = builder.targetId;
        targetType = builder.targetType;
        userId = builder.userId;
        content = builder.content;
        parentCommentId = builder.parentCommentId;
        path = builder.path;
    }

    public CommentId getParentCommentId() {
        return parentCommentId;
    }

    public TargetId getTargetId() {
        return targetId;
    }

    public CommentTargetType getTargetType() {
        return targetType;
    }

    public UserId getUserId() {
        return userId;
    }

    public Content getContent() {
        return content;
    }

    public Path getPath() {
        return path;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void generatePath(Comment parentComment) {
        path = parentComment == null ?
                Path.createRootPath()
                : parentComment.getPath().createChildPath(getId().getValue());
    }

    public static final class Builder {
        private CommentId id;
        private TargetId targetId;
        private CommentTargetType targetType;
        private UserId userId;
        private Content content;
        private CommentId parentCommentId;
        private Path path;

        private Builder() {
        }

        public Builder id(CommentId val) {
            id = val;
            return this;
        }

        public Builder targetId(TargetId val) {
            targetId = val;
            return this;
        }

        public Builder targetType(CommentTargetType val) {
            targetType = val;
            return this;
        }

        public Builder userId(UserId val) {
            userId = val;
            return this;
        }

        public Builder content(Content val) {
            content = val;
            return this;
        }

        public Builder parentCommentId(CommentId val) {
            parentCommentId = val;
            return this;
        }

        public Builder path(Path val) {
            path = val;
            return this;
        }

        public Comment build() {
            return new Comment(this);
        }
    }
}
