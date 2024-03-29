package com.xht.security;

import com.xht.security.entity.User;
import com.xht.security.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class ComXhtApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	UserMapper userMapper;

	@Test
	void test(){
		User user = new User();
		user.setUsername("test");
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String encode = bCryptPasswordEncoder.encode("123456");
		user.setPassword(encode);

		userMapper.insert(user);

	}
}
