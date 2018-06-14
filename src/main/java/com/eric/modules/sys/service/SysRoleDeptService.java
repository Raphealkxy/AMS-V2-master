package com.eric.modules.sys.service;

import java.util.List;

/**
 * @author Chen 2018/1/14
 */
public interface SysRoleDeptService {
    /**
     * 查询角色对应的部门
     */
    List<Long> queryDeptIdList(Long roleId);

    void saveOrUpdate(Long roleId, List<Long> deptIdList);
}
