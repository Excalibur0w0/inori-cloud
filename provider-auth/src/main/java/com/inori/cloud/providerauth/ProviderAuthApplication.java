package com.inori.cloud.providerauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@MapperScan("com.inori.cloud.providerauth.dao.mapper")
@EnableDiscoveryClient
@SpringBootApplication
@EnableResourceServer
public class ProviderAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderAuthApplication.class, args);
    }

}
