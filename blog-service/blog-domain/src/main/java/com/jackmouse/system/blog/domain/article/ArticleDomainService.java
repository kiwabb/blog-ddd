package com.jackmouse.system.blog.domain.article;

import com.jackmouse.system.blog.domain.article.entity.Article;
import com.jackmouse.system.blog.domain.article.event.ArticleCreatedEvent;

/**
 * @ClassName ArticleQueryService
 * @Description 文章查询服务
 * @Author zhoujiaangyao
 * @Date 2025/3/7 11:40
 * @Version 1.0
 **/
public interface ArticleDomainService {

    ArticleCreatedEvent validateAndInitiateArticle(Article article);
}
