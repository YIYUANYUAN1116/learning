package com.xht.security.custom;

import com.xht.security.entity.User;
import com.xht.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByName(username);
        if (user == null){
            throw new UsernameNotFoundException(username + " can not found !");
        }
        List<GrantedAuthority> admin = AuthorityUtils.createAuthorityList("admin");
        return new MyUserDetails(user.getUsername(), user.getPassword(), admin);
    }
}
