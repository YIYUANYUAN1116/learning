package com.xht.ai.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import org.springframework.web.reactive.socket.server.RequestUpgradeStrategy;
import org.springframework.web.reactive.socket.server.support.HandshakeWebSocketService;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.upgrade.ReactorNettyRequestUpgradeStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

@Configuration
public class WebSocketConfig {

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter(webSocketService());
    }

    @Bean
    public HandshakeWebSocketService webSocketService() {
        return new HandshakeWebSocketService(new ReactorNettyRequestUpgradeStrategy());
    }

    // 确保你有一个WebSocketHandler来处理WebSocket消息
    @Autowired
    private WebSocketHandler webSocketHandler;

    // ... 省略其他配置
}
