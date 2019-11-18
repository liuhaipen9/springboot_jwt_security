package com.example.springboot_jwt_sso.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author 刘海鹏
 * @iphone 13714872954
 * @date 2019/3/3下午9:48
 */
@Controller
public class PageController {

    @RequestMapping(value = "/ssoLogin", method = RequestMethod.GET)
    public String login() {
        return "sso/ssoLogin";
    }

    @RequestMapping("/index")
    public String index() {
        return "sso/index";
    }

}
