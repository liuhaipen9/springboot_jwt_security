package com.example.springboot_jwt_sso.sso;

import com.example.springboot_jwt_sso.sso_filter.JWTAuthenticationFilter;
import com.example.springboot_jwt_sso.sso_filter.JWTLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author 刘海鹏
 * @iphone 13714872954
 * @date 2019/3/1下午9:46
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
   @Autowired
   private MyAuthenticationProvider myAuthenticationProvider;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(myAuthenticationProvider);
        auth.userDetailsService(userDetailsService());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.authorizeRequests().anyRequest().authenticated()
               .and()
               .formLogin().loginProcessingUrl("/login")
               .and()
               .csrf().disable()
               .addFilter(new JWTLoginFilter(authenticationManager()))
               .addFilter(new JWTAuthenticationFilter(authenticationManager()));
        super.configure(http);
    }
    @Bean
    public PasswordEncoder mypasswordEncoder(){
        return new MyPasswordEncoder();
    }
    @Bean
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager iud=new InMemoryUserDetailsManager();
        Collection<GrantedAuthority> admintAuth=new ArrayList<>();
        ((ArrayList<GrantedAuthority>) admintAuth).add(new SimpleGrantedAuthority(("ROLE_ADMIN")));
        iud.createUser(new User("zhangsan","123456",admintAuth));
        return iud;
    }
}
