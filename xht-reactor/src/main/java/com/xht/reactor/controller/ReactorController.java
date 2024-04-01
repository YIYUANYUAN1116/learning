package com.xht.reactor.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@RestController
@Slf4j
public class ReactorController {


    @GetMapping("/mvc")
    public String getByMvc(){
        log.info("mvc start");
        String s = doSomeThing("mvc");
        log.info("mvc end");
        return s;
    }


    @GetMapping("/mono")
    public Mono<String> getByMono(){
        log.info("mono start");
        Mono<String> mono = Mono.fromSupplier(() -> doSomeThing("mono"));
        log.info("mono end");
        return mono;
    }

    @GetMapping(value = "/flux",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getByFlux(){
        log.info("flux start");
        Flux<String> stringFlux = Flux
                .fromStream(IntStream.range(1, 5).mapToObj(i -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "flux data--" + i;
                }));
        log.info("flux end");
        return stringFlux;
    }


    @GetMapping(value = "/stream-flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamFlux() {
        return Flux
                .interval(Duration.ofSeconds(1))
                .map(sequence -> "Flux - " + LocalTime.now().toString());
    }



    private String doSomeThing(String str){
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return "doSomeThing: "+str;
    }
}
