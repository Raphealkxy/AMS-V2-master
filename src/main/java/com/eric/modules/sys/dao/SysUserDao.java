package com.eric.modules.sys.dao;

import com.eric.common.utils.BaseDao;
import com.eric.modules.sys.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 系统用户Dao
 */
@Mapper
@Repository
public interface SysUserDao extends BaseDao<SysUserEntity> {

    /**
     * 查询用户的所有权限
     */
    List<String> queryAllPerms(Long userId);

    /**
     * 根据用户id，查询该用户所有的菜单
     */
    List<Long> queryAllMenuId(Long userId);

    void deleteBatch(Long[] userId);

    int updatePassword(Map<String, Object> map);
}