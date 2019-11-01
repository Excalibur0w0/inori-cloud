package com.inori.cloud.providerauth.controller;

import com.inori.cloud.providerauth.client.AuthServiceClient;
import com.inori.cloud.providerauth.dto.UserBasicDTO;
import com.inori.cloud.providerauth.pojo.TblUser;
import com.inori.cloud.providerauth.service.UserService;
import com.inori.cloud.providerauth.service.imp.AuthService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;


    @PutMapping
    public TblUser updateUser(@RequestHeader("Authorization")String authorization,
                              @RequestParam(value = "uname", required = false) String uname,
                              @RequestParam(value = "description", required = false) String description,
                              @RequestParam(value = "birthday", required = false)Long birthday,
                              @RequestParam(value = "gender", required = false)String gender,
                              @RequestParam(value = "city", required = false)String city) {
        TblUser user = new TblUser();
        if (!StringUtils.isEmpty(uname)) {
            user.setUname(uname);
        }
        if (!StringUtils.isEmpty(description)) {
            user.setDescription(description);
        }
        if (birthday != null && birthday != 0L) {
            user.setBirthday(new Date(birthday));
        }
        if (!StringUtils.isEmpty(description)) {
            user.setGender(gender);
        }
        if (!StringUtils.isEmpty(city)) {
            user.setCity(city);
        }
        TblUser oldUser = authService.getUserByToken(authorization);
        if (oldUser == null) {
            throw new RuntimeException("该token已经失效，请重新登陆");
        }

        if (userService.updateUser(oldUser.getUuid(), user)) {
            return userService.getUserById(oldUser.getUuid());
        } else {
            return null;
        }

    }


    @GetMapping("getUserBasicInfo")
    public UserBasicDTO getUserBasicInfo(@RequestParam("userId")String userId) {
        return userService.getUserBasicInfo(userId);
    }

}
