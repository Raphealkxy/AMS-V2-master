package com.eric.modules.ams.dao;


import com.eric.common.utils.BaseDao;
import com.eric.modules.ams.entity.AmsFacesetEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AmsFacesetDao extends BaseDao<AmsFacesetEntity> {
    /**
     * 删除所有
     */
    void deleteAll();

    void deleteBatch(@Param("ids") List<Long> ids);
}