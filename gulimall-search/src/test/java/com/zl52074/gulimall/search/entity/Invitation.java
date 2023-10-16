package com.zl52074.gulimall.search.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.util.Date;

@Data
@Document(indexName = "invitation",createIndex = true)
public class Invitation {

    @Id
    private Long id;

//    //指定字段的索引方式，index是否索引、store是否存储、字段的分词方式、搜索时关键字分词的方式、type指定该字段的值以什么样的数据类型来存储
//    @Field(index=true,store=true,analyzer="ik_max_word",searchAnalyzer="ik_max_word",type=FieldType.Text)
    /* ik_smart:粗粒度分词 */
    @Field(analyzer = "ik_smart", type = FieldType.Text)
    private String name;

    /* ik_max_word:细粒度分词 */
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String country;

    @Field(type = FieldType.Integer)
    private Integer age;

    @Field(type = FieldType.Text)
    private String isDelete;

    @Field(type = FieldType.Text)
    private String status;

    @Field(type = FieldType.Text)
    private String sex;

    @Field(type = FieldType.Text)
    private String type;

    @Field(type = FieldType.Date,format = DateFormat.basic_date_time)
    private Date createDate;


    //es中的位置字段，存储的是经纬度，方便进行范围搜索
    @GeoPointField
    private GeoPoint address;
}

