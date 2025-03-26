package com.jackmouse.system.blog.domain.valueobject;

/**
 * @ClassName ArticleContent
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/7 11:23
 * @Version 1.0
 **/
public record Content(String value) {
    public Content generateSummary(int maxLength) {
        String summary = value.length() > maxLength ?
                value.substring(0, maxLength - 3) + "..." :
                value;
        return new Content(summary); // ⭐️返回新值对象
    }
}
