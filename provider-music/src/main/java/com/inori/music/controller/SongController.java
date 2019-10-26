package com.inori.music.controller;

import com.inori.music.dto.UploadProgressDTO;
import com.inori.music.pojo.TblSong;
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

import java.util.List;

@Log4j2
@RestController
@RequestMapping("song")
public class SongController {
    @Qualifier("HSongServiceImp")
    @Autowired
    private SongService songService;

    @GetMapping("getSongsBySheet")
    public List<TblSong> getSongsBySheet(@RequestParam("sheetId") String sheetId) {
        return songService.getSongsByShtId(sheetId);
    }

    @GetMapping("searchSongs")
    public List<TblSong> searchSongs(@RequestParam("keywords")String keywords) {
        return songService.searchAllSongs(keywords);
    }

    @GetMapping("getSongsByAuthor")
    public List<TblSong> getSongsByAuthor(@RequestParam("author")String author) {
        return songService.getSongsByAuthor(author);
    }

    @GetMapping("getSongsByUploader")
    public List<TblSong> getSongsByUploader(@RequestParam("uploaderId")String uploaderId) {
        return songService.getSongsByUploader(uploaderId);
    }

    @PostMapping("upload")
    public UploadProgressDTO upload(@RequestParam("file") MultipartFile file,
                                    @RequestParam("md5") String md5,
                                    @RequestParam("chunkIndex") Long chunkIndex,
                                    @RequestParam("chunkTotal") Long chunkTotal,
                                    @RequestParam("uploader") String uploader,
                                    @RequestParam("extenstion") String extenstion,
                                    @RequestParam("checkRightNow") Boolean checkRightNow) {
        log.info(chunkIndex + " " + chunkTotal);


        if (chunkIndex < chunkTotal) {
            songService.uploadSingleSongChunk(md5, file, chunkIndex, chunkTotal);

            if (chunkIndex + 1 == chunkTotal || checkRightNow) {
                return checkAndRegist(md5, chunkTotal, extenstion, uploader);
            } else {
                return new UploadProgressDTO(true, null);
            }
        } else {
            return new UploadProgressDTO(false, null);
        }
    }

    public UploadProgressDTO checkAndRegist(String md5, Long chunkTotal, String extenstion, String uploader) {
        List<Long> badChunks = songService.checkSongIntegrity(md5, chunkTotal);

        if (badChunks != null) {
            return new UploadProgressDTO(false, badChunks);
        }
        // 不存在坏块
        songService.afterCompletedUpload(md5, extenstion, uploader);
        return new UploadProgressDTO(false, null);
    }


    @GetMapping("download")
    public ResponseEntity<Resource> download(@RequestParam("md5") String md5) {

        Resource resource = songService.getSingleSongByMd5(md5);

        // Try to determine file's content type
        String contentType = null;

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
