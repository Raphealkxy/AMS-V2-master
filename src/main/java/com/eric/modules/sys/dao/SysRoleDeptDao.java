package com.eric.modules.sys.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Chen 2018/1/14
 */
@Repository
public interface SysRoleDeptDao{
    List<Long> queryDeptIdList(Long roleId);

    void save(Map<String, Object> map);

    void delete(Long roleId);
}
