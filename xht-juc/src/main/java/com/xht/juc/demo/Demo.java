package com.xht.juc.demo;

import com.xht.juc.entity.User;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;

/**
 * @Program: learning
 * @Description:
 * @Author: YIYUANYUAN
 * @Create: 2025-03-18 20:52
 **/
public class Demo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(2);
        atomicInteger.compareAndSet(2,4);
        System.out.println(atomicInteger.get());

        User user = new User();
        user.setAge(12);
        user.setName("lisi");
        AtomicReference<User> atomicReference = new AtomicReference<>();
        atomicReference.set(user);

        atomicReference.compareAndSet(user,new User(13,"lisi"));
        System.out.println(atomicReference.get().toString());

        LongAdder longAdder = new LongAdder();
        longAdder.increment();

        ThreadLocal<User> userThreadLocal = new ThreadLocal<>();
        userThreadLocal.set(user);
        User user1 = userThreadLocal.get();

    }
}
