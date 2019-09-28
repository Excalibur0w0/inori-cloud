package com.inori.cloud.providerauth.controller;

import com.inori.cloud.providerauth.dto.UserLoginDTO;
import com.inori.cloud.providerauth.pojo.TblRole;
import com.inori.cloud.providerauth.pojo.TblUser;
import com.inori.cloud.providerauth.service.imp.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private AuthService userDetailsService;

    @GetMapping("auth")
    @ResponseBody
    public String auth() {
        return "auth desu";
    }

    @PostMapping("register")
    @ResponseBody
    public boolean register(@RequestParam("username")String username,
                            @RequestParam("password")String pwd) {
        TblUser user = new TblUser();
        user.setUpass(pwd);
        user.setUname(username);
        return userDetailsService.register(user);
    }

    @PostMapping("login")
    @ResponseBody
    public UserLoginDTO login(@RequestParam("username")String username,
                              @RequestParam("password")String password) {
        return userDetailsService.login(username, password);
    }

    @GetMapping("roles")
    @ResponseBody
    public List<TblRole> authorities(@RequestParam("username")String username) {
        return userDetailsService.getRolesByUsername(username);
    }
}
