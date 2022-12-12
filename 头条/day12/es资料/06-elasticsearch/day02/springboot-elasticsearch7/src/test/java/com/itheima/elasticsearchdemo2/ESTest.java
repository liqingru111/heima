package com.itheima.elasticsearchdemo2;

import com.alibaba.fastjson.JSON;
import com.itheima.elasticsearchdemo2.domain.Goods;
import com.itheima.elasticsearchdemo2.mapper.GoodsMapper;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@SpringBootTest
public class ESTest {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private RestHighLevelClient client;

    /**
     * 批量导入
     */
    @Test
    public void saveAll() throws IOException {
        //第一步，全查goods表
        List<Goods> goodsList = goodsMapper.findAll();
        //第三步，批量请求类
        BulkRequest bulkRequest=new BulkRequest();


        for (Goods goods : goodsList) {
            IndexRequest indexRequest=new IndexRequest();
            
            //1.先把spec取出来
            String specStr = goods.getSpecStr();
            //2.把json字符串转化成map集合
            Map specMap = JSON.parseObject(specStr, Map.class);
            //3.存入到goods实体中的Map类型spec属性中
            goods.setSpec(specMap);
            
            //把goods对象转为json字符串
            String goodsStr = JSON.toJSONString(goods);
            indexRequest.index("goods").source(goodsStr, XContentType.JSON);
            //第四步，添加文档请求对象
            bulkRequest.add(indexRequest);
        }

        //第二步，批量导入
        BulkResponse responses = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(responses.status());
    }

}
