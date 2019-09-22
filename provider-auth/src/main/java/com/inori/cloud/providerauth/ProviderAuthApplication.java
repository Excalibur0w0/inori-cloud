package com.inori.cloud.providerauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ProviderAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderAuthApplication.class, args);
    }

}
