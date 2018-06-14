package com.eric.modules.ams.dao;

import com.eric.common.utils.BaseDao;
import com.eric.modules.ams.entity.AmsAttlistEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;

@Mapper
@Repository
public interface AmsAttlistDao extends BaseDao<AmsAttlistEntity> {
}