package com.inori.cloud.providerauth.redis;

import com.inori.cloud.providerauth.pojo.TblUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class CacheUserToken implements CacheStorage<TblUser> {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void set(String token, TblUser user) {
        redisTemplate.opsForValue().set(token, user);
    }

    @Override
    public TblUser get(String token) {
        Object valObj = redisTemplate.opsForValue().get(token);

        if (valObj == null) {
            throw new RuntimeException("token: " + token + "对应的对象在缓存中不存在");
        }
        if (valObj.getClass() != TblUser.class) {
            throw new RuntimeException("缓存中取出的对象反序列化后并非TblUser！");
        }

        return (TblUser)valObj;
    }
}
