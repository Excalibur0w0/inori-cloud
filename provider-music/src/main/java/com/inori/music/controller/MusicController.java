package com.inori.music.controller;

import com.inori.music.pojo.TblSong;
import com.inori.music.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MusicController {

    @GetMapping("/musicTest")
    public String test() {
        return "success";
    }
}
