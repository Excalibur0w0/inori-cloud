package com.inori.cloud.providerauth.redis;

import com.inori.cloud.providerauth.pojo.TblUser;

public interface CacheStorage<T> {
    void set(String key, T value);
    TblUser get(String key);
}
