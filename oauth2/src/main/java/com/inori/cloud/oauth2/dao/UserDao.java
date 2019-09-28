package com.inori.cloud.oauth2.dao;


import com.inori.cloud.oauth2.pojo.TblUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<TblUser, Long> {
    TblUser findByUname(String username);
}