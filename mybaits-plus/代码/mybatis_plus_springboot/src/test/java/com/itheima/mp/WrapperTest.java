package com.itheima.mp;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.mp.entity.User;
import com.itheima.mp.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WrapperTest {


    @Autowired
    private UserMapper userMapper;


    /**
     * 基础比较查询
     *
     * Wrapper:
     *  1.QueryWrapper
     *      LambdaQueryWrapper
     *  2.UpdateWrapper
     *      LambdaUpdateWrapper
     *
     */
    @Test
    public void testWrapper1(){
        //1.创建查询条件构建器
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //2.设置条件
        wrapper.eq("user_name","lisi")
                .lt("age",23)
                .in("name","李四","王五");
        /*
            select * from tb_user where user_name = ? and age < ? and name in (?,?)
         */

        List<User> users = userMapper.selectList(wrapper);

        System.out.println(users);
    }


    @Test
    public void testWrapper2(){
        //1.创建查询条件构建器
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //2.设置条件
        wrapper.eq("user_name","lisi")
                .or()
                .lt("age",23)
                .in("name","李四","王五");
        /*
            select * from tb_user where user_name = ? or age < ? and name in (?,?)
         */

        List<User> users = userMapper.selectList(wrapper);

        System.out.println(users);
    }


    /**
     * 模糊查询
     */
    @Test
    public void testWrapper3(){
        //1.创建查询条件构建器
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //2.设置条件
        wrapper.likeLeft("user_name","zhang");
        /*
            SELECT id,user_name,password,name,age,email
             from tb_user
             where user_name like ?

             %zhang
         */

        List<User> users = userMapper.selectList(wrapper);

        System.out.println(users);
    }

    /**
     * 排序查询
     */

    @Test
    public void testWrapper4(){
        //1.创建查询条件构建器
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //2.设置条件
        wrapper.eq("user_name","lisi")
                .or()
                .lt("age",23)
                .in("name","李四","王五")
                //.orderBy(true,true,"age")
                .orderByDesc("age");

        ;
        /*
            select * from tb_user where user_name = ? or age < ? and name in (?,?) order by age asc
         */

        List<User> users = userMapper.selectList(wrapper);

        System.out.println(users);
    }


    /**
     * select：指定需要查询的字段
     */

    @Test
    public void testWrapper5(){
        //1.创建查询条件构建器
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //2.设置条件
        wrapper.eq("user_name","lisi")
                .or()
                .lt("age",23)
                .in("name","李四","王五")
                //.orderBy(true,true,"age")
                .orderByDesc("age")
                .select("id","user_name");

        ;
        /*
            select id,user_name from tb_user where user_name = ? or age < ? and name in (?,?) order by age asc
         */

        List<User> users = userMapper.selectList(wrapper);

        System.out.println(users);
    }




    /**
     * 分页条件查询
     */

    @Test
    public void testWrapper6(){

        int current = 1;//当前页码
        int size = 2;//每页显示条数
        //1. 构建分页对象
        Page<User> page = new Page<>(current,size);
        //2. 构建条件对象
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.lt("age",23);


        userMapper.selectPage(page,wrapper);


        List<User> records = page.getRecords();
        long total = page.getTotal();
        long pages = page.getPages();

        System.out.println(records);
        System.out.println(total);//2
        System.out.println(pages);//1

    }

    /**
     * LambdaQueryWrapper：消除代码中的硬编码
     */

    @Test
    public void testWrapper7(){


        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(User::getUserName,"zhangsan");

        userMapper.selectOne(wrapper);
    }


    /**
     * 删除条件
     */

    @Test
    public void testWrapper8(){


        QueryWrapper<User> wrapper = new QueryWrapper<>();

        wrapper.eq("user_name","bbb");

        userMapper.delete(wrapper);
    }

    /**
     * 修改条件
     */

    @Test
    public void testWrapper9(){


        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        //条件
        wrapper.eq("user_name","lisi")
                .set("password","22222");

        //update tb_user set password = ? where user_name = ?
        userMapper.update(null,wrapper);
    }


    @Test
    public void testWrapper10(){


        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
    //条件
        wrapper.eq("user_name","lisi");


    //update tb_user set password = ?,age = ? where user_name = ?
        User user = new User();
        user.setPassword("3333");
        user.setAge(33);
        userMapper.update(user,wrapper);
}
}
