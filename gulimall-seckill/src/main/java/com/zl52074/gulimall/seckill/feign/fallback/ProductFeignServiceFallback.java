package com.zl52074.gulimall.seckill.feign.fallback;


import com.zl52074.gulimall.common.utils.R;
import com.zl52074.gulimall.seckill.feign.ProductFeignService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
public class ProductFeignServiceFallback implements ProductFeignService {

    public R getSkuInfo(Long skuId){
        return R.error("ProductFeignServiceFallback：熔断");
    }

}
