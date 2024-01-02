package com.xht.redis.controller;


import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 缓存穿透
 */
@RestController
@RequestMapping("/penetrate")
@Slf4j
public class RedisPenetrateController {

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Autowired
    Redisson redisson;

    @GetMapping("/null")
    public String byNull(){
        //1.查缓存
        log.info("1.查缓存");
        Object test = redisTemplate.opsForValue().get("test");
        if (test == null){
            //2.查数据库
            log.info("2.查数据库");
            redisTemplate.opsForValue().setIfAbsent("test"," ",5, TimeUnit.SECONDS);
        }

        return "hello";
    }

    /**
     * 布隆过滤器
     * 有不一定有
     * 没有一定没有
     * @return
     */
    @GetMapping("/bloom/{id}")
    public String byBloom(@PathVariable String id){

        //布隆过滤器
        RBloomFilter<Object> bloomFilter = redisson.getBloomFilter("bloom");
        // 初始化布隆过滤器：初始化大小、误差率
        bloomFilter.tryInit(100000000L, 0.03);
        bloomFilter.add("a");
        bloomFilter.add("b");
        bloomFilter.add("c");
        bloomFilter.add("d");

        boolean contains = bloomFilter.contains(id);
        log.info("布隆过滤器判断："+contains);
        if (contains){
            //1.查缓存
            log.info("1.查缓存");
            Object test = redisTemplate.opsForValue().get("test-bloom");
            if (test == null){
                //2.查数据库
                log.info("2.查数据库");
//                redisTemplate.opsForValue().setIfAbsent("test"," ",5, TimeUnit.SECONDS);
            }

            return "命中";
        }

        return "布隆过滤器未命中";
    }
}
