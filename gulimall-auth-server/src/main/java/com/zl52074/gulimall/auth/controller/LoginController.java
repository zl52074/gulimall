package com.zl52074.gulimall.auth.controller;

import com.zl52074.gulimall.common.constatnt.AuthServerConstant;
import com.zl52074.gulimall.auth.feign.ThirdPartyFeignService;
import com.zl52074.gulimall.common.exception.BizCodeEnum;
import com.zl52074.gulimall.common.utils.R;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: zl52074
 * @time: 2023/10/22 23:53
 */
@RestController
public class LoginController {
    @Autowired
    private ThirdPartyFeignService thirdPartyFeignService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping(value = "/sms/sendCode")
    public R sendCode(@RequestParam("phone") String phone) {

        //1、接口防刷
        String redisCode = stringRedisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone);
        if (!StringUtils.isEmpty(redisCode)) {
            //活动存入redis的时间，用当前时间减去存入redis的时间，判断用户手机号是否在60s内发送验证码
            long currentTime = Long.parseLong(redisCode.split("_")[1]);
            if (System.currentTimeMillis() - currentTime < 60*1000) {
                //60s内不能再发
                return R.error(BizCodeEnum.SMS_CODE_EXCEPTION.getCode(),BizCodeEnum.SMS_CODE_EXCEPTION.getMsg());
            }
        }

        //2、验证码的再次效验 redis.存key-phone,value-code
        //6位随机数code
        // n=new Random.nextInt(max-min+1)+min
        //(int) (Math.random() * (end - start + 1)) + start;
        int code = new Random().nextInt(1000000-100000+1)+100000;
        String codeNum = String.valueOf(code);
        String redisStorage = codeNum + "_" + System.currentTimeMillis();

        //存入redis，防止同一个手机号在60秒内再次发送验证码
        stringRedisTemplate.opsForValue().set(AuthServerConstant.SMS_CODE_CACHE_PREFIX+phone, redisStorage,10, TimeUnit.MINUTES);

        thirdPartyFeignService.sendCode(phone, codeNum);

        return R.ok();
    }
}
