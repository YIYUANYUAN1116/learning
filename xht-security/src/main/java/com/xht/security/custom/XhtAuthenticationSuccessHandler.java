package com.xht.security.custom;

import com.alibaba.fastjson.JSON;
import com.xht.common.utils.ResponseUtils;
import com.xht.common.vo.Result;
import com.xht.common.vo.ResultCodeEnum;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


import java.io.IOException;

public class XhtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ResponseUtils.buildResponse(response, Result.build(null, ResultCodeEnum.FORBIDDEN), HttpStatus.FORBIDDEN);
    }
}
