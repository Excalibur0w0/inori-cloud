package com.inori.music.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "provider-auth", fallback = UserServiceHystrix.class)
public interface UserServiceClient {
    @GetMapping(value = "auth/getUserInfoByToken")
    String getUserInfoByToken(@RequestHeader(value = "Authorization") String authorization);
}