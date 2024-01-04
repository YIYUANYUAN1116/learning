package com.xht.redis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author : YIYUANYUAN
 * @description :
 * @date: 2024/1/4  17:43
 */

@Tag(name = "RedissonController")
@RequestMapping("/redisson")
@RestController
@Slf4j
public class RedissonController {

    @Autowired
    private Redisson redisson;


    @GetMapping("/lock")
    @Operation(summary = "lock")
    public void getLock(){
        new Thread(()->{

            RLock lock = redisson.getLock("lock");
            if (!lock.isLocked()){
                try {
                    lock.lock(10, TimeUnit.SECONDS);
                    log.info("加锁成功"+Thread.currentThread().getName());
                    TimeUnit.SECONDS.sleep(5);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }finally {
                    lock.unlock();
                }
            }else {
                log.info("加锁失败" + Thread.currentThread().getName());
            }
        }).start();
    }

}
