package com.zl52074.gulimall.member.controller;

import java.util.Arrays;
import java.util.Map;

import com.zl52074.gulimall.common.exception.BizCodeEnum;
import com.zl52074.gulimall.member.exception.PhoneExistException;
import com.zl52074.gulimall.member.exception.UsernameExistException;
import com.zl52074.gulimall.member.feign.CouponService;
import com.zl52074.gulimall.member.vo.MemberUserLoginVo;
import com.zl52074.gulimall.member.vo.MemberUserRegisterVo;
import com.zl52074.gulimall.member.vo.SocialUser;
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

    @PostMapping(value = "/oauth2/login")
    public R oauthLogin(@RequestBody SocialUser socialUser) throws Exception {

        MemberEntity memberEntity = memberService.login(socialUser);

        if (memberEntity != null) {
            return R.ok().setData(memberEntity);
        } else {
            return R.error(BizCodeEnum.LOGINACCT_PASSWORD_EXCEPTION.getCode(),BizCodeEnum.LOGINACCT_PASSWORD_EXCEPTION.getMsg());
        }
    }

    @PostMapping(value = "/register")
    public R register(@RequestBody MemberUserRegisterVo vo) {

        try {
            memberService.register(vo);
        } catch (PhoneExistException e) {
            return R.error(BizCodeEnum.PHONE_EXIST_EXCEPTION.getCode(),BizCodeEnum.PHONE_EXIST_EXCEPTION.getMsg());
        } catch (UsernameExistException e) {
            return R.error(BizCodeEnum.USER_EXIST_EXCEPTION.getCode(),BizCodeEnum.USER_EXIST_EXCEPTION.getMsg());
        }

        return R.ok();
    }


    @PostMapping(value = "/login")
    public R login(@RequestBody MemberUserLoginVo vo) {

        MemberEntity memberEntity = memberService.login(vo);

        if (memberEntity != null) {
            return R.ok().setData(memberEntity);
        } else {
            return R.error(BizCodeEnum.LOGINACCT_PASSWORD_EXCEPTION.getCode(),BizCodeEnum.LOGINACCT_PASSWORD_EXCEPTION.getMsg());
        }
    }

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
