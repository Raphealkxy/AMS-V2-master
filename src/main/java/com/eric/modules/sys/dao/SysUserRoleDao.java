package com.eric.modules.sys.dao;


import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 用户与角色对应关系
 */
@Repository
public interface SysUserRoleDao{
	
	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> queryRoleIdList(Long userId);

	void delete(Long userId);

	void save(Map<String, Object> map);
}
