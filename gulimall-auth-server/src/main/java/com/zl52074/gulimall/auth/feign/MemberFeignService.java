package com.zl52074.gulimall.auth.feign;

import com.zl52074.gulimall.auth.vo.SocialUser;
import com.zl52074.gulimall.auth.vo.UserLoginVo;
import com.zl52074.gulimall.auth.vo.UserRegisterVo;
import com.zl52074.gulimall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@FeignClient("gulimall-member")
public interface MemberFeignService {

    @PostMapping(value = "/member/member/register")
    R register(@RequestBody UserRegisterVo vo);


    @PostMapping(value = "/member/member/login")
    R login(@RequestBody UserLoginVo vo);

    @PostMapping(value = "/member/member/oauth2/login")
    R oauthLogin(@RequestBody SocialUser socialUser) throws Exception;
}
