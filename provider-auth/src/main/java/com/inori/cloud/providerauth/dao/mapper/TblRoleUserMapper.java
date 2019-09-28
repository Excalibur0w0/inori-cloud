package com.inori.cloud.providerauth.dao.mapper;

import com.inori.cloud.providerauth.pojo.TblRoleUser;
import com.inori.cloud.providerauth.pojo.TblRoleUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblRoleUserMapper {
    int countByExample(TblRoleUserExample example);

    int deleteByExample(TblRoleUserExample example);

    int deleteByPrimaryKey(String uuid);

    int insert(TblRoleUser record);

    int insertSelective(TblRoleUser record);

    List<TblRoleUser> selectByExample(TblRoleUserExample example);

    TblRoleUser selectByPrimaryKey(String uuid);

    int updateByExampleSelective(@Param("record") TblRoleUser record, @Param("example") TblRoleUserExample example);

    int updateByExample(@Param("record") TblRoleUser record, @Param("example") TblRoleUserExample example);

    int updateByPrimaryKeySelective(TblRoleUser record);

    int updateByPrimaryKey(TblRoleUser record);
}