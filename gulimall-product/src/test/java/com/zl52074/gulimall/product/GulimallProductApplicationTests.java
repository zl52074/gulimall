package com.zl52074.gulimall.product;

import com.zl52074.gulimall.product.entity.BrandEntity;
import com.zl52074.gulimall.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GulimallProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Value("${spring.datasource.url}")
    String url;

    @Test
    void test() {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName("test");
        System.out.println(url);
        brandService.save(brandEntity);
    }

}
