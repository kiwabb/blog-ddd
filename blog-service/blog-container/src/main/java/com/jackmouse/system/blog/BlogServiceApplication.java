package com.jackmouse.system.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @ClassName BlogServiceApplication
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/7 15:35
 * @Version 1.0
 **/
@EnableJpaRepositories
@SpringBootApplication
public class BlogServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogServiceApplication.class, args);
    }
}
