package com.inori.cloud.providerauth.redis;

import com.inori.cloud.providerauth.pojo.TblUser;

public interface CachUserToken {
    void set(String token, TblUser user);
    TblUser get(String token);
}
