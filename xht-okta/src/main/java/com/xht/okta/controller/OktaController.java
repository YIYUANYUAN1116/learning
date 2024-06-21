package com.xht.okta.controller;

import com.okta.spring.boot.sdk.config.OktaClientProperties;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@Tag(name = "okta")
public class OktaController {

    private static  HashMap<String,String> map = new HashMap<>();


    @GetMapping("/")
    @Operation(summary = "greetUser")
    public String greetUser(@AuthenticationPrincipal OidcUser user){
        if (user!=null){
            return "Hello "+user.getName()+" from application 2";
        }else {
            return "Hello "+"null"+" from application 2";
        }
    }

    @GetMapping("/user-info")
    @ResponseBody
    Object code() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/authorization-code/callback")
    @ResponseBody
    Object callback(@RequestParam(value = "code") String code) {
        System.out.println(code);

        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/oauth2/login/success")
    public String handleLoginSuccess(Model model, HttpServletRequest request) throws Exception {
        log.info(SecurityContextHolder.getContext().getAuthentication().toString());
        DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        OidcUserInfo userInfo = principal.getUserInfo();
        Map<String, Object> claims = userInfo.getClaims();
        OidcIdToken idToken = principal.getIdToken();

        model.addAttribute("sub",claims.get("sub").toString());
        model.addAttribute("name",claims.get("name").toString().replace(" ",""));
        model.addAttribute("email",claims.get("email").toString());
        model.addAttribute("token",idToken.getTokenValue());
        map.put("sub",claims.get("sub").toString());
        map.put("token",idToken.getTokenValue());
        return "oauth";
    }

    @GetMapping("/login/oauth2/code/okta")
    @ResponseBody
    public String okta() throws Exception {
        log.info("/login/oauth2/code/okta");
        return "/login/oauth2/code/okta";
    }

    @GetMapping("/api/logout")
    @ResponseBody
    public String logout() throws Exception {
        try {
            SecurityContextHolder.clearContext();
            RestTemplate restTemplate = new RestTemplate();
            // 设置请求 URL
            String url = "https://dev-92748479.okta.com/api/v1/users/00uht6m9tcs5zHq195d7/sessions";

            // 创建 Headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
            headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            headers.set("Authorization","SSWS 00-73lwl5TgIXoxorkd_sjzVU5Knc68YOB3-zZ33HW");

            // 创建 HttpEntity 实例
            HttpEntity<?> entity = new HttpEntity<>(headers);

            // 发送 DELETE 请求
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
            // 处理响应
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Request successful");
                System.out.println("Response body: " + response.getBody());
            } else {
                System.out.println("Request failed with status: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
        return "ok";
    }


}
