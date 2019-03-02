package com.example.springboot_jwt_sso.sso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @author 刘海鹏
 * @iphone 13714872954
 * @date 2019/3/1下午10:01
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username=authentication.getName();
        String password=(String) authentication.getCredentials();
        UserDetails user=userDetailsService.loadUserByUsername(username);
        if (user.getPassword().equals(password)){
            return new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
