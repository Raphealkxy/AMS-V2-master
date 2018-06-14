package com.eric.modules.sys.dao;

import com.eric.common.utils.BaseDao;
import com.eric.modules.sys.entity.SysConfigEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysConfigDao extends BaseDao<SysConfigEntity> {
}