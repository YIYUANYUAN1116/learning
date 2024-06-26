package com.xht.zhibo.paydesign.constant;
import lombok.Getter;
@Getter
public enum PayType {

    WeiXin("微信"),
    ZhiFuBao("支付宝");
    private final String payMethod;

    PayType(String type){
        this.payMethod = type;
    }
}
