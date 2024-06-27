package com.xht.pay.entity;

import lombok.Data;

@Data
public class AliPay {
    private String outTraceNo;
    private double totalAmount;
    private String subject;
    private String productCode;
    private String tradeNo;
}
