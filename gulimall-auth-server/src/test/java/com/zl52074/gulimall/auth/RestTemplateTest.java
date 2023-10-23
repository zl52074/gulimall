package com.zl52074.gulimall.auth;

import com.zl52074.gulimall.auth.vo.SocialUser;
import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: zl52074
 * @time: 2023/10/23 22:49
 */
public class RestTemplateTest {
    public static void main(String[] args) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://api.weibo.com/2/users/show.json")
                .queryParam("access_token", "2.00mRW3bH0muHCLe424ad5acb5ZN8EC")
                .queryParam("uid", "6965021582");
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, null, Map.class);
        System.out.println(response);
    }
}
