package com.xht.zhibo.paydesign.v4;


import java.util.HashMap;
import java.util.Map;

public class PayFactory {
    private static Map<String, PayAbstractHandler> map = new HashMap<>();

    public static PayAbstractHandler getPayHandler(String payType){
        return map.get(payType);
    }

    public static void registerHandler(String payType, PayAbstractHandler payAbstractHandler){
        map.put(payType, payAbstractHandler);
    }

}
