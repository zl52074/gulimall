package com.zl52074.gulimall.search.es;

import com.zl52074.gulimall.search.entity.Invitation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 此类和@Document中的createIndex = true配合，启动就可以将实体类变成索引注入到es中
 * 不需要写具体的实现，函数名遵循命名规范即可自动实现
 * 命名规则参考：https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#repositories.query-methods
 * 此类完成的是一些基础查询，复杂查询用ElasticsearchRestTemplate
 */
@Repository
public interface InvitationRepository extends ElasticsearchRepository<Invitation, Long> {

    //根据名字查询 findBy+字段名
    List<Invitation> findByName(String name);

    //根据地址查询 findBy+字段名
    List<Invitation> findByAddress(String address);

    //根据地址和姓名查询  findBy+多个字段名之间And分隔
    List<Invitation> findByAddressAndName(String address,String name);

    //查询id小于某个值的数据  findBy+比大小的字段+LessThan
    List<Invitation> findByIdLessThan(int id);

    //查询年龄在多少-多少之间的   findBy+条件字段+Between
    List<Invitation> findByAgeBetween(Integer minAge,Integer maxAge);


}

