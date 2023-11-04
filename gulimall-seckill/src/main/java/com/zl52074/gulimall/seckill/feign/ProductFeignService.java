package com.zl52074.gulimall.seckill.feign;


import com.zl52074.gulimall.common.utils.R;
import com.zl52074.gulimall.seckill.feign.fallback.ProductFeignServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



@FeignClient(value="gulimall-product",fallback = ProductFeignServiceFallback.class)
public interface ProductFeignService {

    @RequestMapping("/product/skuinfo/info/{skuId}")
    R getSkuInfo(@PathVariable("skuId") Long skuId);

}
