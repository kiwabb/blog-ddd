package com.jackmouse.system.blog.config;

import com.jackmouse.system.blog.domain.article.ArticleQueryService;
import com.jackmouse.system.blog.domain.article.ArticleQueryServiceImpl;
import com.jackmouse.system.blog.domain.article.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName BeanConfiguration
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/7 15:38
 * @Version 1.0
 **/
@Configuration
public class BeanConfiguration {

    @Autowired
    private ArticleRepository articleRepository;

    @Bean
    public ArticleQueryService articleQueryService(){
        return new ArticleQueryServiceImpl(articleRepository);
    }

}
