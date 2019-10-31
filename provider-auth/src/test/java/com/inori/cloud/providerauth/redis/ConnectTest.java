package com.inori.cloud.providerauth.redis;

import com.inori.cloud.providerauth.pojo.TblUser;
import lombok.Data;
import lombok.ToString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConnectTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void test() throws Exception {
        TblUser user = new TblUser();

        user.setUuid("tttt");
        user.setUpass("pwd");
        user.setUname("this is usernaem");
        redisTemplate.opsForValue().set("test", user);
        System.out.println(redisTemplate.opsForValue().get("test").getClass());
    }

}
