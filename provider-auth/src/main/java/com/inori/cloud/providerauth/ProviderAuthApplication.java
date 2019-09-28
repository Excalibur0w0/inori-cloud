package com.inori.cloud.providerauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@MapperScan("com.inori.cloud.providerauth.dao.mapper")
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class ProviderAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderAuthApplication.class, args);
    }

}
