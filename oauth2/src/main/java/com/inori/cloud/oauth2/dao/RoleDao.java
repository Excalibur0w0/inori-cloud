package com.inori.cloud.oauth2.dao;

import com.inori.cloud.oauth2.pojo.TblRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<TblRole, String> {
}
