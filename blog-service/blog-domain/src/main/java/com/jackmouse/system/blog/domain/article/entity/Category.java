package com.jackmouse.system.blog.domain.article.entity;

import com.jackmouse.system.blog.domain.article.valueobject.CategoryName;
import com.jackmouse.system.blog.domain.entity.BaseEntity;

/**
 * @ClassName Category
 * @Description 分类实体
 * @Author zhoujiaangyao
 * @Date 2025/3/7 11:25
 * @Version 1.0
 **/
public class Category extends BaseEntity<Long> {
    private CategoryName name;


    public Category(Long id, CategoryName name) {
        setId(id);
        this.name = name;
    }

    private Category(Builder builder) {
        setId(builder.categoryId);
        name = builder.name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public CategoryName getName() {
        return name;
    }

    public static final class Builder {
        private Long categoryId;
        private CategoryName name;

        private Builder() {
        }

        public Builder id(Long val) {
            categoryId = val;
            return this;
        }

        public Builder name(CategoryName val) {
            name = val;
            return this;
        }

        public Category build() {
            return new Category(this);
        }
    }
}
