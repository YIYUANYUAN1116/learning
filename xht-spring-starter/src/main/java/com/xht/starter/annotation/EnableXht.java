package com.xht.starter.annotation;

import com.xht.starter.configuration.XhtAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author : YIYUANYUAN
 * @date: 2024/1/30  16:25
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({XhtAutoConfiguration.class})
@ConditionalOnWebApplication
public @interface EnableXht {
}
