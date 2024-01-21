package com.xht.rabbitmq.controller;

import com.alibaba.fastjson2.JSON;
import com.xht.rabbitmq.entity.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
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


    @GetMapping("/orderByDelayQ")
    @Operation(summary = "模拟用户下单,使用延时队列")
    public String orderByDelayQ(){
        System.out.println("下订单成功，发送订单消息到MQ中....当前时间："+ LocalDateTime.now());
        Message message = new Message(JSON.toJSONBytes(getOrder(3L)));
        rabbitTemplate.convertAndSend("delayExchange", "delay.key2", message);
        return "ok";
    }


    @GetMapping("/orderByDelayM")
    @Operation(summary = "模拟用户下单,使用延时消息")
    public String orderByDelayM(){
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setExpiration("10000"); // 10秒过期
        System.out.println("下订单成功，发送订单消息到MQ中....当前时间："+ LocalDateTime.now());
        Message message = new Message(JSON.toJSONBytes(getOrder(1L)), messageProperties);
        rabbitTemplate.convertAndSend("delayExchange", "delay.key", message);

        MessageProperties messageProperties2 = new MessageProperties();
        messageProperties2.setExpiration("5000"); // 5秒过期
        System.out.println("下订单成功，发送订单消息到MQ中....当前时间："+ LocalDateTime.now());
        Message message2 = new Message(JSON.toJSONBytes(getOrder(2L)), messageProperties2);
        rabbitTemplate.convertAndSend("delayExchange", "delay.key", message2);
        return "ok";
    }


    @GetMapping("/order")
    @Operation(summary = "模拟用户下单,使用延时插件")
    public String order(){
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setDelay(10000); // 10秒过期
        System.out.println("下订单成功，发送订单消息到MQ中....当前时间："+ LocalDateTime.now());
        Message message = new Message(JSON.toJSONBytes(getOrder(4L)), messageProperties);
        rabbitTemplate.convertAndSend("pluginDelayExchange", "plugin.delay.key", message);


        MessageProperties messageProperties2 = new MessageProperties();
        messageProperties2.setDelay(5000); //5秒过期
        System.out.println("下订单成功，发送订单消息到MQ中....当前时间："+ LocalDateTime.now());
        Message message2 = new Message(JSON.toJSONBytes(getOrder(5L)), messageProperties2);
        rabbitTemplate.convertAndSend("pluginDelayExchange", "plugin.delay.key", message2);
        return "ok";
    }


    public Order getOrder(Long id){
        return new Order(id, new BigDecimal(10000));
    }



}
