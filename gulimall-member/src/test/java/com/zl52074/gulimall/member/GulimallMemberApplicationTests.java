package com.zl52074.gulimall.member;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class GulimallMemberApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123456");
        System.out.println(encode);
    }

}
