package com.xht.zhibo.paydesign.v4;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

@Slf4j
public  abstract class PayAbstractHandler implements InitializingBean {

    private   void payBefore(){
        log.info("payBefore");
    }

    private  void payAfter(){
        log.info("payAfter");
    }
    public abstract void doPay();

    public void doTemplatePay(){
        payBefore();
        doPay();
        payAfter();
    }
}
