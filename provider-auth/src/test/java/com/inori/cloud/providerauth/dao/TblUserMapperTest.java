package com.inori.cloud.providerauth.dao;

import com.inori.cloud.providerauth.dao.mapper.TblUserMapper;
import com.inori.cloud.providerauth.pojo.TblUser;
import com.inori.cloud.providerauth.pojo.TblUserExample;
import com.inori.cloud.providerauth.service.UserService;
import com.netflix.discovery.converters.Auto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TblUserMapperTest {
    @Autowired
    private TblUserMapper tblUserMapper;
    @Autowired
    private UserService userService;

    @Test
    public void insertTest() {
        TblUser user = new TblUser();
        user.setUname("inori");
        user.setUuid(UUID.randomUUID().toString());

        tblUserMapper.insert(user);
    }

    @Test
    public void updateTest() {
        TblUser newUser = new TblUser();
        newUser.setCity("天津");
        TblUserExample example = new TblUserExample();
        example.createCriteria().andUnameEqualTo("test");
        TblUser oldUser = tblUserMapper.selectByExample(example).get(0);
        newUser.setUuid(oldUser.getUuid());

        userService.updateUser(newUser.getUuid(), newUser);
    }

    @Test
    public void deleteTest() {

        tblUserMapper.deleteByPrimaryKey("32");
    }

    @Test
    public void getAll() {
        List<TblUser> resultList = tblUserMapper.selectByExample(new TblUserExample());

        for (TblUser user : resultList) {
            System.out.println(user.getUname());
        }
    }
}
