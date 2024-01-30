package com.xht.starter.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : YIYUANYUAN
 * @date: 2024/1/30  16:16
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
@ConfigurationProperties(prefix = "xht")
public class XhtProperties {
    private String name;
    private String passwd;
}
