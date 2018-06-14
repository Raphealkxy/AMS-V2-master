package com.eric.modules.sys.service;

import com.eric.common.utils.PageUtils;
import com.eric.modules.sys.entity.SysRoleEntity;
import com.eric.modules.sys.param.SearchRoleParam;

import java.util.List;
import java.util.Map;

/**
 * @author Chen 2018/1/14
 */
public interface SysRoleService {
    /**
     * 搜索用户参数
     */
    PageUtils queryList(SearchRoleParam param);

    /**
     * 保存角色
     */
    void save(SysRoleEntity role);

    /**
     * 根据id查询角色
     */
    SysRoleEntity queryByRoleId(Long roleId);

    /**
     * 更新
     */
    void update(SysRoleEntity role);

    /**
     * 批量删除
     */
    void deleteBatch(Long[] roleIds);

    List<SysRoleEntity> selectList(Map<String, Object> map);
}
