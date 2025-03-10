package com.jackmouse.system.blog.domain.article.valueobject;


import com.jackmouse.system.blog.domain.article.exception.BlogDomainException;

/**
 * @ClassName ArticleStats
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/7 11:13
 * @Version 1.0
 **/
public record ArticleStats(
        int likes,          // 点赞数
        int favorites,      // 收藏数
        int commentCount,   // 评论数
        int readCount       // 阅读数
) {
    public ArticleStats {
        if (likes < 0) {
            throw new BlogDomainException("点赞数不能小于0");
        }
        if (favorites < 0) {
            throw new BlogDomainException("收藏数不能小于0");
        }
        if (commentCount < 0) {
            throw new BlogDomainException("评论数不能小于0");
        }
        if (readCount < 0) {
            throw new BlogDomainException("阅读数不能小于0");
        }
    }
    public ArticleStats incrementLikes() {
        return new ArticleStats(likes + 1, favorites, commentCount, readCount);
    }

    public ArticleStats incrementFavorites() {
        return new ArticleStats(likes, favorites + 1, commentCount, readCount);
    }
    public ArticleStats incrementCommentCount() {
        return new ArticleStats(likes, favorites, commentCount + 1, readCount);
    }
    public ArticleStats incrementReadCount() {
        return new ArticleStats(likes, favorites, commentCount, readCount + 1);
    }

}
