package com.xht.kettle.listener;

import com.xht.kettle.service.KettleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyApplicationListener implements ApplicationRunner {

    @Autowired
    private KettleService kettleService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("程序启动");
        long l = System.currentTimeMillis();
//        kettleService.runTaskKtr("D:\\Development\\Tool\\Kettle\\file\\javascript_demo.ktr",null);
        long l2 = System.currentTimeMillis();
        log.info("run结束"+(l2-l));
    }
}
