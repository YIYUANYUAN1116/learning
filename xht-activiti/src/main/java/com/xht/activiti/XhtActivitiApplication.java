package com.xht.activiti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {
        //activiti 默认整合security，屏蔽Security认证
        SecurityAutoConfiguration.class,
        ManagementWebSecurityAutoConfiguration.class
})
public class XhtActivitiApplication {

    public static void main(String[] args) {
        SpringApplication.run(XhtActivitiApplication.class, args);
    }

}
