package com.inori.cloud.providerauth.service;

import com.inori.cloud.providerauth.pojo.TblPermission;
import com.inori.cloud.providerauth.pojo.TblRole;
import com.inori.cloud.providerauth.pojo.TblUser;

import java.util.List;

public interface UserService {
    boolean addUser(TblUser user);
    boolean deleteUser(String userId);
    boolean updateUser(String userId, TblUser newUser);
    TblUser getUserById(String uuid);
    TblUser getUserByUsername(String username);
    List<TblRole> getRolesByUser(TblUser user);
    boolean addRelationBetweenRoleAndUser(String roleId, String userId);
    boolean deleteRelationBetweenRoleAndUser(String roleId, String userId);
    boolean hasRelationBetweenRoleAndUser(String roleId, String userId);
    List<TblUser> getAllUsers();
}
