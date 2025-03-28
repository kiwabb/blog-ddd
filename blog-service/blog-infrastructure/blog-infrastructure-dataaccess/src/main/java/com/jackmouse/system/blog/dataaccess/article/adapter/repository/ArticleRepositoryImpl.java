package com.jackmouse.system.blog.dataaccess.article.adapter.repository;

import com.jackmouse.system.blog.dataaccess.article.entity.ArticleEntity;
import com.jackmouse.system.blog.dataaccess.article.entity.CategoryEntity;
import com.jackmouse.system.blog.dataaccess.article.entity.TagEntity;
import com.jackmouse.system.blog.dataaccess.article.mapper.ArticleDataAccessMapper;
import com.jackmouse.system.blog.dataaccess.article.repoository.ArticleJpaRepository;
import com.jackmouse.system.blog.dataaccess.article.repoository.CategoryJpaRepository;
import com.jackmouse.system.blog.dataaccess.article.repoository.TagJpaRepository;
import com.jackmouse.system.blog.domain.article.entity.Article;
import com.jackmouse.system.blog.domain.article.entity.Category;
import com.jackmouse.system.blog.domain.article.entity.Tag;
import com.jackmouse.system.blog.domain.article.query.ArticleSummary;
import com.jackmouse.system.blog.domain.article.repository.ArticleRepository;
import com.jackmouse.system.blog.domain.article.valueobject.ArticleId;
import com.jackmouse.system.blog.domain.article.valueobject.ArticleStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName ArticleRespositoryImpl
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/7 16:03
 * @Version 1.0
 **/
@Component
public class ArticleRepositoryImpl implements ArticleRepository {

    private final ArticleJpaRepository articleJpaRepository;
    private final CategoryJpaRepository categoryJpaRepository;
    private final TagJpaRepository tagJpaRepository;
    private final ArticleDataAccessMapper articleDataAccessMapper;

    public ArticleRepositoryImpl(ArticleJpaRepository articleJpaRepository,
                                 CategoryJpaRepository categoryJpaRepository, TagJpaRepository tagJpaRepository, ArticleDataAccessMapper articleDataAccessMapper) {
        this.articleJpaRepository = articleJpaRepository;
        this.categoryJpaRepository = categoryJpaRepository;
        this.tagJpaRepository = tagJpaRepository;
        this.articleDataAccessMapper = articleDataAccessMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ArticleSummary> findPublishSortCategoryArticles() {
        List<CategoryEntity> categoryEntities = categoryJpaRepository.findTop4ByOrderBySort();
        List<ArticleEntity> articleEntities = new ArrayList<>(6 * categoryEntities.size());
        categoryEntities.forEach(categoryEntity -> {
            List<ArticleEntity> articles =
                    articleJpaRepository.findTop6ByCategoryEntityAndStatusOrderByPublishTimeDesc(categoryEntity, ArticleStatus.PUBLISHED);
            articleEntities.addAll(articles);
        });

        return articleEntities.stream()
                .map(articleDataAccessMapper::articleEntityToArticleSummary)
                .toList();
    }

    @Override
    public Optional<Article> findById(ArticleId id) {
        return articleJpaRepository.findById(id.getValue()).map(ArticleEntity::toData);
    }

    @Override
    public Boolean existById(ArticleId id) {
        return articleJpaRepository.existsById(id.getValue());
    }

    @Override
    public Article save(Article article) {
        ArticleEntity saveArticle = articleJpaRepository.save(articleDataAccessMapper.articleToArticleEntity(article));
        return articleDataAccessMapper.articleEntityToArticle(saveArticle);
    }

    @Override
    public List<Category> findCategories() {
        return categoryJpaRepository.findAll().stream().map(CategoryEntity::toData).toList();
    }

    @Override
    public List<Tag> findTags() {
        return tagJpaRepository.findAll().stream().map(TagEntity::toData).toList();
    }
}
