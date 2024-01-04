package com.xht.common.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableKnife4j
public class Knife4jConfig {
    @Bean
    public GroupedOpenApi adminApi(){
        return GroupedOpenApi.builder()
                .group("learning-api")         // 分组名称
                .pathsToMatch("/**")  // 接口请求路径规则
                .build();
    }

    /***
     * @description 自定义接口信息
     */
    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("xht-learning API接口文档")
                        .version("1.0")
                        .description("xht-learning API接口文档")
                        .contact(new Contact().name("xht"))); // 设定作者
    }
}
