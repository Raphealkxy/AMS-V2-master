package com.eric.modules.ams.dao;

import com.eric.common.utils.BaseDao;
import com.eric.common.utils.Res;
import com.eric.modules.ams.entity.AmsAttdetallEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AmsAttdetallDao extends BaseDao<AmsAttdetallEntity> {

    List<Long> selectAttlistIdsByLoginNum(@Param("loginNum") String loginNum);
}