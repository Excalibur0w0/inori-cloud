package com.inori.cloud.providerauth.controller;

import com.inori.cloud.providerauth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    @GetMapping("auth")
    public String auth() {
        return "auth desu";
    }
}
