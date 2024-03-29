package com.xht.reactor.function;

import java.util.function.Consumer;

public class FunctionMain {
    public static void main(String[] args) {

        XhtFunction xhtFunction = System.out::println;
        xhtFunction.printfMsg("test");

        Consumer<Integer> consumer = a-> System.out.println(a+a);
        consumer.accept(2);
    }
}
