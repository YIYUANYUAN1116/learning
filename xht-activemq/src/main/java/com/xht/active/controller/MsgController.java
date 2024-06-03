package com.xht.active.controller;

import com.xht.active.consumer.Consumer;
import com.xht.active.entity.User;
import com.xht.active.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MsgController {

    @Autowired
    private Producer producer;

    @Autowired
    private Consumer consumer;

    @GetMapping("/sendQueue")
    public String sendQueueMsg() {
        User user = new User();
        user.setId(1);
        user.setUsername("xht--Queue");
        user.setPassword("123");
        producer.sendQueueMessage(user.toString());
        return "发送成功!";
    }

    @GetMapping("/sendTopic")
    public String sendTopicMsg() {
        User user = new User();
        user.setId(1);
        user.setUsername("xht--Topic");
        user.setPassword("123456");
        producer.sendTopicMessage(user.toString());
        return "发送成功!";
    }

}
