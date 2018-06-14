package com.eric.modules.sys.controller;

import com.eric.common.utils.BaseController;
import com.eric.common.utils.Constant;
import com.eric.common.utils.Res;
import com.eric.modules.sys.entity.SysDeptEntity;
import com.eric.modules.sys.param.SearchDeptParam;
import com.eric.modules.sys.service.SysDeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 部门管理Controller
 * @author Chen 2018/1/13
 */
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController extends BaseController {
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:dept:list")
    public List<SysDeptEntity> list(SearchDeptParam param){
        param.setDeptType(0);//部门
        List<SysDeptEntity> deptList = sysDeptService.queryList(param);
        return deptList;
    }
    /**
     * 上级部门Id(管理员则为0)
     */
    @RequestMapping("/info")
    @RequiresPermissions("sys:dept:list")
    public Res info(){
        long deptId = 0;
        if(getUserId() != Constant.SUPER_ADMIN){
            SysDeptEntity dept = sysDeptService.queryByDeptId(getDeptId());
            deptId = dept.getParentId();
        }

        return Res.ok().put("deptId", deptId);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:dept:save")
    public Res save(@RequestBody SysDeptEntity dept){
        sysDeptService.save(dept);
        return Res.ok();
    }

    /**
     * 选择部门(添加、修改菜单)
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:dept:select")
    public Res select(SearchDeptParam param){
        List<SysDeptEntity> deptList = sysDeptService.queryList(param);

        //添加一级部门
        if(getUserId() == Constant.SUPER_ADMIN){
            SysDeptEntity root = new SysDeptEntity();
            root.setDeptId(0L);
            root.setName("一级部门");
            root.setParentId(-1L);
            root.setOpen(true);
            deptList.add(root);
        }

        return Res.ok().put("deptList", deptList);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{deptId}")
    @RequiresPermissions("sys:dept:info")
    public Res info(@PathVariable("deptId") Long deptId){
        SysDeptEntity dept = sysDeptService.queryByDeptId(deptId);
        return Res.ok().put("dept", dept);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:dept:update")
    public Res update(@RequestBody SysDeptEntity dept){
        dept.setDeptType(0);
        sysDeptService.update(dept);
        return Res.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:dept:delete")
    public Res delete(long deptId){
        //判断是否有子部门
        List<Long> deptList = sysDeptService.queryDetpIdList(deptId);
        if(deptList.size() > 0){
            return Res.error("请先删除子部门");
        }

        sysDeptService.delete(deptId);

        return Res.ok();
    }

}
