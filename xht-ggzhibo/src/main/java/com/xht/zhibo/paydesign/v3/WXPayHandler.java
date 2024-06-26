package com.xht.zhibo.paydesign.v3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WXPayHandler implements PayHandler, InitializingBean {
    @Override
    public void pay() {
        log.info("微信支付流程");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        PayFactory.registerHandler("微信",new WXPayHandler());
    }
}
