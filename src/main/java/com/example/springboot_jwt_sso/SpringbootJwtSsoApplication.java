package com.example.springboot_jwt_sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.springboot_jwt_sso.*"})
public class SpringbootJwtSsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootJwtSsoApplication.class, args);
    }

}
