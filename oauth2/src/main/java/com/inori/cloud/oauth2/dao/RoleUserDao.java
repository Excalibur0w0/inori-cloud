package com.inori.cloud.oauth2.dao;

import com.inori.cloud.oauth2.pojo.TblRole;
import com.inori.cloud.oauth2.pojo.TblRoleUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleUserDao extends JpaRepository<TblRoleUser, String> {
}
