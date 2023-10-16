package com.zl52074.gulimall.search.es;

import com.zl52074.gulimall.search.entity.Invitation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.stereotype.Service;


/**
 * 索引管理类
 */
@Service
public class IndexManage {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 创建索引和mapping
     * es不支持修改mapping，如果想要修改mapping，只能备份原来的数据，删除原有索引重新创建
     */
//    @PostConstruct
    public boolean  createIndex(){
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(Invitation.class);

        //如果存在索引，先删除索引
        if (indexOperations.exists()){
            indexOperations.delete();
        }

        //创建索引
        boolean a = indexOperations.create();
        if (a){
            //生成映射
            Document mapping = indexOperations.createMapping();
            //推送映射
            boolean b = indexOperations.putMapping(mapping);
            return b;
        }else {
            return a;
        }
    }


    public boolean deleteIndex() {
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(Invitation.class);
        boolean delete = indexOperations.delete();
        return delete;
    }
}

