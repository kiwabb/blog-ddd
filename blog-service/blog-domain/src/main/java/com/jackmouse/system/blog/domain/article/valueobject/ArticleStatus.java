package com.jackmouse.system.blog.domain.article.valueobject;

/**
 * @ClassName ArticleStatus
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/7 11:29
 * @Version 1.0
 **/
public enum ArticleStatus {
    DRAFT,           // 草稿
    PUBLISHED,       // 已发布
    PENDING_APPROVAL,// 待审核
    DELETED,         // 已删除
    TAKEN_DOWN       // 已下架
}
