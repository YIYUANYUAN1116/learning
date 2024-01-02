package com.xht.redis;

import com.xht.redis.model.entity.User;
import com.xht.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootTest
@Slf4j
class HxtRedisApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	RedisService redisService;

	@Autowired
	RedisTemplate<String,Object> redisTemplate;


	private final String redis_test="redis_test";
	@Test
	public void  testStringRedis(){
		User zs = new User("张三", "123456");
		redisService.set(redis_test,zs);
		User o = (User)redisService.get(redis_test);
		log.info(o.toString());
	}


	@Test
	public void  testHash(){
		String key = redis_test+"hash";
		User zs = new User("张三", "123456");
		redisService.hSet(key,"zs",zs);
		User ls = new User("李四", "123456");
		redisService.hSet(key,"ls",ls);

		User ls2 = new User("李四2", "123456");
		Boolean ls3 = redisService.hHasKey(key, "ls");
		Boolean ls1 = redisService.hSet(key, "ls", ls2);
		Map<Object, Object> objectObjectMap = redisService.hGetAll(key);
		log.info("hset end"+ls3+ls1);
	}

	@Test
	public void testSet(){
		String key = redis_test+"set";
		User zs = new User("张三", "123456");
		User ls = new User("李四", "123456");

		Long aLong = redisService.sAdd(key, zs, ls);
		Long aLong1 = redisService.sSize(key);
		Set<Object> objects = redisService.sMembers(key);
		Boolean aBoolean = redisService.sIsMember(key, zs);
		log.info("zset end");
	}

	@Test
	public void testList(){
		String key = redis_test+"list";
		User zs = new User("张三", "123456");
		User ls = new User("李四", "123456");
		User we = new User("王二", "123456");
		User we1 = new User("王二", "123456");
		User we2 = new User("王二", "123456");
		redisService.lPushAll(key,zs,ls,we,we1,we2);

		Long aLong = redisService.lSize(key);
		Object o = redisService.lIndex(key, 2);
		List<Object> objects = redisService.lRange(key, 0, 1);
		log.info("list end");
	}


}
