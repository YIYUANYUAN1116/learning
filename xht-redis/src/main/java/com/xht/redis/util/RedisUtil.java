package com.xht.redis.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-21 21:12
 **/
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    private static final int count=100;

    public void delBigHash(String bigHashKey){
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        ScanOptions scanOptions = ScanOptions.scanOptions().count(count).match("*").build();
        try (Cursor<Map.Entry<Object, Object>> scan = hashOperations.scan(bigHashKey,scanOptions);){
            while (scan.hasNext()){
                Map.Entry<Object, Object> next = scan.next();
                hashOperations.delete(bigHashKey,next.getKey());
            }
        }
    }


    public void delBigString(String bigStringKey){
        redisTemplate.delete(bigStringKey);
    }

    public void delBigList(String bigListKey){
        ListOperations<String, Object> listOperations = redisTemplate.opsForList();
        Long size = listOperations.size(bigListKey);
        if (size == null)return;
        int counter = 0;
        while (counter < size){
            //保留count 至 size 的元素，删除其他
            listOperations.trim(bigListKey,count,size);
            counter += count;
        }
    }

    public void delSetList(String bigSetKey){
        SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
        ScanOptions scanOptions = ScanOptions.scanOptions().count(count).match("*").build();
        try (Cursor<Object> scan = setOperations.scan(bigSetKey,scanOptions);){
            while (scan.hasNext()){
                setOperations.remove(bigSetKey,scan.next());
            }
        }
    }

    public void delZSetList(String bigZSetKey){
        ZSetOperations<String, Object> opsForZSet = redisTemplate.opsForZSet();
        ScanOptions scanOptions = ScanOptions.scanOptions().count(count).match("*").build();
        try (Cursor<ZSetOperations.TypedTuple<Object>> scan = opsForZSet.scan(bigZSetKey,scanOptions)){
            while (scan.hasNext()){
                opsForZSet.remove(bigZSetKey,scan.next().getValue());
            }
        }
    }
}
