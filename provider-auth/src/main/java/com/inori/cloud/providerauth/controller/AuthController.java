package com.inori.cloud.providerauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @GetMapping("test")
    public String test() {
        return "test";
    }
}