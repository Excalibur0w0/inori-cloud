package com.inori.cloud.providerauth.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("demo")
public interface DemoServiceClient {

    @GetMapping("/feign-test")
    String consumer();
}
