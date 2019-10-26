package com.inori.music.controller;

import com.inori.music.service.FetchUserService;
import com.inori.music.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("song")
public class SongController {
    @Autowired
    private SongService songService;
    @Autowired
    private FetchUserService fetchUserService;

    @PostMapping("like")
    public void like(@RequestHeader("Authorization") String authorization,
                     @RequestParam("songId") String songId) {
        String userId = fetchUserService.getUserId(authorization);
        songService.likeSong(songId, userId);
    }

    @DeleteMapping("like")
    public void dislike(@RequestHeader("Authorization") String authorization,
                        @RequestParam("songId") String songId) {
        String userId = fetchUserService.getUserId(authorization);
        songService.dislikeSong(songId, userId);
    }


}
