package com.zl52074.gulimall.coupon.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zl52074.gulimall.coupon.entity.CouponEntity;
import com.zl52074.gulimall.coupon.service.CouponService;
import com.zl52074.gulimall.common.utils.PageUtils;
import com.zl52074.gulimall.common.utils.R;



/**
 * 优惠券信息
 *
 * @author zl52074
 * @email
 * @date 2023-10-05 18:16:20
 */
@RestController
@RequestMapping("coupon/coupon")
public class CouponController {
    @Autowired
    private CouponService couponService;

    @GetMapping("/member/list")
    public R memberCouponList(){
        CouponEntity coupon = new CouponEntity();
        coupon.setCouponName("test");
        return R.ok().put("couponList", Arrays.asList(coupon));
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
        public R list(@RequestParam Map<String, Object> params){
        PageUtils page = couponService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
        public R info(@PathVariable("id") Long id){
		CouponEntity coupon = couponService.getById(id);

        return R.ok().put("coupon", coupon);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
        public R save(@RequestBody CouponEntity coupon){
		couponService.save(coupon);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
        public R update(@RequestBody CouponEntity coupon){
		couponService.updateById(coupon);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
        public R delete(@RequestBody Long[] ids){
		couponService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
