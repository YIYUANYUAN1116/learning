package com.xht.okta.controller;

import com.mysql.cj.log.Log;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@Tag(name = "okta")
public class OktaController {

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
    Object callback(@RequestParam String nonce) {
        System.out.println(nonce);
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/oauth2/login/success")
    @ResponseBody
    public Object handleLoginSuccess(@AuthenticationPrincipal OidcUser oidcUser, HttpServletResponse response) throws Exception {
        if (oidcUser!=null){
            return oidcUser;
        }else {
            return "Hello "+"null"+" from application 2";
        }
    }


}
