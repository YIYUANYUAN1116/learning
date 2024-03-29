package com.xht.ai;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SpringBootTest
class XhtAiApplicationTests {

    @Autowired
    private OllamaChatClient ollamaChatClient;

    @Test
    void contextLoads() {
        String message = "写一个java 版的 hello world";
        System.out.println( ollamaChatClient.call(message));
    }

    @Test
    void streamChat() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = new CompletableFuture<>();

        String msg = "年终总结";

        PromptTemplate promptTemplate = new PromptTemplate("你是一个Java开发工程师，你擅长于写公司年底的工作总结报告，\n" +
                "根据：{message}场景写100字的总结报告");

        Prompt prompt = promptTemplate.create(Map.of("message", msg));

        ollamaChatClient.stream(prompt).subscribe(chatResponse -> {
            System.out.println(chatResponse.getResult().getOutput().getContent());
        },throwable -> {
            System.out.println("err:   "+throwable.getMessage());
        },()->{
            System.out.println("complete~!");
            // 关闭函数
            future.complete(null);
        });

        future.get();
    }

}
