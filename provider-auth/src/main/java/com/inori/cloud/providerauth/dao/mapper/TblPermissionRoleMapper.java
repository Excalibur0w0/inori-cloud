package com.inori.cloud.providerauth.dao.mapper;

import com.inori.cloud.providerauth.pojo.TblPermissionRole;
import com.inori.cloud.providerauth.pojo.TblPermissionRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblPermissionRoleMapper {
    int countByExample(TblPermissionRoleExample example);

    int deleteByExample(TblPermissionRoleExample example);

    int deleteByPrimaryKey(String uuid);

    int insert(TblPermissionRole record);

    int insertSelective(TblPermissionRole record);

    List<TblPermissionRole> selectByExample(TblPermissionRoleExample example);

    TblPermissionRole selectByPrimaryKey(String uuid);

    int updateByExampleSelective(@Param("record") TblPermissionRole record, @Param("example") TblPermissionRoleExample example);

    int updateByExample(@Param("record") TblPermissionRole record, @Param("example") TblPermissionRoleExample example);

    int updateByPrimaryKeySelective(TblPermissionRole record);

    int updateByPrimaryKey(TblPermissionRole record);
}