package com.zl52074.gulimall.member.controller;

import java.util.Arrays;
import java.util.Map;

import com.zl52074.gulimall.member.feign.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zl52074.gulimall.member.entity.MemberEntity;
import com.zl52074.gulimall.member.service.MemberService;
import com.zl52074.gulimall.common.utils.PageUtils;
import com.zl52074.gulimall.common.utils.R;



/**
 * 会员
 *
 * @author zl52074
 * @email
 * @date 2023-10-05 09:04:48
 */
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    CouponService couponService;

    @GetMapping("/coupon/list")
    public R memberCouponList(){
        return R.ok().put("couponList", couponService.memberCouponList());
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
        public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
        public R info(@PathVariable("id") Long id){
		MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
        public R save(@RequestBody MemberEntity member){
		memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
        public R update(@RequestBody MemberEntity member){
		memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
        public R delete(@RequestBody Long[] ids){
		memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
