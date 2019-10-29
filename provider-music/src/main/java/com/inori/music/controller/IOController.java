package com.inori.music.controller;

import com.inori.music.dto.UploadProgressDTO;
import com.inori.music.pojo.FileImg;
import com.inori.music.service.SongService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Log4j2
@RequestMapping("io")
@RestController
public class IOController {
    @Autowired
    private SongService songService;

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
        // 不存在坏块    开启子线程进行文件合并
        Thread th = new Thread(() -> songService.afterCompletedUpload(md5, extenstion, uploader));
        th.start();
        // 主线程直接返回
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

    @GetMapping("resource/img")
    public ResponseEntity<Resource> getImg(@RequestParam("imgPath") String imgPath) throws FileNotFoundException {
        FileImg img = songService.getSongImage(imgPath);
        Resource resource = new ByteArrayResource(img.getData());
        String contentType = "image/" + img.getFiletype();

        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("resource/audio")
    public ResponseEntity<Resource> getAudio(@RequestParam("md5") String md5) throws IOException {
        if(md5 == null || md5.trim().length() == 0 || md5.equals("undefined")) {
            return ResponseEntity.badRequest().body(null);
        }

        Resource resource = songService.getSingleSongByMd5(md5);
        String contentType = "audio/mpeg";
        // chrome 必须 fireFox不必要
        long contentLength = resource.contentLength();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
        headers.add(HttpHeaders.ACCEPT_RANGES, "bytes");

        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .contentLength(contentLength)
                .headers(headers)
                .body(resource);
    }
}

