package com.itheima.mp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.mp.entity.User;
import com.itheima.mp.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;


    /**
     * 根据id查询
     */
    @Test
    public void testSelectById() {

        User user = userMapper.selectById(1L);
        System.out.println(user);


    }



    /**
     * 添加
     */
    @Test
    public void testInsert() {

        User user = new User();

        //user.setId(6L);
        user.setUserName("itcast");
        user.setPassword("itheima");

        int count = userMapper.insert(user);
        System.out.println(count);


    }
    /**
     * 删除
     */
    @Test
    public void testDelete() {

/*

        //1. 根据id删除
        int count = userMapper.deleteById(8L);

*/
/*
        //2. 根据id集合批量删除
        List ids = new ArrayList();
        ids.add(6);
        ids.add(7);
        userMapper.deleteBatchIds(ids);
*/
        //3. 根据map构造条件，删除

        Map<String, Object> map = new HashMap<>();

        //delete from tb_user where user_name = ? and age = ?
        map.put("user_name","zhangsan");
        map.put("age","18");
        userMapper.deleteByMap(map);




    }


    /**
     * 修改
     */
    @Test
    public void testUpdateById() {

        User user = new User();
        user.setId(2L);
        user.setPassword("1111111");

        int count = userMapper.updateById(user);
    }


    /**
     * 分页查询：
     *  1. 当前页码：currentPage
     *  2. 每页显示条数：size
     *
     *  注意：使用mp的分页要设置一个拦截器！！！
            */
    @Test
    public void testSelectPage() {
        int current = 1;//当前页码
        int size = 2;//每页显示条数
        IPage<User> page = new Page(current,size);
        userMapper.selectPage(page,null);


        List<User> records = page.getRecords();//当前页的数据
        long pages = page.getPages();//总页数 2
        long total = page.getTotal();//总记录数 4

        System.out.println(records);
        System.out.println(pages);
        System.out.println(total);
    }






}
