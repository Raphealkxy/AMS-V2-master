package com.eric.modules.sys.dao;

import com.eric.common.utils.BaseDao;
import com.eric.modules.sys.entity.SysUserTokenEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysUserTokenDao extends BaseDao<SysUserTokenEntity> {

}