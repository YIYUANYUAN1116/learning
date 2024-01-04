package com.xht.redis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : YIYUANYUAN
 * @description :
 * @date: 2024/1/3  19:54
 */

@Tag(name = "亿级UV的redis统计方案")
@RestController
@RequestMapping("/hll")
public class HyperLogLogController {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Operation(summary = "获得IP去重后的首页访问量")
    @GetMapping
    public long uv()
    {
        //pfcount
        //HyperLogLog实际上不会存储每个元素的值，它使用的是概率算法，通过存储元素的hash值的第一个1的位置
        //在 Redis 里面，每个 HyperLogLog 键只需要花费 12 KB 内存，就可以计算接近 2^64 个不同元素的基 数。这和计算基数时，元素越多耗费内存就越多的集合形成鲜明对比。
        return redisTemplate.opsForHyperLogLog().size("hll");
    }
}
