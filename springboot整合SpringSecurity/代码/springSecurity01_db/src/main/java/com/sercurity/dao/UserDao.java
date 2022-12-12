package com.sercurity.dao;

import com.sercurity.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserInfo,Long>{
    //根据用户名查询实体
    public UserInfo findByUserName(String username);
}
