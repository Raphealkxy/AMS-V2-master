package com.eric.modules.sys.dao;

import com.eric.common.utils.BaseDao;
import com.eric.modules.sys.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SysMenuDao extends BaseDao<SysMenuEntity> {
    /**
     * 根据父菜单，获取菜单
     */
    List<SysMenuEntity> queryListParentId(Long parentId);

    /**
     * 获取菜单
     */
    List<SysMenuEntity> queryList();

    /**
     * 获取不包含按钮的菜单列表
     */
    List<SysMenuEntity> queryNotButtonList();

    /**
     * 批量删除
     */
    void deleteBatch(Long[] menuIds);
}