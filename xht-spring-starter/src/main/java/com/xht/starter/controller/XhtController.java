package com.xht.starter.controller;

import com.xht.starter.properties.XhtProperties;
import com.xht.starter.service.XhtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : YIYUANYUAN
 * @date: 2024/1/30  16:15
 */
@RestController
public class XhtController {

    @Autowired
    XhtService xhtService;

    @GetMapping("/starter")
    public String testController(){
        return xhtService.xhtStart();
    }
}
