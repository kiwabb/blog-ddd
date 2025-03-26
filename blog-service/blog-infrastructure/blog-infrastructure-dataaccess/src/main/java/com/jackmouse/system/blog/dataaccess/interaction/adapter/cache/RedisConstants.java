package com.jackmouse.system.blog.dataaccess.interaction.adapter.cache;

/**
 * @ClassName RedisConstants
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/25 11:41
 * @Version 1.0
 **/
public class RedisConstants {
    public static final String USER_LIKE_KEY = "user:like:";
    public static final String USER_FAVORITE_KEY = "user:favorite:";
    public static final String ARTICLE_LIKE_KEY = "article:like:";
    public static final String ARTICLE_FAVORITE_KEY = "article:favorite:";
    public static final String ARTICLE_LIKE_COUNT_KEY = "article:like:count:";
    public static final String ARTICLE_FAVORITE_COUNT_KEY = "article:favorite:count:";
    public static final String ARTICLE_HOT_KEY = "article:hot:";


    static final String COMMENT_LIKE_COUNT_KEY = "blog:comment:like:count:";
    static final String COMMENT_COUNT_KEY = "blog:comment:count";

}
