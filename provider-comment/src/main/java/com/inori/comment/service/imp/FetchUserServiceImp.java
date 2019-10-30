package com.inori.comment.service.imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.inori.comment.client.UserServiceClient;
import com.inori.comment.service.FetchUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class FetchUserServiceImp implements FetchUserService {
    @Autowired
    private UserServiceClient client;

    @Override
    public String getUserIdByToken(String auth) {
        return JSON.parseObject(client.getUserInfoByToken(auth)).get("uuid").toString();
    }

    @Override
    public JSONObject getUserInfo(String auth, String userId) {
        try {
            return JSON.parseObject(client.getUserBasicInfo(auth, userId).queue().get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Future<String> getUserInfoAsync(String auth, String userId) {
        return client.getUserBasicInfo(auth, userId).queue();
    }

}
