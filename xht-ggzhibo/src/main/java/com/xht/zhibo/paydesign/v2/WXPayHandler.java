package com.xht.zhibo.paydesign.v2;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WXPayHandler implements PayHandler{
    @Override
    public void pay() {
        log.info("微信支付流程");
    }
}
