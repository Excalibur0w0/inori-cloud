package com.inori.cloud.providerauth.service.imp;

import com.inori.cloud.providerauth.dao.mapper.TblUserMapper;
import com.inori.cloud.providerauth.pojo.TblUser;
import com.inori.cloud.providerauth.pojo.TblUserExample;
import com.inori.cloud.providerauth.service.AuthService;
import com.netflix.discovery.converters.Auto;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImp implements AuthService {
    @Autowired
    private TblUserMapper tblUserMapper;

    @Override
    public void addUser(TblUser user) {
        tblUserMapper.insert(user);
    }

    @Override
    public void deleteUser(String userId) {
        tblUserMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public void updateUser(String userId, TblUser newUser) {
        newUser.setUuid(userId);
        tblUserMapper.updateByPrimaryKey(newUser);
    }

    @Override
    public TblUser getUserById(String uuid) {
        return tblUserMapper.selectByPrimaryKey(uuid);
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
}
