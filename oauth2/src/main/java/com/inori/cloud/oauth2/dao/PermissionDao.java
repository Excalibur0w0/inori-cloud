package com.inori.cloud.oauth2.dao;

import com.inori.cloud.oauth2.pojo.TblPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionDao extends JpaRepository<TblPermission, String>  {
}
