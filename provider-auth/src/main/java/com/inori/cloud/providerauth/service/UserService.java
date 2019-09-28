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
    List<TblUser> getAllUsers();
    List<TblUser> getUsersLimit(int page, int perPage);
    boolean checkUserLogin(TblUser user);
//    TblUser login(String username, String pwd);
    List<TblRole> getRolesByUser(TblUser user);
    List<TblPermission> getPermissionByRole(TblRole role);
}
