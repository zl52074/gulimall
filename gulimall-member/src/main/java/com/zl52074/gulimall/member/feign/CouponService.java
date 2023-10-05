package com.zl52074.gulimall.member.feign;

import com.zl52074.gulimall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("gulimall-coupon")
@RequestMapping("coupon/coupon")
public interface CouponService {
    @GetMapping("/member/list")
    public R memberCouponList();
}
