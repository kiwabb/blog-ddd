package com.jackmouse.system.blog.domain.article.entity;

import com.jackmouse.system.blog.domain.article.valueobject.TagId;
import com.jackmouse.system.blog.domain.article.valueobject.TagName;
import com.jackmouse.system.blog.domain.entity.BaseEntity;

/**
 * @ClassName Tag
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/7 11:28
 * @Version 1.0
 **/
public class Tag extends BaseEntity<TagId> {
    private final TagName name;


    private Tag(Builder builder) {
        setId(builder.tagId);
        name = builder.name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public TagName getName() {
        return name;
    }

    public static final class Builder {
        private TagId tagId;
        private TagName name;

        private Builder() {
        }

        public Builder id(TagId val) {
            tagId = val;
            return this;
        }

        public Builder name(TagName val) {
            name = val;
            return this;
        }

        public Tag build() {
            return new Tag(this);
        }
    }
}
