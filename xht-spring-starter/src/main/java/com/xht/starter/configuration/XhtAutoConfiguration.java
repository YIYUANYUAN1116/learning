package com.xht.starter.configuration;

import com.xht.starter.controller.XhtController;
import com.xht.starter.properties.XhtProperties;
import com.xht.starter.service.XhtService;
import com.xht.starter.service.impl.XhtServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author : YIYUANYUAN
 * @date: 2024/1/30  16:28
 */

//Import 注解给该starter 导入所有需要用到的功能
@Import({XhtController.class, XhtProperties.class, XhtServiceImpl.class})
@Configuration
public class XhtAutoConfiguration {
}
