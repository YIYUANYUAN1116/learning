package com.xht.kettle.utils;


import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseUtils {

    public static void buildResponse(HttpServletResponse response, Object result, HttpStatus httpStatus) throws IOException {
        response.setContentType("application/json;charset=utf-8"); // 返回JSON
        response.setStatus(httpStatus.value());  // 状态码
        response.getWriter().write(JSON.toJSONString(result));
    }
}
