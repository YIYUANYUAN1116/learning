package com.xht.activiti.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class SecurityConfig {

    @Bean
    public UserDetailsService myUserDetailsService() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        //这里添加用户，后面处理流程时用到的任务负责人，需要添加在这里
        String[][] usersGroupsAndRoles = {
                {"小河豚", "password", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam"},
                {"我是ceo", "password", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam"},
                {"我是hr", "password", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam"},
                {"1", "password", "ROLE_ACTIVITI_USER", "GROUP_otherTeam"},
                {"2", "password", "ROLE_ACTIVITI_USER"},
                {"3", "password", "ROLE_ACTIVITI_ADMIN"},
                {"张三", "password", "ROLE_ACTIVITI_ADMIN"},
                {"李四", "password", "ROLE_ACTIVITI_ADMIN"},
                {"王五", "password", "ROLE_ACTIVITI_ADMIN"},
                {"王二麻", "password", "ROLE_ACTIVITI_ADMIN"},
        };

        for (String[] user : usersGroupsAndRoles) {
            List<String> authoritiesStrings = Arrays.asList(Arrays.copyOfRange(user, 2, user.length));
            log.info("> Registering new user: " + user[0] + " with the following Authorities[" + authoritiesStrings + "]");
            inMemoryUserDetailsManager.createUser(new User(user[0], passwordEncoder().encode(user[1]),
                    authoritiesStrings.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())));
        }

        return inMemoryUserDetailsManager;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
