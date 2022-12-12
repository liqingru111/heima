package com.itheima.dao;

import com.itheima.pojo.UserInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
/*
ElasticsearchRepository<T, ID extends Serializable>
    参数1表示 要映射的类名
    参数2表示 映射实体中的主键的类型
 */
public interface ElasticSearchDao extends ElasticsearchRepository<UserInfo,Long>{
}
