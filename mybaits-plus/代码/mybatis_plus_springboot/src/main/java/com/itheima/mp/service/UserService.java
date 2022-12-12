package com.itheima.mp.service;

import com.itheima.mp.entity.User;

import java.io.Serializable;

public interface UserService {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public User findOne(Serializable id);
}
