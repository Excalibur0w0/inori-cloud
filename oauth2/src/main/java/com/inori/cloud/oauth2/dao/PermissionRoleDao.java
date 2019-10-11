package com.inori.cloud.oauth2.dao;

import com.inori.cloud.oauth2.pojo.TblPermissionRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRoleDao extends JpaRepository<TblPermissionRole, String> {
}
