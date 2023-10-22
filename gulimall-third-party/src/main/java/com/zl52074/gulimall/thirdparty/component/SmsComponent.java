package com.zl52074.gulimall.thirdparty.component;

import com.zl52074.gulimall.thirdparty.util.HttpUtils;
import com.zl52074.gulimall.thirdparty.util.RSAPemUtil;
import lombok.Data;
import org.apache.http.HttpResponse;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: zl52074
 * @time: 2023/10/22 22:48
 */
@ConfigurationProperties(prefix = "spring.cloud.alicloud.sms")
@Component
@Data
public class SmsComponent {
    private String host;
    private String path;
    private String templateId;
    private String appcode;


    public void sendSms(String phone,String code){
        String method = "POST";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("content", "code:"+code);
        bodys.put("template_id", templateId);
        bodys.put("phone_number", phone);


        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
