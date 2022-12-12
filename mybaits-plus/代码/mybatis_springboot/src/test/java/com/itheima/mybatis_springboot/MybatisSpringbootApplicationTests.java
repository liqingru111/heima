package com.itheima.mybatis_springboot;

import com.itheima.mybatis_springboot.domain.User;
import com.itheima.mybatis_springboot.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
class MybatisSpringbootApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testFindAll() {

        List<User> list = userMapper.findAll();
        System.out.println(list);



    }

}
