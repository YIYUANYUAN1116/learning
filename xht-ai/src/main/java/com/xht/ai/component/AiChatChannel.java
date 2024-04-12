package com.xht.ai.component;


import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;

@ServerEndpoint(value = "/channel/ai/chat")
@Slf4j
@Component
public class AiChatChannel implements ApplicationContextAware {

    private Session session;

    // 运行时的 WebSocket 连接对象，也就是端点实例，是由服务器创建，而不是 Spring，所以不能使用自动装配
    // 全局静态变量，保存 ApplicationContext
    private static ApplicationContext applicationContext;

    // 声明需要的 Bean
    private OllamaChatClient chatClient;

    // 收到消息
    @OnMessage
    public void onMessage(String message) throws IOException {

        log.info("[websocket] 收到消息：id={}，message={}", this.session.getId(), message);
        if (message.equalsIgnoreCase("bye")) {
            // 由服务器主动关闭连接。状态码为 NORMAL_CLOSURE（正常关闭）。
            this.session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Bye"));;
            return;
        }

//        chatClient.stream(message).subscribe(content -> {
//            this.session.getAsyncRemote().sendText(content);
//        });
        String content = chatClient.call(message);
        this.session.getAsyncRemote().sendText(content);
//        PromptTemplate promptTemplate = new PromptTemplate(message);
//
//        Prompt prompt = promptTemplate.create();
//        chatClient.stream(prompt).subscribe(chatResponse -> {
//            String content = chatResponse.getResult().getOutput().getContent();
//            this.session.getAsyncRemote().sendText(content);
//        },throwable -> {
//            this.session.getAsyncRemote().sendText("err:   "+throwable.getMessage());
//        },()->{
//            this.session.getAsyncRemote().sendText("complete~!");
//        });


    }

    // 连接打开
    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig){
        // 保存 session 到对象
        this.session = session;
        // 连接创建的时候，从 ApplicationContext 获取到 Bean 进行初始化
        this.chatClient = applicationContext.getBean(OllamaChatClient.class);

        log.info("[websocket] 新的连接：id={}", this.session.getId());

    }

    // 连接关闭
    @OnClose
    public void onClose(CloseReason closeReason){
        log.info("[websocket] 连接断开：id={}，reason={}", this.session.getId(),closeReason);
    }

    // 连接异常
    @OnError
    public void onError(Throwable throwable) throws IOException {

        log.info("[websocket] 连接异常：id={}，throwable={}", this.session.getId(), throwable.getMessage());

        // 关闭连接。状态码为 UNEXPECTED_CONDITION（意料之外的异常）
        this.session.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, throwable.getMessage()));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AiChatChannel.applicationContext = applicationContext;
    }

}
