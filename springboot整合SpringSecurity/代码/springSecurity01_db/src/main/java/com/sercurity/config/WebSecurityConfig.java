package com.sercurity.config;

import com.sercurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/*
security配置类
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    UserService userDetailsService;

    //解决rem
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        // 在这里将 UserDetailsSercie 实例注入
        try {
            auth.userDetailsService(userDetailsService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //不需要认证，直接放行的资源
                .antMatchers("/", "/home","/toLogin","/**/customer/**").permitAll()
                .antMatchers("/js/**", "/css/**", "/images/**", "/fronts/**", "/doc/**", "/toLogin").permitAll()
                //访问/user请求的资源，必须是USER角色
                .antMatchers("/user/**").hasAnyRole("USER")
                .antMatchers("/**").access("hasRole('ADMIN')")
                .anyRequest().authenticated()
                //自定义登录界面
                .and().formLogin().loginPage("/toLogin").loginProcessingUrl("/login").failureUrl("/toLogin?error").permitAll()
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .and().exceptionHandling().accessDeniedPage("/toLogin?deny")
                .and().httpBasic()
                //开启rememberme功能
                .and().rememberMe().tokenValiditySeconds(2419200).key("spittrKey")
                .and().sessionManagement().invalidSessionUrl("/toLogin")
                .and().csrf().disable();
    }
}
