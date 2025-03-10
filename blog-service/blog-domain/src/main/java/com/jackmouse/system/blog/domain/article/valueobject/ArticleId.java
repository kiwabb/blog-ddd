package com.jackmouse.system.blog.domain.article.valueobject;

import com.jackmouse.system.blog.domain.valueobject.BaseId;

import java.util.UUID;

/**
 * @ClassName ArticleId
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/7 10:42
 * @Version 1.0
 **/
public class ArticleId extends BaseId<UUID> {
    public ArticleId(UUID value) {
        super(value);
    }
}
