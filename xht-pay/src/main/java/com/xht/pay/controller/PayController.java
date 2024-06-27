package com.xht.pay.controller;


import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayAcquireRefundRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.easysdk.factory.Factory;
import com.xht.pay.entity.AliPay;
import com.xht.pay.properties.AliPayProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/pay")
@Slf4j
public class PayController {

    @Autowired
    private AliPayProperties aliPayProperties;

    private static final String FORMAT ="JSON";
    private static final String CHARSET ="utf-8";
    private static final String SIGN_TYPE ="RSA2";

    private static AliPay aliPay = new AliPay();
    @GetMapping("/alipay")
    public void pay(HttpServletResponse httpResponse){
        try {
            aliPay.setSubject("兰博基尼");
            aliPay.setOutTraceNo(UUID.randomUUID().toString().substring(0,16));
            aliPay.setTotalAmount(8000);
            aliPay.setProductCode("FAST_INSTANT_TRADE_PAY");
            AlipayClient alipayClient = new DefaultAlipayClient(aliPayProperties.getGatewayUrl(), aliPayProperties.getAppId(), aliPayProperties.getAppPrivateKey(),
                    FORMAT, CHARSET, aliPayProperties.getAlipayPublicKey(), SIGN_TYPE);

            AlipayTradePagePayRequest payRequest = new AlipayTradePagePayRequest();
            payRequest.setNotifyUrl(aliPayProperties.getNotifyUrl());

            payRequest.setBizContent("{\"out_trade_no\":\"" + aliPay.getOutTraceNo() + "\","
                    + "\"total_amount\":\"" + aliPay.getTotalAmount() + "\","
                    + "\"subject\":\"" + aliPay.getSubject() + "\","
                    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

//            payRequest.setBizContent(JSON.toJSONString(aliPay));
            String form = alipayClient.pageExecute(payRequest).getBody();

            httpResponse.setContentType("text/html;charset=" + CHARSET);
            // 直接将完整的表单html输出到页面
            httpResponse.getWriter().write(form);
            httpResponse.getWriter().flush();
            httpResponse.getWriter().close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @PostMapping("/alipay/notify")
    public String alipayNotify(HttpServletRequest request){
        try {
            if (request.getParameter("trade_status").equals("TRADE_SUCCESS")){
                Map<String, String> params = new HashMap<>();
                Map<String, String[]> requestParams = request.getParameterMap();
                for (String name : requestParams.keySet()) {
                    params.put(name, request.getParameter(name));
                }
    
                String tradeNo = params.get("out_trade_no");
                String gmtPayment = params.get("gmt_payment");
                String alipayTradeNo = params.get("trade_no");
                // 支付宝验签
                if (Factory.Payment.Common().verifyNotify(params)) {
                    // 验签通过
                    log.info("交易名称: " + params.get("subject"));
                    log.info("交易状态: " + params.get("trade_status"));
                    log.info("支付宝交易凭证号: " + params.get("trade_no"));
                    aliPay.setTradeNo(params.get("trade_no"));
                    log.info("商户订单号: " + params.get("out_trade_no"));
                    log.info("交易金额: " + params.get("total_amount"));
                    log.info("买家在支付宝唯一id: " + params.get("buyer_id"));
                    log.info("买家付款时间: " + params.get("gmt_payment"));
                    log.info("买家付款金额: " + params.get("buyer_pay_amount"));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return "success";
    }

    @RequestMapping("/alipay/refund")
    public void toRefund(HttpServletResponse response, HttpSession session) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(aliPayProperties.getGatewayUrl(), aliPayProperties.getAppId(), aliPayProperties.getAppPrivateKey(),
                FORMAT, CHARSET, aliPayProperties.getAlipayPublicKey(), SIGN_TYPE);


        AlipayTradeRefundRequest refundRequest = new AlipayTradeRefundRequest();

        refundRequest.setBizContent("{\"out_trade_no\":\""+ aliPay.getOutTraceNo() +"\","
                + "\"trade_no\":\""+ aliPay.getTradeNo() +"\","
                + "\"refund_amount\":\""+ aliPay.getTotalAmount() +"\","
                + "\"refund_reason\":\""+ "退款" +"\","
                + "\"out_request_no\":\""+ "111111111111" +"\"}");
        String body = alipayClient.execute(refundRequest).getBody();
        log.info(body);
    }

}
