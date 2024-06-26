package com.xht.zhibo.paydesign.v3;


import java.util.HashMap;
import java.util.Map;

public class PayFactory {
    private static Map<String, PayHandler> map = new HashMap<>();

    public static PayHandler getPayHandler(String payType){
        return map.get(payType);
    }

    public static void registerHandler(String payType,PayHandler payHandler){
        map.put(payType,payHandler);
    }

}
