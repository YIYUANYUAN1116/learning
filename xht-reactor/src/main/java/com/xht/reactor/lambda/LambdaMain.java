package com.xht.reactor.lambda;

public class LambdaMain {
    public static void main(String[] args) {
        MyInterface myInterface1 = (x,y) ->{return x+y;};
        System.out.println(myInterface1.sum(1,2));

        MyInterface myInterface2 = Integer::sum;
        System.out.println(myInterface2.sum(1,2));
    }
}
