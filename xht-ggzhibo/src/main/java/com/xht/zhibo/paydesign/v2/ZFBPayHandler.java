package com.xht.zhibo.paydesign.v2;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZFBPayHandler implements PayHandler{
    @Override
    public void pay() {
        log.info("支付宝支付流程");
    }
}
