package com.xht.ai.config;

import com.xht.ai.handler.ChatWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> route(ChatWebSocketHandler chatWebSocketHandler) {
        return RouterFunctions.route()
                .GET("/ws/chat", (req) -> ServerResponse.ok().bodyValue("Connect to /ws/chat"))
                .build();
    }
}
