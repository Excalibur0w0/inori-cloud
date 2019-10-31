package com.inori.cloud.providerauth.controller;

import com.inori.cloud.providerauth.client.AuthServiceClient;
import com.inori.cloud.providerauth.dto.UserBasicDTO;
import com.inori.cloud.providerauth.pojo.TblUser;
import com.inori.cloud.providerauth.service.UserService;
import com.inori.cloud.providerauth.service.imp.AuthService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;
    @Autowired
    private AuthServiceClient client;

    @GetMapping("getUserBasicInfo")
    public UserBasicDTO getUserBasicInfo(@RequestParam("userId")String userId) {
        return userService.getUserBasicInfo(userId);
    }

    @PostMapping("upload/avatar")
    public Boolean uploadUserAvatar(@RequestHeader("Authorization")String authorization,
                                    @RequestParam("base64")String imgStr) {

        if (authorization == null || authorization.length() <= 0) {
            throw new RuntimeException("token cannot be empty");
        }

        TblUser user = authService.getUserByToken(authorization);
        String userId = user.getUuid();

        byte[] buffer = Base64.decodeBase64(imgStr);
        userService.uploadUserAvatar(userId, buffer);

        return true;
    }
}
