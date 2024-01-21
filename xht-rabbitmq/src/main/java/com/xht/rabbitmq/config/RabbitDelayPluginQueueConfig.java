package com.xht.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : YIYUANYUAN
 * @description :
 * @date: 2024/1/5  14:51
 */

@Configuration
public class RabbitDelayPluginQueueConfig {

    @Bean("pluginDelayExchange")
    public CustomExchange pluginDelayExchange(){
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-delayed-type", "direct");
        return new CustomExchange("pluginDelayExchange","x-delayed-message",true,false,arguments);
    }

    /**
     * 创建队列：
     * 指定死信交换机、路由KEY
     */
    @Bean("pluginDelayQueue")
    public Queue pluginDelayQueue() {
        return QueueBuilder.durable("pluginDelayQueue").build();
    }

    @Bean
    public Binding pluginDelayBinding(){
        return BindingBuilder.bind(pluginDelayQueue()).to(pluginDelayExchange()).with("plugin.delay.key").and(null);
    }

}
