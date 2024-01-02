package com.xht.redis.msg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class SubMsgListener implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("收到消息："+ new String(message.getBody()));
    }
}
