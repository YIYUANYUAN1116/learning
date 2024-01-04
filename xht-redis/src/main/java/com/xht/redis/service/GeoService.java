package com.xht.redis.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : YIYUANYUAN
 * @description :
 * @date: 2024/1/4  9:30
 */

@Service
@Slf4j
public class GeoService {
    public static final String CITY ="city";
    @Autowired
    private RedisTemplate redisTemplate;

    public String geoAdd(){
        Map<String, Point> map= new HashMap<>();
        HashMap<String, Point> hashMap = new HashMap<>();
        map.put("天安门",new Point(116.403963,39.915119));
        map.put("故宫",new Point(116.403414 ,39.924091));
        map.put("长城" ,new Point(116.024067,40.362639));
        map.put("长城" ,new Point(116.024067,40.362639));
        map.put("冬奥公园",new Point(116.177752,39.898995));
        map.put("首都机场",new Point(116.616988,40.088262));
        redisTemplate.opsForGeo().add(CITY,map);
        return map.toString();
    }

    public Point position(String member){
        Point res = null;
        List<Point> position = redisTemplate.opsForGeo().position(CITY, member);
        if (position!= null && !position.isEmpty()) res =position.get(0);
        return res;
    }

    public String hash(String member){
        //geohash算法生成的base32编码值
        String res = null;
        List<String> hash = redisTemplate.opsForGeo().hash(CITY, member);
        if (hash!= null && !hash.isEmpty()) res = hash.get(0);
        return res;
    }

    public Distance distance(String member1,String member2){
        Distance distance = redisTemplate.opsForGeo().distance(CITY, member1, member2, RedisGeoCommands.DistanceUnit.KILOMETERS);
        return distance;
    }

    //通过经度，纬度查找附近的
    public GeoResults radiusByxy(){
        //通过经度，纬度查找附近的,北京王府井位置116.418017,39.914402
        Circle circle = new Circle(116.418017, 39.914402, Metrics.KILOMETERS.getMultiplier());
        RedisGeoCommands.GeoRadiusCommandArgs commandArgs = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                .includeDistance() //返回值包含距离
                .includeCoordinates()//返回值包含坐标
                .sortAscending()//升序
                .limit(50);// 显示返回数量
        return redisTemplate.opsForGeo().radius(CITY, circle, commandArgs);
    }

    //通过地方查找
    public GeoResults radiusByMember(){
        //通过地方查找附近
        String member="天安门";
        //返回50条
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                .includeDistance()
                .includeCoordinates()
                .sortAscending()
                .limit(50);
        //半径10公里内
        Distance distance=new Distance(50, Metrics.KILOMETERS);
        return redisTemplate.opsForGeo().radius(CITY,member, distance,args);
    }

}
