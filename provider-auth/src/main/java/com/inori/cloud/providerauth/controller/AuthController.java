package com.inori.cloud.providerauth.controller;

import com.inori.cloud.providerauth.dto.UserLoginDTO;
import com.inori.cloud.providerauth.pojo.TblRole;
import com.inori.cloud.providerauth.pojo.TblUser;
import com.inori.cloud.providerauth.service.imp.AuthService;
import com.netflix.client.http.HttpHeaders;
import com.netflix.client.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController()
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private HttpServletRequest request;

    @GetMapping("auth")
    public String auth() {
        return "auth desu";
    }

    @PostMapping("register")
    public boolean register(@RequestParam("username")String username,
                            @RequestParam("password")String pwd) {
        TblUser user = new TblUser();
        user.setUpass(pwd);
        user.setUname(username);
        return authService.register(user);
    }

    @PostMapping("login")
    public UserLoginDTO login(@RequestParam("username")String username,
                              @RequestParam("password")String password) {
        return authService.login(username, password);
    }

    @GetMapping("roles")
    public List<TblRole> authorities(@RequestParam("username")String username) {
        return authService.getRolesByUsername(username);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("giveRoleToUser")
    public boolean giveRoleToUser() {
        return false;
    }

    @GetMapping("getUserInfoByToken")
    public TblUser getUserInfoByToken() {
        String token = request.getHeader("Authorization");

        if (token == null || token.length() <= 0) {
            throw new RuntimeException("token cannot be empty");
        }

        return authService.getUserByToken(token);
    }


}
