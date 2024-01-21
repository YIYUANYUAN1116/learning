package com.xht.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : YIYUANYUAN
 * @description :
 * @date: 2024/1/5  10:37
 */
@Configuration
public class RabbitMqDeadQueueConfig {
    private static final String DEAD_QUEUE = "deadQueue";

    private static final String DEAD_EXCHANGE = "deadExchange";

    private static final String DEAD_ROUTE_KEY = "dead.key";



    /****************死信队列********************/
    @Bean(DEAD_QUEUE)
    public Queue deadQueue(){
        return QueueBuilder.durable(DEAD_QUEUE).build();
    }

    @Bean
    public Exchange deadExchange(){
        return ExchangeBuilder.directExchange(DEAD_EXCHANGE).durable(true).build();
    }

    @Bean
    public Binding deadBinding(){
        return BindingBuilder.bind(deadQueue()).to(deadExchange()).with(DEAD_ROUTE_KEY).and(null);
    }


}
