package com.xht.zhibo.paydesign.v4;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ZFBPayHandlerV4 extends PayAbstractHandler {
    @Override
    public void doPay() {
        log.info("支付宝支付流程");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        PayFactory.registerHandler("支付宝",new ZFBPayHandlerV4());
    }
}
