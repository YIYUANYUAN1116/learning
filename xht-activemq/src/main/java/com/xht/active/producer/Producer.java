package com.xht.active.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.Topic;

@Slf4j
@Component
public class Producer {
    @Resource(name = "springboot.queue")
    private Queue queue;

    @Resource(name = "springboot.topic")
    private Topic topic;

//    @Resource(name = "springboot.replyQueue")
//    private Queue replyQueue;

    @Autowired
    private JmsMessagingTemplate jmsTemplate;

    public void sendMessage(Destination destination,String message){
        jmsTemplate.convertAndSend(destination,message);
    }

    /**
     * 发送队列消息
     */
    public void sendQueueMessage(final String message) {
        sendMessage(queue, message);
    }

    /**
     * 发送Topic消息
     */
    public void sendTopicMessage(final String message) {
        sendMessage(topic, message);
    }

}
