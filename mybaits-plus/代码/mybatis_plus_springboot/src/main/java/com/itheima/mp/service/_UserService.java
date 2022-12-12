package com.itheima.mp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.mp.entity.User;

/**
 * Service封装
 * 1. 定义接口继承IService
 * 2. 定义实现类继承ServiceImpl<Mapper，Entity> 实现定义的接口
 */
public interface _UserService extends IService<User> {
}
