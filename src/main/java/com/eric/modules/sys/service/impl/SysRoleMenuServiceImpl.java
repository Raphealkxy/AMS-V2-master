package com.eric.modules.sys.service.impl;

import com.eric.modules.sys.dao.SysRoleMenuDao;
import com.eric.modules.sys.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Chen 2018/1/14
 */
@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;

    /**
     * 查询角色对应的菜单
     */
    @Override
    public List<Long> queryMenuByRoleId(Long roleId) {
        return sysRoleMenuDao.queryMenuByRoleId(roleId);
    }

    @Override
    public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
        //先删除角色与菜单关系
        sysRoleMenuDao.delete(roleId);
        if(menuIdList.size() == 0){
            return ;
        }
        //保存角色与菜单关系
        Map<String, Object> map = new HashMap<>();
        map.put("roleId", roleId);
        map.put("menuIdList", menuIdList);
        sysRoleMenuDao.save(map);
    }
}
