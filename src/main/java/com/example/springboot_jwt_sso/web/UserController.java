package com.example.springboot_jwt_sso.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 刘海鹏
 * @iphone 13714872954
 * @date 2019/3/1下午10:16
 */
@RestController
public class UserController {
    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @RequestMapping("/test")
    public String test() {
        return "test";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @RequestMapping("/test2")
    public String test2() {
        return "test2";
    }

}
