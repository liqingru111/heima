package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.entity.User;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.service._UserService;
import org.springframework.stereotype.Service;


@Service
public class _UserServiceImpl extends ServiceImpl<UserMapper, User> implements _UserService {
}
