package com.eric.modules.sys.dao;

import com.eric.common.utils.BaseDao;
import com.eric.modules.sys.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface SysRoleDao extends BaseDao<SysRoleEntity> {
    void update(SysRoleEntity role);

    void deleteBatch(Long[] roleIds);

    List<SysRoleEntity> queryList(Map<String, Object> map);
}