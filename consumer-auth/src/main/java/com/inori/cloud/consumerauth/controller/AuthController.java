package com.inori.cloud.consumerauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private AuthProvider authProvider;

    @GetMapping("test") // 自己的路由
    public String consumer() {
        return authProvider.consumer();
    }

    @FeignClient("provider-auth")
    public interface AuthProvider {
        // 映射到对应provider 的路由
        @GetMapping("test")
        String consumer();
    }
}
