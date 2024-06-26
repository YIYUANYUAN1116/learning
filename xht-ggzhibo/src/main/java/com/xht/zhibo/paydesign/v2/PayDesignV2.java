package com.xht.zhibo.paydesign.v2;

import com.xht.zhibo.paydesign.constant.PayType;
import lombok.extern.slf4j.Slf4j;

/**
 * 支付方式v1
 * ：支付方式有微信，支付宝，工行，农行等
 */
@Slf4j
public class PayDesignV2 {
    public static void main(String[] args) {
        pay("支付宝");
    }

    public static void pay(String payType){
        if (payType.equalsIgnoreCase(PayType.WeiXin.getPayMethod())){
            log.info("微信支付流程");
        }else if (payType.equalsIgnoreCase(PayType.ZhiFuBao.getPayMethod())){
            log.info("支付宝支付流程");
        }
    }
}
