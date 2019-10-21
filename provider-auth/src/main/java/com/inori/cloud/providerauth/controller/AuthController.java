package com.inori.cloud.providerauth.controller;

import com.inori.cloud.providerauth.dto.UserBasicDTO;
import com.inori.cloud.providerauth.dto.UserLoginDTO;
import com.inori.cloud.providerauth.pojo.TblRole;
import com.inori.cloud.providerauth.pojo.TblUser;
import com.inori.cloud.providerauth.service.UserService;
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
    private UserService userService;

    @GetMapping("auth")
    public String auth() {
        return "auth desu";
    }

    @PostMapping("register")
    public boolean register(@RequestParam("username") String username,
                            @RequestParam("password") String pwd) {
        TblUser user = new TblUser();
        user.setUpass(pwd);
        user.setUname(username);
        return authService.register(user);
    }

    @PostMapping("login")
    public UserLoginDTO login(@RequestParam("username") String username,
                              @RequestParam("password") String password) {
        return authService.login(username, password);
    }

    @GetMapping("roles")
    public List<TblRole> authorities(@RequestParam("username") String username) {
        // 获取角色的权限
        return authService.getRolesByUsername(username);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'PERMIT_ALL')")
    @PostMapping("giveRoleToUser")
    public boolean giveRoleToUser(@RequestParam("role_code") String roleCode,
                                  @RequestHeader("Authorization") String token) {
        TblUser user = authService.getUserByToken(token);
        if (user != null) {
            return authService.giveRoleToUser(roleCode, user.getUuid());
        } else {
            throw new RuntimeException("用户签名无效，清重新登陆或申请!");
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'PERMIT_ALL')")
    @PostMapping("revokeRoleFromUser")
    public boolean revokeRoleFromUser(@RequestParam("role_code") String roleCode,
                                      @RequestHeader("Authorization") String token) {
        TblUser user = authService.getUserByToken(token);
        if (user != null) {
            return authService.revokeRoleFromUser(roleCode, user.getUuid());
        } else {
            throw new RuntimeException("用户签名无效，清重新登陆或申请!");
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'PERMIT_ALL')")
    @PostMapping("givePermissionToRole")
    public boolean givePermisisonToRole(@RequestParam("permission_code") String permissionCode,
                                        @RequestParam("role_code") String roleCode) {
        return authService.givePermissionToRole(roleCode, permissionCode);
    }

    @PreAuthorize("hasAngAuthority('ROLE_ADMIN', 'PERMIT_ALL')")
    @PostMapping("revokePermissionFromRole")
    public boolean revokePermissionFromRole(@RequestParam("permission_code") String permissionCode,
                                            @RequestParam("role_code") String roleCode) {
        return authService.revokePermissionFromRole(roleCode, permissionCode);
    }

    @GetMapping("getUserInfoByToken")
    public TblUser getUserInfoByToken(@RequestHeader("Authorization") String token) {
        if (token == null || token.length() <= 0) {
            throw new RuntimeException("token cannot be empty");
        }

        return authService.getUserByToken(token);
    }
}
