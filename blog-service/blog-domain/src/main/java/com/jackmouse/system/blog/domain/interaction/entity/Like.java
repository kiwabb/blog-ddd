package com.jackmouse.system.blog.domain.interaction.entity;

import com.jackmouse.system.blog.domain.entity.AggregateRoot;
import com.jackmouse.system.blog.domain.exception.BlogDomainException;
import com.jackmouse.system.blog.domain.interaction.valueobject.LikeId;
import com.jackmouse.system.blog.domain.interaction.valueobject.InteractionStatus;
import com.jackmouse.system.blog.domain.interaction.valueobject.TargetType;
import com.jackmouse.system.blog.domain.interaction.valueobject.TargetId;
import com.jackmouse.system.blog.domain.valueobject.UserId;
import com.jackmouse.system.blog.domain.valueobject.Version;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

/**
 * @ClassName ArticleInteraction
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/21 10:10
 * @Version 1.0
 **/
public class Like extends AggregateRoot<LikeId> {
    private final TargetId targetId;
    private final UserId userId;
    private final TargetType targetType;
    private final Version version;
    private InteractionStatus interactionStatus;

    private Like(Builder builder) {
        setId(builder.likeId);
        targetId = builder.targetId;
        userId = builder.userId;
        targetType = builder.targetType;
        version = builder.version;
        interactionStatus = builder.interactionStatus;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Version getVersion() {
        return version;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public TargetId getTargetId() {
        return targetId;
    }

    public UserId getUserId() {
        return userId;
    }

    public InteractionStatus getInteractionStatus() {
        return interactionStatus;
    }

    public void toggleOperation() {
        //validateOperationInterval();
        this.interactionStatus =
                new InteractionStatus(true, OffsetDateTime.now());
    }

    private void validateOperationInterval() {
        if (this.interactionStatus != null &&
                Duration.between(this.interactionStatus.getLastModified(), OffsetDateTime.now()).toMinutes() < 1) {
            throw new BlogDomainException("操作过于频繁");
        }
    }

    public void changeInteractionStatus(Boolean isActive) {
        //validateOperationInterval();
        this.interactionStatus =
                new InteractionStatus(isActive, OffsetDateTime.now());
    }

    public static final class Builder {
        private LikeId likeId;
        private TargetId targetId;
        private UserId userId;
        private TargetType targetType;
        private Version version;
        private InteractionStatus interactionStatus;

        private Builder() {
        }

        public Builder likeId(LikeId val) {
            likeId = val;
            return this;
        }

        public Builder targetId(TargetId val) {
            targetId = val;
            return this;
        }

        public Builder userId(UserId val) {
            userId = val;
            return this;
        }

        public Builder targetType(TargetType val) {
            targetType = val;
            return this;
        }

        public Builder version(Version val) {
            version = val;
            return this;
        }

        public Builder interactionStatus(InteractionStatus val) {
            interactionStatus = val;
            return this;
        }

        public Like build() {
            return new Like(this);
        }
    }
}
