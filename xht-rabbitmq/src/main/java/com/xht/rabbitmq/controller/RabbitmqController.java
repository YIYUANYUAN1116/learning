package com.xht.rabbitmq.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author : YIYUANYUAN
 * @description :
 * @date: 2024/1/4  15:28
 */

@Tag(name = "rabbit发消息测试接口")
@RestController
@RequestMapping("/rabbit")
public class RabbitmqController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/direct")
    @Operation(summary = "direct")
    public String direct(){

        rabbitTemplate.convertAndSend("directExchange","directRouting",getInfoMap("directExchange"));
        return "ok";
    }

    @GetMapping("/topic/1")
    @Operation(summary = "topic1")
    public String topic1(){

        rabbitTemplate.convertAndSend("topicExchange","topic.first",getInfoMap("topic.first"));
        return "ok";
    }

    @Operation(summary = "topic2")
    @GetMapping("/topic/2")
    public String topic2(){

        rabbitTemplate.convertAndSend("topicExchange","topic.second",getInfoMap("topic.second"));
        return "ok";
    }


    @Operation(summary = "fanout")
    @GetMapping("/fanout")
    public String fanout(){
        rabbitTemplate.convertAndSend("fanoutExchange","fanout.send",getInfoMap("fanout"));
        return "ok";
    }

    public Map<String, Object> getInfoMap(String str){
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "Test message, hello world! "+str;
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new  HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        return map;
    }

}
