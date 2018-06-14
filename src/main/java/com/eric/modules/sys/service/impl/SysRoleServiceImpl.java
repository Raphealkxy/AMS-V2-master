package com.eric.modules.sys.service.impl;

import com.eric.common.utils.BaseExample;
import com.eric.common.utils.PageUtils;
import com.eric.modules.sys.dao.SysDeptDao;
import com.eric.modules.sys.dao.SysRoleDao;
import com.eric.modules.sys.entity.SysDeptEntity;
import com.eric.modules.sys.entity.SysRoleEntity;
import com.eric.modules.sys.param.SearchRoleParam;
import com.eric.modules.sys.service.SysRoleDeptService;
import com.eric.modules.sys.service.SysRoleMenuService;
import com.eric.modules.sys.service.SysRoleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Chen 2018/1/14
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleDao sysRoleDao;
    @Autowired
    private SysDeptDao sysDeptDao;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysRoleDeptService sysRoleDeptService;


    /**
     * 获取角色列表
     */
    @Override
    public PageUtils queryList(SearchRoleParam param) {
        Page<Object> page = PageHelper.startPage(param.getPage(), param.getLimit());
        Example example = BaseExample.getExample(SysRoleEntity.class,param);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(param.getRoleName())){
            criteria.andLike("roleName",param.getRoleName());
        }
        if (param.getCreateUserId()!=null){
            criteria.andEqualTo("createUserid",param.getCreateUserId());
        }
        List<SysRoleEntity> list = sysRoleDao.selectByExample(example);
        for(SysRoleEntity entity : list){
            if (entity.getDeptId() != null){
                SysDeptEntity deptEntity = sysDeptDao.selectByPrimaryKey(entity.getDeptId());
                if (deptEntity!=null && StringUtils.isNotBlank(deptEntity.getName()))
                    entity.setDeptName(deptEntity.getName());
            }
        }
        PageUtils pageUtils = new PageUtils(list,page.getTotal(),param.getLimit(),param.getPage());
        return pageUtils;
    }

    @Override
    public void save(SysRoleEntity role) {
        role.setCreateTime(new Date());
        sysRoleDao.insert(role);
        //保存角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());

        //保存角色与部门关系
        sysRoleDeptService.saveOrUpdate(role.getRoleId(), role.getDeptIdList());
    }

    @Override
    public SysRoleEntity queryByRoleId(Long roleId) {
        return sysRoleDao.selectByPrimaryKey(roleId);
    }

    @Override
    public void update(SysRoleEntity role) {
        sysRoleDao.update(role);

        //更新角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());

        //保存角色与部门关系
        sysRoleDeptService.saveOrUpdate(role.getRoleId(), role.getDeptIdList());
    }

    @Override
    public void deleteBatch(Long[] roleIds) {
        sysRoleDao.deleteBatch(roleIds);
    }

    @Override
    public List<SysRoleEntity> selectList(Map<String, Object> map) {
        return sysRoleDao.queryList(map);
    }


}
