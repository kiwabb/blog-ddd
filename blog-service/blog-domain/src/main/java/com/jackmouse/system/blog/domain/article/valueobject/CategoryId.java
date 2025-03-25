package com.jackmouse.system.blog.domain.article.valueobject;

import com.jackmouse.system.blog.domain.entity.BaseEntity;
import com.jackmouse.system.blog.domain.valueobject.BaseId;

/**
 * @ClassName CategoryId
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/25 13:32
 * @Version 1.0
 **/
public class CategoryId extends BaseId<Long> {
    public CategoryId(Long value) {
        super(value);
    }
}
