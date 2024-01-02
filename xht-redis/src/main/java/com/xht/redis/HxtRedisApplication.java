package com.xht.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HxtRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(HxtRedisApplication.class, args);
	}

}
