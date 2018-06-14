package com.eric.modules.sys.controller;

import com.eric.common.utils.BaseController;
import com.eric.common.utils.Constant;
import com.eric.common.utils.PageUtils;
import com.eric.common.utils.Res;
import com.eric.modules.sys.entity.SysRoleEntity;
import com.eric.modules.sys.param.SearchRoleParam;
import com.eric.modules.sys.service.SysRoleDeptService;
import com.eric.modules.sys.service.SysRoleMenuService;
import com.eric.modules.sys.service.SysRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 角色管理
 * @author Chen 2018/1/14
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysRoleDeptService sysRoleDeptService;
    /**
     * 角色列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:role:list")
    public Res list(SearchRoleParam param){
        //如果不是超级管理员，则只查询自己创建的角色列表
        if(getUserId() != Constant.SUPER_ADMIN){
            param.setCreateUserId(getUserId());
        }
        PageUtils pageUtil = sysRoleService.queryList(param);

        return Res.ok().put("page", pageUtil);
    }

    /**
     * 保存角色
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:role:save")
    public Res save(@RequestBody SysRoleEntity role){
        role.setCreateUserid(getUserId());
        sysRoleService.save(role);
        return Res.ok();
    }

    /**
     * 角色信息
     */
    @RequestMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    public Res info(@PathVariable("roleId") Long roleId){
        SysRoleEntity role = sysRoleService.queryByRoleId(roleId);

        //查询角色对应的菜单
        List<Long> menuIdList = sysRoleMenuService.queryMenuByRoleId(roleId);
        role.setMenuIdList(menuIdList);

        //查询角色对应的部门
        List<Long> deptIdList = sysRoleDeptService.queryDeptIdList(roleId);
        role.setDeptIdList(deptIdList);

        return Res.ok().put("role", role);
    }

    /**
     * 修改角色
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:role:update")
    public Res update(@RequestBody SysRoleEntity role){
        sysRoleService.update(role);

        return Res.ok();
    }

    /**
     * 删除角色
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    public Res delete(@RequestBody Long[] roleIds){
        sysRoleService.deleteBatch(roleIds);

        return Res.ok();
    }
    /**
     * 角色列表
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:role:select")
    public Res select(){
        Map<String, Object> map = new HashMap<>();

        //如果不是超级管理员，则只查询自己所拥有的角色列表
        if(getUserId() != Constant.SUPER_ADMIN){
            map.put("createUserId", getUserId());
        }
        List<SysRoleEntity> list = sysRoleService.selectList(map);

        return Res.ok().put("list", list);
    }
}
