package com.inori.cloud.providerauth.controller;

import com.inori.cloud.providerauth.pojo.FileImg;
import com.inori.cloud.providerauth.pojo.TblUser;
import com.inori.cloud.providerauth.service.UserService;
import com.inori.cloud.providerauth.service.imp.AuthService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("io")
public class IOController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @PostMapping("upload/avatar")
    public Boolean uploadUserAvatar(@RequestHeader("Authorization")String authorization,
                                    @RequestParam("base64")String imgStr) {

        if (authorization == null || authorization.length() <= 0) {
            throw new RuntimeException("token cannot be empty");
        }

        TblUser user = authService.getUserByToken(authorization);
        String userId = user.getUuid();

        byte[] buffer = Base64.decodeBase64(imgStr.split(",")[1]);
        System.out.println(buffer.length + " " + userId);
        userService.uploadUserAvatar(userId, buffer);

        return true;
    }

    @GetMapping("resource/img")
    public ResponseEntity<Resource> getImg(@RequestParam("imgPath") String imgPath) throws FileNotFoundException {
        FileImg img = userService.getAvatarImg(imgPath);
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

}
