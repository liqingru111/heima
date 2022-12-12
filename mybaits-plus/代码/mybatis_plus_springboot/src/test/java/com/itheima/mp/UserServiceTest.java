package com.itheima.mp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itheima.mp.entity.User;
import com.itheima.mp.service._UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {


    @Autowired
    private _UserService userService;

    @Test
    public void test(){
       /* User user = userService.findOne(2L);
        System.out.println(user);*/


        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name","lisi");
        User user = userService.getOne(wrapper);
        System.out.println(user);
    }


}
