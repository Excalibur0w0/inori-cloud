package com.inori.cloud.providerauth.service;

import com.inori.cloud.providerauth.pojo.TblPermission;

import java.util.List;

public interface PermissionService {
    boolean addPermission(TblPermission permission);
    boolean delPermission(String permissionId);
    boolean updatePermission(TblPermission permission, String permissionId);
    List<TblPermission> getAllPermissions();
}
