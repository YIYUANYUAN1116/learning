package com.xht.rabbitmq.Listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;


/**
 * @author : YIYUANYUAN
 * @description :
 * @date: 2024/1/4  15:58
 */

@Slf4j
@Component
public class RabbitConsumer {

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = "directQueue"),
                    exchange = @Exchange("directExchange"),
                    key = "directRouting")
    })
    public void receiveDirectMsg(Map map,Message message, Channel channel) {
        //接收消息message
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
//            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        } catch (IOException e) {
            log.info(e.toString());
        }
        log.info("Direct模式消费者收到消息: " +new String(message.getBody()) );
    }


    /**
    * @Description: 直接监听队列
    * @Param: [message]
    * @return: void
    * @Author: yzd
    * @Date: 2024/1/4-16:06
    */
    @RabbitListener(queues = "topicFirstQueue")
    public void receiveTopicFirstMsg(Message message){

        //接收消息message
        log.info("Topic模式(topicFirstQueue)消费者收到消息: " +new String(message.getBody()));
    }

    /**
     * 消费者监听，绑定队列
     */
    @RabbitListener(queues = "topicSecondQueue")
    public void receiveTopicSecondMsg(Message message) {
        //接收消息message
        log.info("Topic模式(topicSecondQueue)消费者收到消息: " + new String(message.getBody()));
    }


    /**
     * 消费者监听，绑定队列
     */
    @RabbitListener(queues = "fanoutFirstQueue")
    public void receiveFanoutFirstMsg(Message message) {
        //接收消息message
        log.info("Fanout模式(fanoutFirstQueue)消费者收到消息: " + new String(message.getBody()));
    }

    /**
     * 消费者监听，绑定队列
     */
    @RabbitListener(queues = "fanoutSecondQueue")
    public void receiveFanoutSecondMsg(Message message) {
        //接收消息message
        log.info("Fanout模式(fanoutSecondQueue)消费者收到消息: " + new String(message.getBody()));
    }
}
