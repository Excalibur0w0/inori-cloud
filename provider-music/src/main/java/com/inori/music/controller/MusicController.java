package com.inori.music.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MusicController {

    @GetMapping("/musicTest")
    public String test() {
        return "success";
    }
}
