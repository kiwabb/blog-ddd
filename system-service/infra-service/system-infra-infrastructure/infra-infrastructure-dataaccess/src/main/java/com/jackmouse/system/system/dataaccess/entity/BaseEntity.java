package com.jackmouse.system.system.dataaccess.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;

/**
 * @ClassName BaseEntity
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/17 16:40
 * @Version 1.0
 **/
@MappedSuperclass
@Data
public class BaseEntity {

    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "updated_by")
    private Long updatedBy;

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
}
