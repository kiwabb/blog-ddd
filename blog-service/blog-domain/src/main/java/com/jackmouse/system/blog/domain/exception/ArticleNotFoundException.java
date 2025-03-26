package com.jackmouse.system.blog.domain.exception;

import java.util.UUID;

/**
 * @ClassName ArticleNotFoundException
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/26 10:39
 * @Version 1.0
 **/
public class ArticleNotFoundException extends BlogNotFoundException{
    public ArticleNotFoundException(UUID id) {
        super("文章不存在，id：" + id);
    }
}
