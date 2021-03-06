package com.inori.cloud.providerauth.service.imp;

import com.inori.cloud.providerauth.client.AuthServiceClient;
import com.inori.cloud.providerauth.dto.UserLoginDTO;
import com.inori.cloud.providerauth.pojo.*;
import com.inori.cloud.providerauth.redis.CacheUserToken;
import com.inori.cloud.providerauth.service.PermissionService;
import com.inori.cloud.providerauth.service.RoleService;
import com.inori.cloud.providerauth.service.UserService;
import com.inori.cloud.providerauth.util.BPwdEncoderUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Log4j2
@Service
public class AuthService {
    @Autowired
    private AuthServiceClient client;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private CacheUserToken cacheUserToken;

    @Transactional
    public boolean register(TblUser user) {
        // 如果用户名存在，不允许注册
        TblUser tmpUser = userService.getUserByUsername(user.getUname());
        if (tmpUser != null) {
            throw new RuntimeException("用户名已经存在");
        }
        // 添加uuid
        user.setUuid(UUID.randomUUID().toString());
        boolean isSuccess = userService.addUser(user);
        if (isSuccess) {
            // 如果成功，为此用户添加普通用户角色.
            TblRole ordRole = roleService.getRoleByRoleCode("ORD_USER");
            if (ordRole == null) {
                throw new RuntimeException("注册用户时失败！原因为数据库中不存在名为ORD_USER的角色！");
            }
            isSuccess = userService.addRelationBetweenRoleAndUser(ordRole.getUuid(), user.getUuid());
        }

        return isSuccess;
    }

    public List<TblRole> getRolesByUsername(String username) {
        TblUser user = userService.getUserByUsername(username);

        return userService.getRolesByUser(user);
    }

    public UserLoginDTO login(String username, String pwd) {
        TblUser user = userService.getUserByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户名不存在");
        }
        if (!BPwdEncoderUtil.matches(pwd, user.getUpass())) {
            throw new RuntimeException("debug: 用户密码不正确! 密码: " + pwd +" 密码: " + user.getUpass());
        }
        // 客户端 provider-auth:123456 的base64缩写
        JWT jwt = client.getToken("Basic cHJvdmlkZXItYXV0aDoxMjM0NTY=", "password", username, pwd);
        log.warn(username + " " + pwd + " token: " + jwt);
        if (jwt == null) {
            throw new RuntimeException("用户token有问题");
        }
        UserLoginDTO dto = new UserLoginDTO();
        dto.setJwt(jwt);
        dto.setUser(user);

        System.out.println(jwt.getAccess_token());
        // 此处需要写缓存，否则之后操作无法获取用户信息
        cacheUserToken.set(jwt.getAccess_token(), user);

        return dto;
    }

    public TblUser getUserByToken(String token) {
        // 因为传递的token 是以 Bearer[空格]打头的，所以在取出token 的时候先截取掉前7位。
        return cacheUserToken.get(token.substring(7));
    }

    public TblUser getUserByTokenAndRefresh(String token) {
        TblUser usr = this.getUserByToken(token);
        if (usr != null) {
            return userService.getUserById(usr.getUuid());
        } else {
            return null;
        }
    }

    public boolean giveRoleToUser(String roleCode, String user_id) {
        TblRole role = roleService.getRoleByRoleCode(roleCode);

        if (role != null) {
            return userService.addRelationBetweenRoleAndUser(role.getUuid(), user_id);
        } else {
            throw new RuntimeException("不存在ROLE_CODE为" + roleCode + "的角色");
        }
    }

    // 撤销角色权限
    public boolean revokeRoleFromUser(String roleCode, String user_id) {
        TblRole role = roleService.getRoleByRoleCode(roleCode);

        if (role != null) {
            return userService.deleteRelationBetweenRoleAndUser(role.getUuid(), user_id);
        } else {
            throw new RuntimeException("不存在ROLE_CODE为" + roleCode + "的角色");
        }
    }

    public boolean givePermissionToRole(String roleCode, String permissionCode) {
        TblPermission permission = permissionService.getByPermissionCode(permissionCode);
        TblRole role = roleService.getRoleByRoleCode(roleCode);

        return roleService.addRelationBetweenRoleAndPermission(role.getUuid(), permission.getUuid());
    }

    public boolean revokePermissionFromRole(String roleCode, String permissionCode) {
        TblPermission permission = permissionService.getByPermissionCode(permissionCode);
        TblRole role = roleService.getRoleByRoleCode(roleCode);

        return roleService.deleteRelationBetweenRoleAndPermission(role.getUuid(), permission.getUuid());
    }
}
