package com.zl52074.gulimall.search;

import com.alibaba.fastjson.JSON;
import com.zl52074.gulimall.search.entity.Invitation;
import com.zl52074.gulimall.search.es.InvitationRepository;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
class InvitationTest {

    /**
     * 基础操作
     */
    @Autowired
    private InvitationRepository invitationRepository;

    /**
     * 复杂操作
     */
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;



    /**
     * 测试新增
     */
    @Test
    void testInsert() {
        Invitation invitation = new Invitation();
        invitation.setId(2l);
        invitation.setName("测试");
        invitation.setCountry("美国");
        invitation.setAge(20);
        invitation.setIsDelete("0");
        invitation.setStatus("0");
        invitation.setSex("2");
        invitation.setType("2");
        invitation.setCreateDate(new Date());

        GeoPoint address =new GeoPoint(41.15998589289666,123.10144051130709);
        invitation.setAddress(address);

        invitationRepository.save(invitation);
    }


    /**
     * 查所有数据
     */
    @Test
    void testFindById() {
        Invitation invitation = invitationRepository.findById(1L).get();

    }

    /**
     * 查所有数据
     */
    @Test
    void testFindAll() {
        Iterable<Invitation> invitationIterable = invitationRepository.findAll();
        invitationIterable.forEach(x -> System.out.println(JSON.toJSONString(x)));
    }

    /**
     * 修改指定数据
     */
    @Test
    void testUpdate() {
        Invitation invitation = invitationRepository.findById(1L).orElse(null);
        System.out.println("修改前名称：" + invitation.getName());
        invitation.setName("测试111");
        invitationRepository.save(invitation);
        invitation = invitationRepository.findById(1L).orElse(null);
        System.out.println("修改后名称：" + invitation.getName());
    }

    /**
     * 删除指定数据
     */
    @Test
    void testDelete() {
        Long id = 1L;
        invitationRepository.deleteById(id);
        Invitation invitation = invitationRepository.findById(id).orElse(null);
        System.out.println(invitation == null? "删除成功": "删除失败");
    }


    /**
     * 通过name查询
     */
    @Test
    void testfindByName(){
        List<Invitation> invitationList = invitationRepository.findByName("儿子");
        invitationList.forEach(x -> System.out.println(JSON.toJSONString(x)));
    }



    /**
     * 通过name查询
     */
    @Test
    void testfindByName2() {
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("name", "儿子");
        NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(matchQueryBuilder)
            .withFields("id", "name", "country").build();
        SearchHits<Invitation> hits = elasticsearchRestTemplate.search(query, Invitation.class);
        List<SearchHit<Invitation>> searchHits = hits.getSearchHits();
        System.out.println("共" + hits.getTotalHits() + "条");

        searchHits.forEach(x -> System.out.println(JSON.toJSONString(x.getContent())));
    }



    /**
     * 复杂查询：通过范围、性别、年龄、类型、按距离升序、按时间倒序分页查询
     * @return
     */
    @Test
    void getInvitationList(){
        Integer page = 1;
        Integer size = 5;
        Double latitude = 41.1637913541259;
        Double longitude = 123.10181515177084;
        String sex = "3";
        Integer minage = 5;
        Integer maxage = 25;
        String type = "10";

        Pageable pageable = PageRequest.of(page - 1, size);
        //构建查询条件生成器
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //拼接条件
        //指定字段范围查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery("age").gte(minage).lte(maxage));
        //指定字段查询
        QueryBuilder isdeleteBuilder = QueryBuilders.termQuery("isDelete", "0");
        boolQueryBuilder.must(isdeleteBuilder);

        QueryBuilder statusBuilder = QueryBuilders.termQuery("status","0");
//        boolQueryBuilder.mustNot(statusBuilder);
        boolQueryBuilder.must(statusBuilder);

        if (!"3".equals(sex)&&null!=sex){
            QueryBuilder sexBuilder = QueryBuilders.termQuery("sex",sex);
            boolQueryBuilder.must(sexBuilder);
        }

        if (!"10".equals(type)&&null!=type){
            QueryBuilder typeBuilder = QueryBuilders.termQuery("type",type);
            boolQueryBuilder.must(typeBuilder);
        }

        //以某点为中心，搜索指定范围
        GeoDistanceQueryBuilder distanceQueryBuilder = new GeoDistanceQueryBuilder("address");
        distanceQueryBuilder.point(latitude, longitude);
        //查询单位：m
        distanceQueryBuilder.distance(10000, DistanceUnit.METERS);
        boolQueryBuilder.filter(distanceQueryBuilder);

        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);

        //按距离升序
        GeoDistanceSortBuilder distanceSortBuilder =
                new GeoDistanceSortBuilder("address", latitude, longitude);
        distanceSortBuilder.unit(DistanceUnit.KILOMETERS);
        distanceSortBuilder.order(SortOrder.ASC);
        nativeSearchQueryBuilder.withSort(distanceSortBuilder);

        //按时间倒序
        SortBuilder timeSort = SortBuilders.fieldSort("createDate").order(SortOrder.DESC);
        nativeSearchQueryBuilder.withSort(timeSort);

        //分页
        nativeSearchQueryBuilder.withPageable(pageable);

        NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();
        SearchHits<Invitation> hits = elasticsearchRestTemplate.search(searchQuery, Invitation.class);
        List<SearchHit<Invitation>> searchHits = hits.getSearchHits();

        System.out.println("共" + hits.getTotalHits() + "条");
        searchHits.forEach(x -> System.out.println(JSON.toJSONString(x.getContent())));
    }

}

