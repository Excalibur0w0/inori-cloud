package com.inori.cloud.providerauth.dao.mapper;

import com.inori.cloud.providerauth.pojo.TblUser;
import com.inori.cloud.providerauth.pojo.TblUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblUserMapper {
    int countByExample(TblUserExample example);

    int deleteByExample(TblUserExample example);

    int deleteByPrimaryKey(String uuid);

    int insert(TblUser record);

    int insertSelective(TblUser record);

    List<TblUser> selectByExample(TblUserExample example);

    TblUser selectByPrimaryKey(String uuid);

    int updateByExampleSelective(@Param("record") TblUser record, @Param("example") TblUserExample example);

    int updateByExample(@Param("record") TblUser record, @Param("example") TblUserExample example);

    int updateByPrimaryKeySelective(TblUser record);

    int updateByPrimaryKey(TblUser record);
}