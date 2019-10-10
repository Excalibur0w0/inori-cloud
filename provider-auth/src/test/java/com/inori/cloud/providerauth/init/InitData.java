package com.inori.cloud.providerauth.init;

import com.inori.cloud.providerauth.controller.AuthController;
import com.inori.cloud.providerauth.pojo.TblPermission;
import com.inori.cloud.providerauth.pojo.TblRole;
import com.inori.cloud.providerauth.pojo.TblRoleUser;
import com.inori.cloud.providerauth.pojo.TblUser;
import com.inori.cloud.providerauth.service.PermissionService;
import com.inori.cloud.providerauth.service.RoleService;
import com.inori.cloud.providerauth.service.UserService;
import com.inori.cloud.providerauth.service.imp.AuthService;
import com.netflix.discovery.converters.Auto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
public class InitData {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;


    @Test
    @Transactional
    public void addUser() {
        TblUser user = new TblUser();
        TblRole role = new TblRole();
        TblRoleUser tblRoleUser = new TblRoleUser();

        user.setAge(22);
        user.setUname("admin");
        user.setUpass("admin");
        user.setUuid(UUID.randomUUID().toString());

        role.setRoleCode("ADMIN");
        role.setRoleName("系统管理员");
        role.setUuid(UUID.randomUUID().toString());

        // 添加系统管理员
        tblRoleUser.setRoleId(role.getUuid());
        tblRoleUser.setUserId(user.getUuid());
        tblRoleUser.setUuid(UUID.randomUUID().toString());

        TblUser tmpUser = userService.getUserByUsername(user.getUname());
        if (tmpUser != null) {
            userService.deleteUser(tmpUser.getUuid());
        }
        TblRole tmpRole = roleService.getRoleByRoleCode(role.getRoleCode());
        if (tmpRole != null) {
            roleService.deleteRole(tmpRole.getUuid());
        }
        if (userService.hasRelationBetweenRoleAndUser(tmpUser.getUuid(), tmpRole.getUuid())) {
            userService.deleteRelationBetweenRoleAndUser(tmpUser.getUuid(), tmpRole.getUuid());
        }

        userService.addUser(user);
        roleService.addRole(role);
        userService.addRelationBetweenRoleAndUser(role.getUuid(), user.getUuid());
    }

    @Test
    public void addPermission() {

    }

    @Test
    public void addRole() {

    }
}
