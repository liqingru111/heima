package com.itheima.mybatis_springboot.mapper;

import com.itheima.mybatis_springboot.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface UserMapper {

    public List<User> findAll();

}
