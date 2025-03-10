package com.jackmouse.system.blog.dataaccess.article.repoository;

import com.jackmouse.system.blog.dataaccess.article.entity.ArticleEntity;
import com.jackmouse.system.blog.dataaccess.article.entity.CategoryEntity;
import com.jackmouse.system.blog.domain.article.entity.Article;
import com.jackmouse.system.blog.domain.article.valueobject.ArticleStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @ClassName ArticleJpaRepository
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/10 08:37
 * @Version 1.0
 **/
@Repository
public interface ArticleJpaRepository extends JpaRepository<ArticleEntity, UUID> {

    List<ArticleEntity> findTop6ByCategoryEntityAndStatusOrderByPublishTimeDesc
            (CategoryEntity categoryEntity, ArticleStatus articleStatus);
}
