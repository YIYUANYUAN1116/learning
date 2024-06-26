package com.xht.zhibo.paydesign.v3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ZFBPayHandler implements PayHandler, InitializingBean {
    @Override
    public void pay() {
        log.info("支付宝支付流程");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        PayFactory.registerHandler("支付宝",new ZFBPayHandler());
    }
}
