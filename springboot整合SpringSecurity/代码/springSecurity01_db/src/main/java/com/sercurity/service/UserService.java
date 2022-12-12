package com.sercurity.service;

import com.sercurity.dao.UserDao;
import com.sercurity.domain.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sun.security.util.Password;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//业务
@Service("userDetailsService")
public class UserService implements UserDetailsService {

    @Autowired
    private UserDao dao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.调用接口
        UserInfo userInfo = dao.findByUserName(username);
        if(userInfo==null){
            throw new UsernameNotFoundException("用户不存在");
        }

        String password=userInfo.getPassword();
        username=userInfo.getUserName();
        //获取用户中的权限
        List<SimpleGrantedAuthority> authorities=new ArrayList<>();
        if(StringUtils.isNotEmpty(userInfo.getRoles())){
            String[] roles = userInfo.getRoles().split(",");
            for (String role : roles) {
                if(StringUtils.isNotEmpty(role)){
                    authorities.add(new SimpleGrantedAuthority(role));
                }
            }
        }
        return new User(username, password,authorities);
    }
}
