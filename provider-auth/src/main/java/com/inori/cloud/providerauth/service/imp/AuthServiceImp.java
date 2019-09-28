package com.inori.cloud.providerauth.service.imp;

import com.inori.cloud.providerauth.dao.mapper.*;
import com.inori.cloud.providerauth.pojo.*;
import com.inori.cloud.providerauth.service.AuthService;
import com.inori.cloud.providerauth.util.BPwdEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class AuthServiceImp implements AuthService {
    @Autowired
    private TblUserMapper tblUserMapper;
    @Autowired
    private TblRoleUserMapper tblRoleUserMapper;
    @Autowired
    private TblRoleMapper tblRoleMapper;
    @Autowired
    private TblPermissionMapper tblPermissionMapper;
    @Autowired
    private TblPermissionRoleMapper tblPermissionRoleMapper;
    @Autowired
    private InoriUserDetailsService client;

    @Override
    public boolean addUser(TblUser user) {
        String newPass = BPwdEncoderUtil.BCryptPassword(user.getUpass().trim());
        user.setUpass(newPass);
        user.setUuid(UUID.randomUUID().toString());
        return tblUserMapper.insert(user) > 0;
    }

    @Override
    public boolean deleteUser(String userId) {
        return tblUserMapper.deleteByPrimaryKey(userId) > 0;
    }

    @Override
    public boolean updateUser(String userId, TblUser newUser) {
        newUser.setUuid(userId);
        return tblUserMapper.updateByPrimaryKey(newUser) > 0;
    }

    @Override
    public TblUser getUserById(String uuid) {
        return tblUserMapper.selectByPrimaryKey(uuid);
    }

    @Override
    public TblUser getUserByUsername(String username) {
        TblUserExample userExample = new TblUserExample();
        userExample.createCriteria().andUnameEqualTo(username);
        List<TblUser> resultList = tblUserMapper.selectByExample(userExample);

        if (resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<TblUser> getAllUsers() {
        TblUserExample tblUserExample = new TblUserExample();
        return tblUserMapper.selectByExample(tblUserExample);
    }

    @Override
    public List<TblUser> getUsersLimit(int page, int perPage) {
        return null;
    }

    @Override
    public boolean checkUserLogin(TblUser user) {
        return false;
    }

    @Override
    public List<TblRole> getRolesByUser(TblUser user) {
        TblRoleUserExample example = new TblRoleUserExample();
        example.createCriteria().andUserIdEqualTo(user.getUuid());
        List<TblRoleUser> roleUsers = tblRoleUserMapper.selectByExample(example);
        List<TblRole> result = new LinkedList<>();

        for (TblRoleUser ru :
                roleUsers) {
            TblRole role = tblRoleMapper.selectByPrimaryKey(ru.getRoleId());
            if (role != null) {
                result.add(role);
            }
        }

        return result;
    }

    @Override
    public List<TblPermission> getPermissionByRole(TblRole role) {
        TblPermissionRoleExample example = new TblPermissionRoleExample();
        example.createCriteria().andRoleIdEqualTo(role.getUuid());
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
}
