package com.inori.cloud.providerauth.service;

import com.inori.cloud.providerauth.pojo.TblUser;

import java.util.List;

public interface AuthService {
    void addUser(TblUser user);
    void deleteUser(String userId);
    void updateUser(String userId, TblUser newUser);
    TblUser getUserById(String uuid);
    TblUser getUserByUsername(String username);
    List<TblUser> getAllUsers();
    List<TblUser> getUsersLimit(int page, int perPage);
    boolean checkUserLogin(TblUser user);
}
