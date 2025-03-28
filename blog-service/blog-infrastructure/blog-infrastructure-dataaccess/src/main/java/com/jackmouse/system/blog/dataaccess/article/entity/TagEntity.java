package com.jackmouse.system.blog.dataaccess.article.entity;

import com.jackmouse.system.blog.domain.article.entity.Tag;
import com.jackmouse.system.blog.domain.article.valueobject.TagId;
import com.jackmouse.system.blog.domain.article.valueobject.TagName;
import com.jackmouse.system.entity.ToData;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @ClassName Tag
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/7 17:09
 * @Version 1.0
 **/
@Data
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tag", schema = "blog")
public class TagEntity implements ToData<Tag> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDeleted = false;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }


    @Override
    public Tag toData() {
        return Tag.builder()
                .id(new TagId(id))
                .name(new TagName(name))
                .build();
    }
}
