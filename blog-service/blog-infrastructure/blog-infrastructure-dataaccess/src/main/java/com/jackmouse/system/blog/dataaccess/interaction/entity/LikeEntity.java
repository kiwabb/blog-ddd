package com.jackmouse.system.blog.dataaccess.interaction.entity;

import com.jackmouse.system.blog.domain.interaction.entity.Like;
import com.jackmouse.system.blog.domain.interaction.valueobject.InteractionStatus;
import com.jackmouse.system.blog.domain.interaction.valueobject.LikeId;
import com.jackmouse.system.blog.domain.interaction.valueobject.TargetId;
import com.jackmouse.system.blog.domain.interaction.valueobject.TargetType;
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
 **/@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`like`", schema = "blog")
public class LikeEntity implements ToData<Like> {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "target_id", nullable = false)
    private UUID targetId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false)
    private TargetType targetType;  // 注意：根据DDD建议应改为枚举类型，需要调整数据库设计

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

    public static LikeEntity from(Like like) {
        return LikeEntity.builder()
                .id(like.getId() == null ? null : like.getId().getValue())
                .targetId(like.getTargetId().value())
                .userId(like.getUserId().getValue())
                .targetType(like.getTargetType())
                .active(like.getInteractionStatus().isActive())
                .version(like.getVersion() == null ? null : like.getVersion().getValue())
                .lastModified(like.getInteractionStatus().getLastModified())
                .build();
    }

    @Override
    public Like toData() {
        return Like.builder()
                .likeId(new LikeId(id))
                .targetId(new TargetId(targetId))
                .userId(new UserId(userId))
                .targetType(targetType)
                .interactionStatus(new InteractionStatus(active, lastModified))
                .version(new com.jackmouse.system.blog.domain.valueobject.Version(version))
                .build();
    }
}
