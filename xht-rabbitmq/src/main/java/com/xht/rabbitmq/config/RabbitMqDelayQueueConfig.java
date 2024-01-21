package com.xht.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : YIYUANYUAN
 * @description :
 * @date: 2024/1/5  10:43
 */
@Configuration
public class RabbitMqDelayQueueConfig {

    private static final String DEAD_EXCHANGE = "deadExchange";

    private static final String DEAD_ROUTE_KEY = "dead.key";

    /**
     * 使用 ExchangeBuilder 创建交换机
     */
    @Bean("delayExchange")
    public Exchange bootExchange() {
        return ExchangeBuilder.directExchange("delayExchange").durable(true).build();
    }

    /**
     * 创建队列：
     * 指定死信交换机、路由KEY
     */
    @Bean("delayQueue")
    public Queue bootQueue001() {
        return QueueBuilder.durable("delayQueue").deadLetterExchange(DEAD_EXCHANGE).deadLetterRoutingKey(DEAD_ROUTE_KEY).build();
    }


    @Bean("delayQueue2")
    public Queue bootQueue002() {
        return QueueBuilder.durable("delayQueue2").withArgument("x-message-ttl",10000).deadLetterExchange(DEAD_EXCHANGE).deadLetterRoutingKey(DEAD_ROUTE_KEY).build();
    }

    /**
     * 创建绑定关系
     */
    @Bean("delayBinding")
    public Binding delayBinding(@Qualifier("delayQueue") Queue delayQueue, @Qualifier("delayExchange") Exchange delayExchange) {
        return BindingBuilder.bind(delayQueue).to(delayExchange).with("delay.key").and(null);
    }

    @Bean("delayBinding2")
    public Binding delayBinding2() {
        return BindingBuilder.bind(bootQueue002()).to(bootExchange()).with("delay.key2").and(null);
    }

}
