package com.xht.okta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2ClientConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(authorizeHttpRequests->{
            authorizeHttpRequests.requestMatchers("/oauth2/login","/","/index1","/static/**","/user-info","/oauth.html", "templates/oauth.html").permitAll().anyRequest().authenticated();
        });

        httpSecurity.formLogin(Customizer.withDefaults());
        httpSecurity.logout(logoutConfigurer -> {
            logoutConfigurer.logoutUrl("/logout");
            logoutConfigurer.deleteCookies("JSESSIONID");
            logoutConfigurer.invalidateHttpSession(true);
            logoutConfigurer.clearAuthentication(true);
        });
        httpSecurity.oidcLogout();
        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.oauth2Login(oAuth2LoginConfigurer -> {
            oAuth2LoginConfigurer.defaultSuccessUrl("/oauth2/login/success",true);
            oAuth2LoginConfigurer.loginProcessingUrl("/oauth2/login");

        });
        httpSecurity.oauth2Client(Customizer.withDefaults());
        httpSecurity.oauth2ResourceServer(httpSecurityOAuth2ResourceServerConfigurer -> {
            httpSecurityOAuth2ResourceServerConfigurer.jwt(Customizer.withDefaults());
        });
        return httpSecurity.build();
    }
}
