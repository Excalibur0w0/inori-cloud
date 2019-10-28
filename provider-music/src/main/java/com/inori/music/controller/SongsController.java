package com.inori.music.controller;

import com.inori.music.dto.SongDTO;
import com.inori.music.dto.UploadProgressDTO;
import com.inori.music.pojo.TblSong;
import com.inori.music.service.FetchUserService;
import com.inori.music.service.SongService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("songs")
public class SongsController {
    @Qualifier("HSongServiceImp")
    @Autowired
    private SongService songService;
    @Autowired
    private FetchUserService fetchUserService;

    /**
     * 只能通过一个参数进行调用，如果传入多个，报异常
     * @param sheetId
     * @param keywords
     * @param author
     * @param uploaderId
     * @param likeId 查询某个用户的所有“喜欢”的歌曲 (ps: 不取token是因为其他用户也可以查)
     * @return
     */
    @GetMapping
    public List<SongDTO> findSongs(@RequestParam(value = "sheetId", required = false)String sheetId,
                                   @RequestParam(value = "keywords", required = false)String keywords,
                                   @RequestParam(value = "author", required = false) String author,
                                   @RequestParam(value = "uploaderId", required = false) String uploaderId,
                                   @RequestParam(value = "likeId", required = false) String likeId,
                                   @RequestHeader(value = "Authorization", required = false) String authorization) {
        List<String> args = new ArrayList<>();
        int count = 0;
        args.add(sheetId);
        args.add(keywords);
        args.add(author);
        args.add(uploaderId);
        args.add(likeId);
        for (String arg : args) {
            if (arg != null) {
                count++;
            }
        }
        if (count > 1) {
            // 不允许传入多个参数
            throw new IllegalArgumentException("传入参数过多");
        }
        List<TblSong> tblSongs = null;
        List<SongDTO> results = null;

        if (sheetId != null) {
            tblSongs = songService.getSongsByShtId(sheetId);
        } else if (keywords != null) {
            tblSongs = songService.searchAllSongs(keywords);
        } else if (author != null) {
            tblSongs = songService.getSongsByAuthor(author);
        } else if (uploaderId != null) {
            tblSongs = songService.getSongsByUploader(uploaderId);
        } else if (likeId != null) {
            return songService.getSongsByUserLike(likeId);
        } else {
            throw new IllegalArgumentException("没有传入参数");
        }
        if (authorization != null) {
            String userId = fetchUserService.getUserId(authorization);
            results = songService.wrapWithFavorite(tblSongs, userId);
        } else {
            results = new ArrayList<>();
            for (TblSong tblSong : tblSongs) {
                results.add(new SongDTO(tblSong, false));
            }
        }
        return results;
    }

    @GetMapping("favorite")
    public List<TblSong> getUserFavoriteSongs(@RequestHeader("Authorization") String authorization) {
        String userId = fetchUserService.getUserId(authorization);
        return songService.getUserFavoriteSongs(userId);
    }
}
