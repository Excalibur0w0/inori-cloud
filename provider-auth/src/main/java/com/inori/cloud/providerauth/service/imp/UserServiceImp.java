package com.inori.cloud.providerauth.service.imp;

import com.inori.cloud.providerauth.dao.mapper.*;
import com.inori.cloud.providerauth.dto.UserBasicDTO;
import com.inori.cloud.providerauth.pojo.*;
import com.inori.cloud.providerauth.service.UserService;
import com.inori.cloud.providerauth.util.BPwdEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImp implements UserService {
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

    @Override
    public boolean addUser(TblUser user) {
        String newPass = BPwdEncoderUtil.BCryptPassword(user.getUpass().trim());
        user.setUpass(newPass);
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
    public UserBasicDTO getUserBasicInfo(String userId) {
        TblUser user = this.getUserById(userId);
        UserBasicDTO dto = new UserBasicDTO();

        dto.setAge(user.getAge());
        dto.setCity(user.getCity());
        dto.setUname(user.getUname());
        dto.setUuid(user.getUuid());

        return dto;
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
    public boolean addRelationBetweenRoleAndUser(String roleId, String userId) {
        TblRoleUser roleUser = new TblRoleUser();
        roleUser.setUserId(userId);
        roleUser.setRoleId(roleId);
        roleUser.setUuid(UUID.randomUUID().toString());

        TblRoleUserExample example = new TblRoleUserExample();
        example.createCriteria().andRoleIdEqualTo(roleId).andUserIdEqualTo(userId);

        if (tblRoleUserMapper.countByExample(example) > 0) {
            throw new RuntimeException("the relation `roleUser` " + userId + " " + roleId + " also existed");
        }

        return tblRoleUserMapper.insert(roleUser) > 0;
    }

    @Override
    public boolean deleteRelationBetweenRoleAndUser(String roleId, String userId) {
        if (this.hasRelationBetweenRoleAndUser(roleId, userId)) {
            TblRoleUserExample example = new TblRoleUserExample();
            example.createCriteria().andUserIdEqualTo(userId).andRoleIdEqualTo(roleId);

            return tblRoleUserMapper.deleteByExample(example) > 0;
        }
        return true;
    }

    @Override
    public boolean hasRelationBetweenRoleAndUser(String roleId, String userId) {
        TblRoleUserExample example = new TblRoleUserExample();
        example.createCriteria().andUserIdEqualTo(userId).andRoleIdEqualTo(roleId);

        return tblRoleUserMapper.selectByExample(example).size() > 0;
    }

}
