package com.zl52074.gulimall.order.feign;

import com.zl52074.gulimall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@FeignClient("gulimall-product")
public interface ProductFeignService {

    /**
     * 根据skuId查询spu的信息
     * @param skuId
     * @return
     */
    @GetMapping(value = "/product/spuinfo/skuId/{skuId}")
    public R getSpuInfoBySkuId(@PathVariable("skuId") Long skuId);
    @GetMapping(value = "/product/skuinfo/info/{skuId}")
    public R getSkuInfoById(@PathVariable("skuId") Long skuId);

}
