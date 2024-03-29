package com.xht.kettle.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableKnife4j
public class Knife4jConfig {

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
