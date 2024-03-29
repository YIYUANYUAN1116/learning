package com.xht.security;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.xht.security.mapper")
public class XhtSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(XhtSecurityApplication.class, args);
	}

}
