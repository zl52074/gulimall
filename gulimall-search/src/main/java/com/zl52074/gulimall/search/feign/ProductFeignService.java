package com.zl52074.gulimall.search.feign;

import com.zl52074.gulimall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Description:
 * @Created: with IntelliJ IDEA.
 * @author: 夏沫止水
 * @createTime: 2020-06-18 09:15
 **/

@FeignClient("gulimall-product")
public interface ProductFeignService {

    @GetMapping("/product/attr/info/{attrId}")
    public R attrInfo(@PathVariable("attrId") Long attrId);


    @RequestMapping("/product/brand/infos")
    public R brandInfos(@RequestParam List<Long> ids);

    @RequestMapping("/product/category/info/{catId}")
    public R categoryInfo(@PathVariable("catId") Long catId);

}
