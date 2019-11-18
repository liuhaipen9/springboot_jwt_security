package com.example.springboot_jwt_sso.sso_filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 刘海鹏
 * @iphone 13714872954
 * @date 2019/3/1下午9:27
 */
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        /*String header=request.getHeader("Authorization");
        if (header==null||!header.startsWith("Bearer ")){
            chain.doFilter(request,response);
            return;
        }*/
        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }


    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token!=null){
            Claims claims= Jwts.parser().setSigningKey("MyJwtSecretll")
                    .parseClaimsJws(token.replace("Bearer ","")).getBody();
            String user=claims.getSubject();
            List<String> roles=claims.get("role",List.class);
            //为什么chaims里面没有role对应的值
//            List<SimpleGrantedAuthority> auth=roles.stream().map(s->new
//                    SimpleGrantedAuthority(s)).collect(Collectors.toList());
            //拿到username，可以根据username去数据库里面把role查询出来
            List<SimpleGrantedAuthority> auth2=Arrays.asList(new
                    SimpleGrantedAuthority("ROLE_USER"));
            if (user!=null){
                return new UsernamePasswordAuthenticationToken(user,null,auth2);
            }
            return null;
        }
//        String user = "roc-liu";
//        List<SimpleGrantedAuthority> auth = Arrays.asList(new
//                SimpleGrantedAuthority("ROLE_USER"));
//        return new UsernamePasswordAuthenticationToken(user, null, auth);
        return null;
    }


    @Override
    public void setBeanName(String beanName) {
        super.setBeanName(beanName);
    }

    @Override
    public void setEnvironment(Environment environment) {
        super.setEnvironment(environment);
    }

    @Override
    public Environment getEnvironment() {
        return super.getEnvironment();
    }

    @Override
    protected Environment createEnvironment() {
        return super.createEnvironment();
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        super.setServletContext(servletContext);
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    protected void initBeanWrapper(BeanWrapper bw) throws BeansException {
        super.initBeanWrapper(bw);
    }

    @Override
    protected void initFilterBean() throws ServletException {
        super.initFilterBean();
    }

    @Override
    public FilterConfig getFilterConfig() {
        return super.getFilterConfig();
    }

    @Override
    protected String getFilterName() {
        return super.getFilterName();
    }

    @Override
    protected ServletContext getServletContext() {
        return super.getServletContext();
    }

    @Override
    protected boolean isAsyncDispatch(HttpServletRequest request) {
        return super.isAsyncDispatch(request);
    }

    @Override
    protected boolean isAsyncStarted(HttpServletRequest request) {
        return super.isAsyncStarted(request);
    }

    @Override
    protected String getAlreadyFilteredAttributeName() {
        return super.getAlreadyFilteredAttributeName();
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return super.shouldNotFilter(request);
    }

    @Override
    protected boolean shouldNotFilterAsyncDispatch() {
        return super.shouldNotFilterAsyncDispatch();
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return super.shouldNotFilterErrorDispatch();
    }


    @Override
    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException {
        super.onSuccessfulAuthentication(request, response, authResult);
    }

    @Override
    protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        super.onUnsuccessfulAuthentication(request, response, failed);
    }

    @Override
    protected AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return super.getAuthenticationEntryPoint();
    }

    @Override
    protected AuthenticationManager getAuthenticationManager() {
        return super.getAuthenticationManager();
    }

    @Override
    protected boolean isIgnoreFailure() {
        return super.isIgnoreFailure();
    }

    @Override
    public void setAuthenticationDetailsSource(AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
        super.setAuthenticationDetailsSource(authenticationDetailsSource);
    }

    @Override
    public void setRememberMeServices(RememberMeServices rememberMeServices) {
        super.setRememberMeServices(rememberMeServices);
    }

    @Override
    public void setCredentialsCharset(String credentialsCharset) {
        super.setCredentialsCharset(credentialsCharset);
    }

    @Override
    protected String getCredentialsCharset(HttpServletRequest httpRequest) {
        return super.getCredentialsCharset(httpRequest);
    }

}
