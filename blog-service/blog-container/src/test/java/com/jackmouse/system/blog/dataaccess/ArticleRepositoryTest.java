package com.jackmouse.system.blog.dataaccess;

import com.jackmouse.system.blog.domain.article.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @ClassName ArticleRepositoryTest
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/10 10:05
 * @Version 1.0
 **/
@Slf4j
@SpringBootTest(classes = BlogTestConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ArticleRepositoryTest {
    @Autowired
    private ArticleRepository articleRepository;

    @Test
    public void test_findPublishSortCategoryArticles() {
        articleRepository.findPublishSortCategoryArticles().forEach(article -> {
            log.info("article:{}", article);
        });
    }
}
