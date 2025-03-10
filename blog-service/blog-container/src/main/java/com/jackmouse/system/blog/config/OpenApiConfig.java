package com.jackmouse.system.blog.config;

import com.jackmouse.system.blog.response.Result;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @ClassName OpenApiConfig
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/10 10:56
 * @Version 1.0
 **/
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Blog API")
                        .version("1.0")
                        .description("API文档自动生成")
                );
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("blog-public")
                .packagesToScan("com.jackmouse.system.blog.application.dto.query") // 明确扫描包
                .build();
    }
}
