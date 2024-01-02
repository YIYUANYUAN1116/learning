package com.xht.redis.msg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PubMsgTask {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Scheduled(cron = "0 0/5 * * * ?  ")
    public void publish(){
        log.info("redisTemplate 发消息");
        redisTemplate.convertAndSend("test-channel","hello world");
    }
}
