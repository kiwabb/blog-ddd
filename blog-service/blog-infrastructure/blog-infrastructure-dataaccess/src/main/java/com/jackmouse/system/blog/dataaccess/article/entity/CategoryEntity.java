package com.jackmouse.system.blog.dataaccess.article.entity;

import com.jackmouse.system.blog.domain.article.entity.Category;
import com.jackmouse.system.blog.domain.article.valueobject.CategoryId;
import com.jackmouse.system.blog.domain.article.valueobject.CategoryName;
import com.jackmouse.system.entity.ToData;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @ClassName Category
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/7 17:08
 * @Version 1.0
 **/


@Data
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category", schema = "blog")
public class CategoryEntity implements ToData<Category> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer sort = 0;

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
    public Category toData() {
        return Category.builder()
                .id(new CategoryId(id))
                .name(new CategoryName(name))
                .sort(new com.jackmouse.system.blog.domain.article.valueobject.Sort(sort))
                .build();
    }
}

