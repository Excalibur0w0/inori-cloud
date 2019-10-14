package com.inori.cloud.providerauth.service.imp;

import com.inori.cloud.providerauth.dao.mapper.TblPermissionMapper;
import com.inori.cloud.providerauth.dao.mapper.TblPermissionRoleMapper;
import com.inori.cloud.providerauth.dao.mapper.TblRoleMapper;
import com.inori.cloud.providerauth.dao.mapper.TblRoleUserMapper;
import com.inori.cloud.providerauth.pojo.*;
import com.inori.cloud.providerauth.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private TblRoleMapper tblRoleMapper;
    @Autowired
    private TblPermissionRoleMapper tblPermissionRoleMapper;
    @Autowired
    private TblPermissionMapper tblPermissionMapper;

    @Override
    public boolean addRole(TblRole role) {
        return tblRoleMapper.insert(role) > 0;
    }

    @Override
    public boolean deleteRole(String roleId) {
        return tblRoleMapper.deleteByPrimaryKey(roleId) > 0;
    }

    @Override
    public boolean updateRole(TblRole role, String roleId) {
        TblRoleExample example = new TblRoleExample();
        example.createCriteria().andUuidEqualTo(roleId);

        return tblRoleMapper.updateByExample(role, example) > 0;
    }

    @Override
    public List<TblPermission> getPermissionsByRoleId(String roleId) {
        TblPermissionRoleExample example = new TblPermissionRoleExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        List<TblPermissionRole> permissionRoles = tblPermissionRoleMapper.selectByExample(example);
        List<TblPermission> result = new LinkedList<>();

        for (TblPermissionRole pr :
                permissionRoles) {
            TblPermission permission = tblPermissionMapper.selectByPrimaryKey(pr.getPermissionId());

            if (permission != null) {
                result.add(permission);
            }
        }

        return result;
    }

    @Override
    public List<TblRole> getAllRoles() {
        TblRoleExample example = new TblRoleExample();
        return tblRoleMapper.selectByExample(example);
    }

    @Override
    public TblRole getRolebyId(String roleId) {
        return tblRoleMapper.selectByPrimaryKey(roleId);
    }

    @Override
    public TblRole getRoleByRoleCode(String roleCode) {
        TblRoleExample example = new TblRoleExample();
        example.createCriteria().andRoleCodeEqualTo(roleCode);

        List<TblRole> resultSet = tblRoleMapper.selectByExample(example);
        if (resultSet != null && resultSet.size() > 0) {
            return resultSet.get(0);
        } else {
            return null;
        }
    }

    @Override
    public boolean addRelationBetweenRoleAndPermission(String roleId, String permissionId) {
        TblPermissionRole permissionRole = new TblPermissionRole();

        permissionRole.setPermissionId(permissionId);
        permissionRole.setRoleId(roleId);

        return tblPermissionRoleMapper.insert(permissionRole) > 0;
    }

    @Override
    public boolean deleteRelationBetweenRoleAndPermission(String roleId, String permissionId) {
        TblPermissionRoleExample example = new TblPermissionRoleExample();
        example.createCriteria().andRoleIdEqualTo(roleId).andPermissionIdEqualTo(permissionId);

        return tblPermissionRoleMapper.deleteByExample(example) > 0;
    }
}
