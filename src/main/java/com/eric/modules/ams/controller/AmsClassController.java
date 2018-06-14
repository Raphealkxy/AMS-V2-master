package com.eric.modules.ams.controller;

import com.eric.common.utils.*;
import com.eric.modules.ams.param.SearchClassParam;
import com.eric.modules.ams.service.AmsClassService;
import com.eric.modules.sys.entity.SysDeptEntity;
import com.eric.modules.sys.param.SearchDeptParam;
import com.eric.modules.sys.service.SysDeptService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 班级管理
 * @author Chen 2018/1/14
 */
@RestController
@RequestMapping("/ams/class")
public class AmsClassController extends BaseController {
    @Autowired
    private AmsClassService amsClassService;
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:class:list")
    public Res list(SearchClassParam param){
        param.setDeptType(1);//班级
        PageUtils pageUtils = amsClassService.queryList(param);
        return Res.ok().put("page", pageUtils);
    }


    /**
     * 树形结构
     */
    @RequestMapping("/classTree")
    @RequiresPermissions("sys:class:list")
    public List<SysDeptEntity> classTree(SearchDeptParam param){
        List<SysDeptEntity> deptList = sysDeptService.queryList(param);
        return deptList;
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:class:save")
    public Res save(@RequestBody SysDeptEntity dept){
        if (StringUtils.isBlank(dept.getGroupName()))
            dept.setGroupName(AmsUtils.getAttListCode());
        amsClassService.save(dept);
        return Res.ok();
    }

    /**
     * 上级部门Id(管理员则为0)
     */
    @RequestMapping("/info")
    @RequiresPermissions("sys:class:list")
    public Res info(){
        long deptId = 0;
        //如果不是超级管理员
        if(getUserId() != Constant.SUPER_ADMIN){
            SysDeptEntity dept = amsClassService.queryByDeptId(getDeptId());
            deptId = dept.getParentId();
        }

        return Res.ok().put("deptId", deptId);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{deptId}")
    @RequiresPermissions("sys:dept:info")
    public Res info(@PathVariable("deptId") Long deptId){
        SysDeptEntity dept = amsClassService.queryByDeptId(deptId);
        return Res.ok().put("dept", dept);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:class:update")
    public Res update(@RequestBody SysDeptEntity dept){
        dept.setDeptType(1);
        sysDeptService.update(dept);
        return Res.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:class:delete")
    public Res delete(@RequestBody Long[] deptId){
        //判断是否有子部门
        List<Long> deptList = sysDeptService.queryDetpIdList(deptId);
        if(deptList.size() > 0){
            return Res.error("请先删除子部门");
        }

        sysDeptService.deleteBatch(deptId);

        return Res.ok();
    }
}
