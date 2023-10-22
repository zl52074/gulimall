package com.zl52074.gulimall.auth.feign;

import com.zl52074.gulimall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description:
 * @author: zl52074
 * @time: 2023/10/22 23:50
 */
@FeignClient("gulimall-third-party")
public interface ThirdPartyFeignService {
    @RequestMapping("/sms/sendCode")
    public R sendCode(@RequestParam("phone")String phone, @RequestParam("code")String code);
}
