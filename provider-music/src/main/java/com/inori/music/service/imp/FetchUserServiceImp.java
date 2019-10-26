package com.inori.music.service.imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.inori.music.client.UserServiceClient;
import com.inori.music.service.FetchUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FetchUserServiceImp implements FetchUserService {
    @Autowired
    private UserServiceClient client;

    @Override
    public String getUserId(String auth) {
        return JSON.parseObject(client.getUserInfoByToken(auth)).get("uuid").toString();
    }
}
