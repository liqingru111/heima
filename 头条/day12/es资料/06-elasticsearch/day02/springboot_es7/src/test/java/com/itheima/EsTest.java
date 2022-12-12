package com.itheima;

import com.alibaba.fastjson.JSON;
import com.itheima.pojo.Person;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsTest {

    @Autowired
   private RestHighLevelClient client;

    /**
     * 测试
     */
    @Test
    public void contextLoads() {
        System.out.println(client);
    }
    /*
        创建索引库,
            添加索引，未指定映射
     */
    @Test
    public void createIndex() throws IOException {
        //先创建索引集合
        IndicesClient indices = client.indices();

        //指定索引库的名称
        CreateIndexRequest createIndexRequest=new CreateIndexRequest("person");

        /*
            创建索引
            create(CreateIndexRequest createIndexRequest, RequestOptions options)
            参数1表示创建索引请求对象
            参数2表示请求参数
         */
        CreateIndexResponse response = indices.create(createIndexRequest, RequestOptions.DEFAULT);
        System.out.println(response.isAcknowledged());
    }

    /*
       创建索引库,
           添加索引，指定映射
    */
    @Test
    public void createIndexAndMapping() throws IOException {
        //先创建索引集合
        IndicesClient indices = client.indices();

        //指定索引库的名称
        CreateIndexRequest createIndexRequest=new CreateIndexRequest("person");

        //指定映射信息
        String mapping="{\n" +
                "      \"properties\" : {\n" +
                "        \"address\" : {\n" +
                "          \"type\" : \"text\",\n" +
                "          \"analyzer\" : \"ik_max_word\"\n" +
                "        },\n" +
                "        \"age\" : {\n" +
                "          \"type\" : \"long\"\n" +
                "        },\n" +
                "        \"name\" : {\n" +
                "          \"type\" : \"keyword\"\n" +
                "        }\n" +
                "      }\n" +
                "    }";

        /**
         * 添加映射
         *  mapping(String type, String source, XContentType xContentType)
         * 三个参数
         *  参数1表示当前映射类型，值是_doc
         *  参数2表示映射信息
         *  参数3表示内容类型，一般是json
         *
         */
        createIndexRequest.mapping("_doc",mapping, XContentType.JSON);
        /*
            创建索引
            create(CreateIndexRequest createIndexRequest, RequestOptions options)
            参数1表示创建索引请求对象
                包含了指定索引名称，和映射关系
            参数2表示请求参数
         */
        CreateIndexResponse response = indices.create(createIndexRequest, RequestOptions.DEFAULT);
        System.out.println(response.isAcknowledged());
    }
    /*
        查询索引
        get的api
     */
    @Test
    public void getIndex() throws IOException {
        //先创建索引集合
        IndicesClient indices = client.indices();

        //指定索引库的名称
        GetIndexRequest getIndexRequest=new GetIndexRequest("person");
        /*
            查询索引
            get(GetIndexRequest getIndexRequest, RequestOptions options)
            参数1表示获取索引请求对象
            参数2表示请求参数
         */
        GetIndexResponse response = indices.get(getIndexRequest, RequestOptions.DEFAULT);
        Map<String, MappingMetaData> metaDataMap = response.getMappings();
        for (String key : metaDataMap.keySet()) {
            System.out.println(key+"===="+metaDataMap.get(key).getSourceAsMap());
        }
    }
    //删除  delete
    @Test
    public void deleteIndex() throws IOException {
        //先创建索引集合
        IndicesClient indices = client.indices();

        //指定索引库的名称
        DeleteIndexRequest deleteIndexRequest=new DeleteIndexRequest("aaa");
        /*
            查询索引
            get(GetIndexRequest getIndexRequest, RequestOptions options)
            参数1表示获取索引请求对象
            参数2表示请求参数
         */

        AcknowledgedResponse response = indices.delete(deleteIndexRequest, RequestOptions.DEFAULT);
        boolean acknowledged = response.isAcknowledged();
        System.out.println(acknowledged);
    }

    //判断索引
    @Test
    public void isIndex() throws IOException {
        //1、创建索引对象
        IndicesClient indices = client.indices();
        //3.指定索引名称 itheima
        GetIndexRequest getIndexRequest=new GetIndexRequest("aaa");
        //4.使用默认请求参数
        RequestOptions requestOptions=RequestOptions.DEFAULT;
        //2.判断索引，参数1表示索引请求对象，参数2索引请求参数
        boolean exists = indices.exists(getIndexRequest, requestOptions);
        System.out.println(exists);
    }

    //添加文档
    @Test
    public void creatDocument() throws IOException {
        //第三步
        Map<String, Object> map=new HashMap<>();
        map.put("address","北京天安门");
        map.put("name","习大大");
        map.put("age",60);

        /**第二步，
         * IndexRequest（要添加文档的索引库名称）
         * id(文档的id)
         * source(添加的文档信息)
         */
        IndexRequest indexRequest=new IndexRequest("person").id("1").source(map);
        /**第一步，
         * index(IndexRequest indexRequest, RequestOptions options)
         * 参数1表示 索引请求对象
         * 参数2表示请求参数，一般是默认的
         */
        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(response.getId());
    }


    //添加文档2
    @Test
    public void creatDocument2() throws IOException {
        //第三步
        Person person=new Person();
        person.setName("李四");
        person.setAge(20);
        person.setAddress("北京三环");
        //第四步，把实体转为json字符串
        String source = JSON.toJSONString(person);

        /**第二步，
         * IndexRequest（要添加文档的索引库名称）
         * id(文档的id)
         * source(添加的文档信息)
         */
        IndexRequest indexRequest=new IndexRequest("person").id("2").source(source,XContentType.JSON);
        /**第一步，
         * index(IndexRequest indexRequest, RequestOptions options)
         * 参数1表示 索引请求对象
         * 参数2表示请求参数，一般是默认的
         */
        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(response.getId());
    }

    /*
        修改文档
        client.index（）
        如果说id存在，则进行修改操作，如果id不存在，则会进行添加文档操作
     */
    @Test
    public void updateDocument() throws IOException {
        //第三步
        Person person=new Person();
        person.setName("刘国澳不要睡觉了");
        person.setAge(18);
        person.setAddress("北京二环内四合院");
        //第四步，把实体转为json字符串
        String source = JSON.toJSONString(person);

        /**第二步，
         * IndexRequest（要添加文档的索引库名称）
         * id(文档的id)
         * source(添加的文档信息)
         */
        IndexRequest indexRequest=new IndexRequest("person").id("2").source(source,XContentType.JSON);
        /**第一步，
         * index(IndexRequest indexRequest, RequestOptions options)
         * 参数1表示 索引请求对象
         * 参数2表示请求参数，一般是默认的
         */
        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(response.getId());
    }

    /**
     * 根据id查询文档
     * @throws IOException
     */
    @Test
    public void getDoc() throws IOException {
        /**
         * GetRequest(String index, String id)
         *  参数1表示索引名称
         *  参数2表示id
         */
        GetRequest getRequest=new GetRequest("person","1");
        //查询文档
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(getResponse.getSourceAsString());
    }

    /**
     * 查询文档
     * @throws IOException
     */
    @Test
    public void getDoc2() throws IOException {
        //2.指定索引库名称
        SearchRequest searchRequest=new SearchRequest("person");
        //1.search表示全查索引库
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        //3.响应对象中获取查询命中的集合
        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
        }
    }

    /**
     * 根据id删除文档
     */
    @Test
    public void delDoc() throws IOException {
        //设置要删除的索引、文档
        DeleteRequest deleteRequest=new DeleteRequest("person","1");
        DeleteResponse response = client.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(response.getId());
    }


















}
