package com.jackmouse.system.blog.domain.interaction.entity;

import com.jackmouse.system.blog.domain.entity.AggregateRoot;
import com.jackmouse.system.blog.domain.interaction.valueobject.*;
import com.jackmouse.system.blog.domain.valueobject.UserId;
import com.jackmouse.system.blog.domain.valueobject.Version;

import java.time.OffsetDateTime;

/**
 * @ClassName Favorite
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/25 10:51
 * @Version 1.0
 **/
public class Favorite extends AggregateRoot<FavoriteId> {
    private final TargetId targetId;
    private final UserId userId;
    private final FavoriteType favoriteType;
    private final Version version;
    private InteractionStatus interactionStatus;

    private Favorite(Builder builder) {
        setId(builder.id);
        targetId = builder.targetId;
        userId = builder.userId;
        favoriteType = builder.favoriteType;
        version = builder.version;
        interactionStatus = builder.interactionStatus;
    }

    public static Builder builder() {
        return new Builder();
    }

    public TargetId getTargetId() {
        return targetId;
    }

    public UserId getUserId() {
        return userId;
    }

    public FavoriteType getFavoriteType() {
        return favoriteType;
    }

    public Version getVersion() {
        return version;
    }

    public InteractionStatus getInteractionStatus() {
        return interactionStatus;
    }

    public void changeInteractionStatus(Boolean isActive) {
        //validateOperationInterval();
        this.interactionStatus =
                new InteractionStatus(isActive, OffsetDateTime.now());
    }

    public static final class Builder {
        private FavoriteId id;
        private TargetId targetId;
        private UserId userId;
        private FavoriteType favoriteType;
        private Version version;
        private InteractionStatus interactionStatus;

        private Builder() {
        }

        public Builder id(FavoriteId val) {
            id = val;
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

        public Builder favoriteType(FavoriteType val) {
            favoriteType = val;
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

        public Favorite build() {
            return new Favorite(this);
        }
    }
}
