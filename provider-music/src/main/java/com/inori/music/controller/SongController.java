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
    public Boolean like(@RequestHeader("Authorization") String authorization,
                     @RequestParam("songId") String songId) {
        String userId = fetchUserService.getUserId(authorization);
        songService.likeSong(songId, userId);
        return true;
    }

    @DeleteMapping("like")
    public Boolean dislike(@RequestHeader("Authorization") String authorization,
                        @RequestParam("songId") String songId) {
        String userId = fetchUserService.getUserId(authorization);
        songService.dislikeSong(songId, userId);
        return true;
    }

    @PostMapping("collect")
    public Boolean collect(@RequestHeader("Authorization") String authorization,
                           @RequestParam("sheetId") String sheetId,
                           @RequestParam("songId") String songId) {
        String userId = fetchUserService.getUserId(authorization);

        songService.collectSong(sheetId, songId, userId);

        return true;
    }

    @DeleteMapping("collect")
    public Boolean cancelCollect(@RequestParam("Authorization") String authorization,
                                 @RequestParam("sheetId") String sheetId,
                                 @RequestParam("songId") String songId) {
        String userId = fetchUserService.getUserId(authorization);

        songService.cancelCollect(sheetId, songId, userId);

        return true;
    }


}
