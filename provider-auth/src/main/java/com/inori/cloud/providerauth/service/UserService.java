package com.inori.cloud.providerauth.service;

import com.inori.cloud.providerauth.dto.UserBasicDTO;
import com.inori.cloud.providerauth.pojo.FileImg;
import com.inori.cloud.providerauth.pojo.TblPermission;
import com.inori.cloud.providerauth.pojo.TblRole;
import com.inori.cloud.providerauth.pojo.TblUser;

import java.io.InputStream;
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
    UserBasicDTO getUserBasicInfo(String userId);
    boolean uploadUserAvatar(String userId, byte[] img);
    FileImg getAvatarImg(String imgPath);

}
