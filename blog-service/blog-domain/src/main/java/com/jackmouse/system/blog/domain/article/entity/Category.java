package com.jackmouse.system.blog.domain.article.entity;

import com.jackmouse.system.blog.domain.article.valueobject.CategoryId;
import com.jackmouse.system.blog.domain.article.valueobject.CategoryName;
import com.jackmouse.system.blog.domain.article.valueobject.Sort;
import com.jackmouse.system.blog.domain.entity.BaseEntity;

/**
 * @ClassName Category
 * @Description 分类实体
 * @Author zhoujiaangyao
 * @Date 2025/3/7 11:25
 * @Version 1.0
 **/
public class Category extends BaseEntity<CategoryId> {
    private final CategoryName name;
    private final Sort sort;

    private Category(Builder builder) {
        setId(builder.categoryId);
        name = builder.name;
        sort = builder.sort;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Sort getSort() {
        return sort;
    }

    public CategoryName getName() {
        return name;
    }

    public static final class Builder {
        private CategoryId categoryId;
        private CategoryName name;
        private Sort sort;

        private Builder() {
        }

        public Builder id(CategoryId val) {
            categoryId = val;
            return this;
        }

        public Builder name(CategoryName val) {
            name = val;
            return this;
        }

        public Builder sort(Sort val) {
            sort = val;
            return this;
        }

        public Category build() {
            return new Category(this);
        }
    }
}
