package com.xht.rabbitmq.Listener;

import com.rabbitmq.client.Channel;
import com.xht.rabbitmq.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author : YIYUANYUAN
 * @description :
 * @date: 2024/1/5  10:48
 */
@Component
@Slf4j
public class DeadConsumer {

    @RabbitListener(queues = "deadQueue")
    public void receiveDeadMsg(Order order,Message message, Channel channel){
        log.info("死信队列收到消息："+order.toString());
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @RabbitListener(queues = "pluginDelayQueue")
    public void receivePluginDeadMsg(Order order,Message message, Channel channel){
        log.info("插件延时队列收到消息："+order.toString());
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
