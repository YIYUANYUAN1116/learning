package com.xht.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-21 22:01
 **/

@RestController
@RequestMapping("/ops")
public class RedisController {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @GetMapping("/get")
    private Object get(@RequestParam String key){
        Object value = redisTemplate.opsForValue().get(key);
        if (value != null){
            return value;
        }else {
            synchronized (this){
                value = redisTemplate.opsForValue().get(key);
                if (value == null) {
                    //查数据库
                    value = "查数据库";
                    redisTemplate.opsForValue().set(key, value, 30, TimeUnit.HOURS);
                }
                return value;
            }
        }
    }

    @GetMapping("/inter")
    private List<Object> inter(@RequestParam String A,@RequestParam String B){
        SetOperations<String, Object> setOperations = redisTemplate.opsForSet();

        List<String> listB = new ArrayList<>();
        List<String> listA = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            listB.add("id_"+i);
            if (i>10){
                listA.add("id_"+i);
            }
        }
        String[] stringA = new String[listA.size()];
        stringA = listA.toArray(stringA);
        setOperations.add("user_a",stringA);

        String[] stringB = new String[listB.size()];
        stringB = listA.toArray(stringB);
        setOperations.add("user_B",stringB);

        Set<Object> intersect = setOperations.intersect("user_a", "user_b");

        assert intersect != null;
        return intersect.stream().toList();
    }
}
