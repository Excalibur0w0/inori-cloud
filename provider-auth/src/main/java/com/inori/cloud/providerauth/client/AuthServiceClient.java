package com.inori.cloud.providerauth.client;

import com.inori.cloud.providerauth.pojo.JWT;
import feign.Request;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "oauth2", fallback = AuthServiceHystrix.class)
public interface AuthServiceClient {
    @RequestMapping(value = "/oauth/token", method = RequestMethod.POST)
    JWT getToken(@RequestHeader(value = "Authorization")String authorization,
                 @RequestParam("grant_type") String type,
                 @RequestParam("username") String username,
                 @RequestParam("password") String password);
}
