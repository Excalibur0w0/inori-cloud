package com.inori.cloud.providerconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@EnableDiscoveryClient
@SpringBootApplication
public class ProviderConfigApplication {

    public static void main(String[] args) {

        SpringApplication.run(ProviderConfigApplication.class, args);
    }

}
