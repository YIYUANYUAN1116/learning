package com.xht.okta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(authorizeHttpRequests->{
            authorizeHttpRequests.requestMatchers("/oauth2/login").permitAll().anyRequest().authenticated();
        });

        httpSecurity.formLogin(Customizer.withDefaults());
        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.oauth2Login(oAuth2LoginConfigurer -> {
            oAuth2LoginConfigurer.defaultSuccessUrl("/oauth2/login/success",true);
            oAuth2LoginConfigurer.loginProcessingUrl("/oauth2/login");
        });

        return httpSecurity.build();
    }
}
