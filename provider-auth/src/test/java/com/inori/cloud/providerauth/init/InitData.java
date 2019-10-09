package com.inori.cloud.providerauth.init;

import com.inori.cloud.providerauth.controller.AuthController;
import com.inori.cloud.providerauth.pojo.TblUser;
import com.inori.cloud.providerauth.service.UserService;
import com.inori.cloud.providerauth.service.imp.AuthService;
import com.netflix.discovery.converters.Auto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
public class InitData {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @Test
    public void addUser() {
        TblUser user = new TblUser();
        user.setAge(22);
        user.setUname("admin");
        user.setUpass("admin");
        user.setUuid(UUID.randomUUID().toString());

        userService.addUser(user);
    }

}
