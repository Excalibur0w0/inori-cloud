package com.inori.cloud.providerauth.service.imp;

import com.inori.cloud.providerauth.dao.hbase.HImageDao;
import com.inori.cloud.providerauth.dao.mapper.*;
import com.inori.cloud.providerauth.dto.UserBasicDTO;
import com.inori.cloud.providerauth.pojo.*;
import com.inori.cloud.providerauth.service.UserService;
import com.inori.cloud.providerauth.util.BPwdEncoderUtil;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.Date;
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
    @Autowired
    private HImageDao hImageDao;

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

    @Transactional
    @Override
    public boolean updateUser(String userId, TblUser newUser) {
        TblUser oldUser = tblUserMapper.selectByPrimaryKey(userId);

        if (StringUtils.isEmpty(newUser.getUname())) {
            newUser.setUname(oldUser.getUname());
        }
        if (StringUtils.isEmpty(newUser.getDescription())) {
            newUser.setDescription(oldUser.getDescription());
        }
        if (newUser.getBirthday() == null) {
            newUser.setBirthday(oldUser.getBirthday());
        }
        if (StringUtils.isEmpty(newUser.getDescription())) {
            newUser.setGender(oldUser.getGender());
        }
        if (StringUtils.isEmpty(newUser.getCity())) {
            newUser.setCity(oldUser.getCity());
        }
        if (StringUtils.isEmpty(newUser.getEmail())) {
            newUser.setEmail(oldUser.getEmail());
        }
        if (StringUtils.isEmpty(newUser.getAvatar())) {
            newUser.setAvatar(oldUser.getAvatar());
        }
        if (StringUtils.isEmpty(newUser.getGender())) {
            newUser.setGender(oldUser.getGender());
        }
        if (StringUtils.isEmpty(newUser.getUpass())) {
            newUser.setUpass(oldUser.getUpass());
        }

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

        dto.setBirthday(user.getBirthday());
        dto.setDesc(user.getDescription());
        dto.setCity(user.getCity());
        dto.setUname(user.getUname());
        dto.setUuid(user.getUuid());
        if (StringUtils.isEmpty(user.getAvatar()) || user.getAvatar().trim().length() < 1) {
            dto.setAvatar(user.getUuid());
        } else {
            dto.setAvatar(user.getAvatar());
        }

        return dto;
    }

    @Override
    public boolean uploadUserAvatar(String userId, byte[] buff) {
        FileImg img = new FileImg();
        img.setData(buff);
        img.setFilename(userId);
        img.setFiletype("png");
        hImageDao.insert(img);
        return true;
    }

    @Override
    public FileImg getAvatarImg(String imgPath) {
        if (StringUtils.isEmpty(imgPath)) {
            throw new RuntimeException("imgPath could not be null");
        }
        return  hImageDao.getById(imgPath);
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

    @Transactional
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
