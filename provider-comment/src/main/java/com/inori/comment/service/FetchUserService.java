package com.inori.comment.service;

import com.alibaba.fastjson.JSONObject;

import java.util.concurrent.Future;

public interface FetchUserService {
    String getUserIdByToken(String auth);

    JSONObject getUserInfo(String auth, String userId);

    Future<String> getUserInfoAsync(String auth, String userId);
}
