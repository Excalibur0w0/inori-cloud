package com.inori.music;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProviderMusicApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderMusicApplication.class, args);
    }

}
