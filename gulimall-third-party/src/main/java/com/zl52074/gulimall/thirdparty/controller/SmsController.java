package com.zl52074.gulimall.thirdparty.controller;

import com.zl52074.gulimall.common.utils.R;
import com.zl52074.gulimall.thirdparty.component.SmsComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.RequestWrapper;

/**
 * @description:
 * @author: zl52074
 * @time: 2023/10/22 23:36
 */
@RestController
@RequestMapping("/sms")
public class SmsController {
    @Autowired
    private SmsComponent smsComponent;

    @RequestMapping("/sendCode")
    public R sendCode(@RequestParam("phone")String phone, @RequestParam("code")String code){
        smsComponent.sendSms(phone,code);
        return R.ok();
    }
}
