package com.xht.zhibo.paydesign.v3;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pay")
@Tag(name = "支付接口")
public class PayController {

    @GetMapping
    @Operation(summary = "支付")
    public void pay(@RequestParam String pay){
        PayFactory.getPayHandler(pay).pay();
    }
}
