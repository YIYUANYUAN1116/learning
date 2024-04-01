package com.xht.ai.controller;


import com.xht.ai.entity.ChatMessageEntity;
import com.xht.common.vo.Result;
import com.xht.common.vo.ResultCodeEnum;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.IOException;

@RequestMapping("/ollama/chat")
@RestController
@Slf4j
public class OllamaChatController {

    @Autowired
    private OllamaChatClient chatClient;

    @PostMapping("/input")
    public Result chat(@RequestBody ChatMessageEntity chatMessageEntity){
        log.info("chat--start");
        String call = chatClient.call(chatMessageEntity.getMessage());
        log.info("chat--end");
        log.info(call);
        return Result.build(call, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/stream/input")
    public Flux<ChatResponse> streamChat(HttpServletResponse response, @RequestBody ChatMessageEntity chatMessageEntity){
        log.info("streamChat--start");
        Prompt prompt = new Prompt(new UserMessage(chatMessageEntity.getMessage()));
        log.info("streamChat--end");
        return chatClient.stream(prompt);

    }

}
