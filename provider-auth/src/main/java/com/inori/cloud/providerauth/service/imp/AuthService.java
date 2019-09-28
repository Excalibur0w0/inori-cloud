package com.inori.cloud.providerauth.service.imp;

import com.inori.cloud.providerauth.client.AuthServiceClient;
import com.inori.cloud.providerauth.dto.UserLoginDTO;
import com.inori.cloud.providerauth.pojo.JWT;
import com.inori.cloud.providerauth.pojo.TblRole;
import com.inori.cloud.providerauth.pojo.TblUser;
import com.inori.cloud.providerauth.service.UserService;
import com.inori.cloud.providerauth.util.BPwdEncoderUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class AuthService {
    @Autowired
    private UserService authService;
    @Autowired
    private AuthServiceClient client;

    public boolean register(TblUser user) {
        return authService.addUser(user);
    }

    public List<TblRole> getRolesByUsername(String username) {
        TblUser user = authService.getUserByUsername(username);

        return authService.getRolesByUser(user);
    }

    public UserLoginDTO login(String username, String pwd) {
        TblUser user = authService.getUserByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户名不存在");
        }
        if (!BPwdEncoderUtil.matches(pwd, user.getUpass())) {
            throw new RuntimeException("debug: 用户密码不正确! 密码: " + pwd +" 密码: " + user.getUpass());
        }
        // 客户端 provider-auth:123456 的base64缩写
        JWT jwt = client.getToken("Basic cHJvdmlkZXItYXV0aDoxMjM0NTY=", "password", username, pwd);
        if (jwt == null) {
            throw new RuntimeException("用户token有问题");
        }
        UserLoginDTO dto = new UserLoginDTO();
        dto.setJwt(jwt);
        dto.setUser(user);

        return dto;
    }
}
