package com.zl52074.gulimall.product;

import com.zl52074.gulimall.product.entity.BrandEntity;
import com.zl52074.gulimall.product.service.BrandService;
import com.zl52074.gulimall.product.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.util.UUID;

@SpringBootTest
class GulimallProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Value("${spring.datasource.url}")
    String url;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedissonClient redissonClient;



    @Test
    public void testRedisson() {
        redisUtil.hset("test","object",new Object());
        System.out.println(redissonClient);
    }

    @Test
    public void testStringRedis() {
        ValueOperations<String, String> ops= stringRedisTemplate.opsForValue();

        //保存
        ops.set("hello","world_" + UUID.randomUUID().toString());

        //查询
        String hello = ops.get("hello");
        System.out.println("之前保存的数据:"+hello);
    }

    @Test
    void test() {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName("test");
        System.out.println(url);
        brandService.save(brandEntity);
    }

}
