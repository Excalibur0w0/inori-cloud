package com.inori.cloud.providerconfig.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class configTestController {

    @GetMapping("test")
    public String test() {
        return "config test";
    }

}
