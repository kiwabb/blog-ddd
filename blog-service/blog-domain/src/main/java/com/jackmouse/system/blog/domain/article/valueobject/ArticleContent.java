package com.jackmouse.system.blog.domain.article.valueobject;

/**
 * @ClassName ArticleContent
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/7 11:23
 * @Version 1.0
 **/
public record ArticleContent(String value) {
    public ArticleContent generateSummary(int maxLength) {
        String summary = value.length() > maxLength ?
                value.substring(0, maxLength - 3) + "..." :
                value;
        return new ArticleContent(summary); // ⭐️返回新值对象
    }
}
