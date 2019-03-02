package com.example.springboot_jwt_sso.sso;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author 刘海鹏
 * @iphone 13714872954
 * @date 2019/3/1下午10:14
 */
public class MyPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(charSequence.toString());
    }

}
