package com.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/*
权限控制配置类
 */
@Configuration
@EnableWebSecurity//可以不需要加
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    /*
        重写configure方法，参数为AuthenticationManagerBuilder类型的
        目的是配置用户和角色
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()//认证到内存中
                .withUser("lisi")//设置用户名
                .password("$2a$10$j1ZIQnzTX5z/Aw8q6cuRLOGGjn2y/I3EgHLLso4HMcgfYQ7lMOISG")//设置密码
                .roles("admin","user")//设置该用户拥有的角色
                .and()//连接关系符，和的关系
                .withUser("erzi")
                .password("$2a$10$j1ZIQnzTX5z/Aw8q6cuRLOGGjn2y/I3EgHLLso4HMcgfYQ7lMOISG")
                .roles("user");
    }
    //进行配置注入密码加密类
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    //角色-资源 访问控制
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()//http认证请求
                .antMatchers("/product/**").hasAnyRole("admin")//为当前请求路径赋予权限
                .antMatchers("/hello/**").hasRole("user")
                .anyRequest().authenticated()//所有请求都要经过认证
                .and()
                .formLogin()//而且支持基于from表单的登录，默认页面
                .loginPage("/login")//跳转登录页面的控制器，该地址要保证和表单提交的地址一致！
                .permitAll()
                .and()
                .httpBasic()//HTTPBasic方式的认证
                .and()
                .csrf().disable(); //暂时禁用CSRF，否则无法提交表单
    }
}
