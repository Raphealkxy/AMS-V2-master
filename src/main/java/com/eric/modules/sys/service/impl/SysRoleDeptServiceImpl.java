package com.eric.modules.sys.service.impl;

import com.eric.modules.sys.dao.SysRoleDeptDao;
import com.eric.modules.sys.service.SysRoleDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Chen 2018/1/14
 */
@Service
public class SysRoleDeptServiceImpl implements SysRoleDeptService {
    @Autowired
    private SysRoleDeptDao sysRoleDeptDao;
    @Override
    public List<Long> queryDeptIdList(Long roleId) {
        return sysRoleDeptDao.queryDeptIdList(roleId);
    }

    @Override
    public void saveOrUpdate(Long roleId, List<Long> deptIdList) {
        //先删除角色与菜单关系
        sysRoleDeptDao.delete(roleId);

        if(deptIdList.size() == 0){
            return ;
        }

        //保存角色与菜单关系
        Map<String, Object> map = new HashMap<>();
        map.put("roleId", roleId);
        map.put("deptIdList", deptIdList);
        sysRoleDeptDao.save(map);
    }
}
