package com.inori.cloud.providerauth.controller;

import com.inori.cloud.providerauth.client.DemoServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class DemoController {

    @Autowired
    private DemoServiceClient client;

    @RequestMapping("/hi")
    public String hi() {
        return "hi, 你好";
    }

    @RequestMapping("/demo")
    public String test() {
        return client.consumer();
    }

    @RequestMapping("/hello")
    @PreAuthorize("hasAnyAuthority('ROLE_TEST','ROLE_ADMIN')")
    public String hello() {
        return "hello, nihao";
    }

    @RequestMapping("getPrincipal")
    public OAuth2Authentication getPrinciple(OAuth2Authentication oAuth2Authentication, Principal principal, Authentication authentication) {
        System.out.println("====================================");
        System.out.println(oAuth2Authentication);
        System.out.println(principal);
        System.out.println(authentication);
        System.out.println("====================================");
        return oAuth2Authentication;
    }
}
