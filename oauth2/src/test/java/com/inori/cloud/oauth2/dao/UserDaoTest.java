package com.inori.cloud.oauth2.dao;

import com.inori.cloud.oauth2.pojo.TblRoleUser;
import com.inori.cloud.oauth2.pojo.TblUser;
//import org.hibernate.criterion.Example;
import org.springframework.data.domain.Example;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserDaoTest {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RoleUserDao roleUserDao;

    @Test
    public void test() {
        TblUser user = new TblUser();
        user.setUname("testEncodePass");
        user.setUpass(new BCryptPasswordEncoder().encode("123456"));
        user.setUuid(UUID.randomUUID().toString());

        userDao.save(user);
    }

    @Test
    public void testSelect() {
        TblUser user = new TblUser();
        user.setUuid("ef69d231-8a5c-4cf3-8375-3572a0d760ae");
        Example<TblUser> example = Example.of(user);
        TblUser res = userDao.findOne(example).get();

        System.out.println(res);
    }
}
