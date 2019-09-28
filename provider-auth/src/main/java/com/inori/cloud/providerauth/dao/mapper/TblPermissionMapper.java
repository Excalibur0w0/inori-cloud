package com.inori.cloud.providerauth.dao.mapper;

import com.inori.cloud.providerauth.pojo.TblPermission;
import com.inori.cloud.providerauth.pojo.TblPermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblPermissionMapper {
    int countByExample(TblPermissionExample example);

    int deleteByExample(TblPermissionExample example);

    int deleteByPrimaryKey(String uuid);

    int insert(TblPermission record);

    int insertSelective(TblPermission record);

    List<TblPermission> selectByExample(TblPermissionExample example);

    TblPermission selectByPrimaryKey(String uuid);

    int updateByExampleSelective(@Param("record") TblPermission record, @Param("example") TblPermissionExample example);

    int updateByExample(@Param("record") TblPermission record, @Param("example") TblPermissionExample example);

    int updateByPrimaryKeySelective(TblPermission record);

    int updateByPrimaryKey(TblPermission record);
}