package com.inori.cloud.providerauth.service;

import com.inori.cloud.providerauth.pojo.TblPermission;
import com.inori.cloud.providerauth.pojo.TblRole;

import java.util.List;

public interface RoleService {
    boolean addRole(TblRole role);
    boolean deleteRole(String roleId);
    boolean updateRole(TblRole role, String roleId);
    List<TblPermission> getPermissionsByRoleId(String roleId);
    List<TblRole> getAllRoles();
    TblRole getRolebyId(String roleId);
    TblRole getRoleByRoleCode(String roleCode);
    boolean addRelationBetweenRoleAndPermission(String roleId, String permissionId);
    boolean deleteRelationBetweenRoleAndPermission(String roleId, String permissionId);
}
