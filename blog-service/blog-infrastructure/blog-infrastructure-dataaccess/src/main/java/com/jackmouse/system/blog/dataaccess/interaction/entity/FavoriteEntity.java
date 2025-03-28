package com.jackmouse.system.blog.dataaccess.interaction.entity;

import com.jackmouse.system.blog.domain.interaction.entity.Favorite;
import com.jackmouse.system.blog.domain.interaction.entity.Like;
import com.jackmouse.system.blog.domain.interaction.valueobject.*;
import com.jackmouse.system.blog.domain.valueobject.UserId;
import com.jackmouse.system.entity.ToData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * @ClassName LikeEntity
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/21 13:06
 * @Version 1.0
 **/
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "favorite", schema = "blog")
public class FavoriteEntity implements ToData<Favorite> {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "target_id", nullable = false)
    private UUID targetId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "favorite_type", nullable = false)
    private FavoriteType targetType;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "last_modified")
    private OffsetDateTime lastModified;

    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Version
    @Column(name = "version")
    private Long version = 0L;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;



    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    public static FavoriteEntity from(Favorite favorite) {
        return FavoriteEntity.builder()
                .id(favorite.getId() == null ? null : favorite.getId().getValue())
                .targetId(favorite.getTargetId().value())
                .userId(favorite.getUserId().getValue())
                .targetType(favorite.getFavoriteType())
                .active(favorite.getInteractionStatus().isActive())
                .version(favorite.getVersion() == null ? null : favorite.getVersion().getValue())
                .lastModified(favorite.getInteractionStatus().getLastModified())
                .build();
    }

    @Override
    public Favorite toData() {
        return Favorite.builder()
                .id(new FavoriteId(id))
                .targetId(new TargetId(targetId))
                .userId(new UserId(userId))
                .favoriteType(targetType)
                .interactionStatus(new InteractionStatus(active, lastModified))
                .version(new com.jackmouse.system.blog.domain.valueobject.Version(version))
                .build();
    }
}
