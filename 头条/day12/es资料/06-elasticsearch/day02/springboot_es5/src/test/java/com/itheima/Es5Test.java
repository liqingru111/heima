package com.itheima;

import com.itheima.dao.ElasticSearchDao;
import com.itheima.pojo.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Es5Test {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ElasticSearchDao elasticSearchDao;
    //创建索引库
    @Test
    public void createIndex(){
        //创建索引
        elasticsearchTemplate.createIndex(UserInfo.class);
        //创建映射
        elasticsearchTemplate.putMapping(UserInfo.class);
    }

    //添加文档
    @Test
    public void addDoc(){
        UserInfo userInfo=new UserInfo();
        userInfo.setId(1L);
        userInfo.setTitle("我是程序员1121212");
        userInfo.setBrand("小米");
        userInfo.setCategory("手机");
        userInfo.setPrice(12d);
        elasticSearchDao.save(userInfo);
    }

    @Test
    public void plDoc(){
        UserInfo userInfo=new UserInfo();
        userInfo.setId(2L);
        userInfo.setTitle("我是程序员1121212");
        userInfo.setBrand("小米");
        userInfo.setCategory("手机");
        userInfo.setPrice(12d);


        UserInfo userInfo3=new UserInfo();
        userInfo3.setId(3L);
        userInfo3.setTitle("刘国澳又睡觉");
        userInfo3.setBrand("你要知道你的身份");
        userInfo3.setCategory("嘎嘎高");
        userInfo3.setPrice(12d);

        List list=new ArrayList();
        list.add(userInfo);
        list.add(userInfo3);

        elasticSearchDao.saveAll(list);
    }

    //根据id查询
    @Test
    public void findById(){
        Optional<UserInfo> optional = elasticSearchDao.findById(2L);
        //是否有值
        if(optional.isPresent()){
            UserInfo userInfo = optional.get();
            System.out.println(userInfo);
        }
    }
    //查询全部，并按照id降序排序
    @Test
    public void findAll(){
        Iterable<UserInfo> iterable = elasticSearchDao.findAll(Sort.by(Sort.Direction.DESC, "id"));
        for (UserInfo userInfo : iterable) {
            System.out.println(userInfo);
        }
    }


}
