package com.zl52074.gulimall.thirdparty;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@EnableDiscoveryClient
@EnableEncryptableProperties
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class GulimallThirdPartyApplication {
    public static void main(String[] args) {
        System.setProperty("jasypt.encryptor.privateKeyFormat","PEM");
        System.setProperty("jasypt.encryptor.privateKeyLocation","classpath:rsa_private_key.pem");
        SpringApplication.run(GulimallThirdPartyApplication.class, args);
    }

}
