package com.eric.modules.sys.service;

import java.util.List;

/**
 * 角色权限关联服务
 * @author Chen 2018/1/14
 */
public interface SysRoleMenuService {
    /**
     * 查询角色对应的菜单
     */
    List<Long> queryMenuByRoleId(Long roleId);

    /**
     * 保存或更新
     */
    void saveOrUpdate(Long roleId, List<Long> menuIdList);
}
