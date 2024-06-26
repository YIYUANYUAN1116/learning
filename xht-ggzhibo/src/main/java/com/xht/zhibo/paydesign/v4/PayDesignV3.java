package com.xht.zhibo.paydesign.v4;

import lombok.extern.slf4j.Slf4j;

/**
 * 支付方式v1
 * ：支付方式有微信，支付宝，工行，农行等
 */
@Slf4j
public class PayDesignV3 {
    public static void main(String[] args) {
        pay("支付宝");
    }

    public static void pay(String payType){
        PayFactory.getPayHandler(payType).doPay();
    }
}
