package com.example.springboot_jwt_sso.sso_filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author 刘海鹏
 * @iphone 13714872954
 * @date 2019/3/1下午9:00
 */
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Claims claims = Jwts.claims();
        claims.put("role", authResult.getAuthorities().stream().map(s -> ((GrantedAuthority) s).getAuthority()).collect(Collectors.toList()));
        String token = Jwts.builder().setSubject(authResult.getName())
                .setExpiration(new Date(System.currentTimeMillis() + 60*1000))
                .signWith(SignatureAlgorithm.HS512, "MyJwtSecretll").compact();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset-utf-8");
        String str = "{\"token\":\"" + token + "\"}";
        PrintWriter out;
        try {
            out = response.getWriter();
            out.print(str);
            /*out.write("<!DOCTYPE html>");
            out.write("<html lang='en'>");
            out.write("<head>");
            out.write(" <meta charset='UTF-8'>");
            out.write("<title>登录页面</title>");*/
            out.write("<script language='javascript' type='text/javascript'>");
            out.write("window.location.href='index';");
            out.write("</script>");
            /*out.write("</head>");
            out.write("<body>");
            out.write("这是个登录页面<br/><br/><br/>");
            out.write("<a href='http://localhost:8089/index'>跳转到首页</a>");
            out.write("</body>");
            out.write("</html>");*/
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
