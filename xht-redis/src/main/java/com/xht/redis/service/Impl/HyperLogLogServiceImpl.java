package com.xht.redis.service.Impl;

import io.micrometer.core.instrument.util.TimeUtils;
import jakarta.annotation.PostConstruct;
import jodd.time.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author : YIYUANYUAN
 * @description :
 * @date: 2024/1/3  19:43
 */
@Service
@Slf4j
public class HyperLogLogServiceImpl {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @PostConstruct
    public void init(){
        log.info("------模拟后台有用户点击首页，每个用户来自不同ip地址");
        new Thread(()->{
            String ip = null;
            for (int i = 1; i <= 1000; i++) {
                Random r = new Random();
                ip = r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256);
                Long hll = redisTemplate.opsForHyperLogLog().add("hll", ip);
                log.info("ip={},该ip地址访问首页的次数={}",ip,hll);
            }
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"T1").start();
    }
}
