package com.xht.activiti.demo;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-19 21:42
 **/
public class Demo1 {
    public static void main(String[] args) {

        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                1,
                10,
                30,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(),
                new ThreadPoolExecutor.CallerRunsPolicy());

        CountDownLatch countDownLatch = new CountDownLatch(10);

        countDownLatch.countDown();

    }
}
