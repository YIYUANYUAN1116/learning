package com.xht.starter.service.impl;

import com.xht.starter.properties.XhtProperties;
import com.xht.starter.service.XhtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : YIYUANYUAN
 * @date: 2024/1/30  16:23
 */

@Service
public class XhtServiceImpl implements XhtService {

    @Autowired
    XhtProperties xhtProperties;

    @Override
    public String xhtStart() {
        return "hello: "+xhtProperties.getName() +" " +xhtProperties.getPasswd();
    }
}
