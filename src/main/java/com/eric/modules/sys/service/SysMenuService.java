package com.eric.modules.sys.service;

import com.eric.modules.sys.entity.SysMenuEntity;

import java.util.List;

/**
 * @author Chen 2018/1/12
 */
public interface SysMenuService {

    /**
     * 根据父菜单，查询子菜单
     */
    List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList);

    /**
     * 获取该用户的菜单
     */
    List<SysMenuEntity> getUserMenuList(Long userId);

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    List<SysMenuEntity> queryListParentId(Long parentId);

    /**
     * 获取所有菜单
     */
    List<SysMenuEntity> queryList();

    /**
     *获取不包含按钮的菜单列表
     */
    List<SysMenuEntity> queryNotButtonList();

    /**
     * 根据ID查询
     */
    SysMenuEntity selectByMenuId(Long menuId);

    /**
     * 保存菜单
     */
    void save(SysMenuEntity menu);

    /**
     * 根据ID查询
     */
    SysMenuEntity queryByMenuId(Long parentId);

    /**
     * 更新
     */
    void update(SysMenuEntity menu);

    /**
     * 批量删除
     */
    void deleteBatch(Long[] longs);
}
