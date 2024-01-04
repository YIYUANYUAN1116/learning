package com.xht.redis;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableKnife4j
@ComponentScan(value = "com.xht")
public class HxtRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(HxtRedisApplication.class, args);
	}

}
