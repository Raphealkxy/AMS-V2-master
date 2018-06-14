package com.eric.modules.ams.controller;

import com.eric.common.utils.BaseController;
import com.eric.common.utils.Constant;
import com.eric.common.utils.PageUtils;
import com.eric.common.utils.Res;
import com.eric.modules.sys.entity.SysDeptEntity;
import com.eric.modules.sys.entity.SysUserEntity;
import com.eric.modules.sys.param.SearchSysUserParam;
import com.eric.modules.sys.service.SysDeptService;
import com.eric.modules.sys.service.SysUserRoleService;
import com.eric.modules.sys.service.SysUserService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学生管理
 * @author Chen 2018/1/14
 */
@RestController
@RequestMapping("/ams/student")
public class AmsStudentController extends BaseController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysDeptService sysDeptService;


    @GetMapping("/list")
    @ResponseBody
    @RequiresPermissions("sys:student:list")
    public Res list(SearchSysUserParam param){
        param.setUserType(1);
        PageUtils pageUtil = sysUserService.queryList(param);
        return Res.ok().put("page", pageUtil);
    }


    /**
     * 获取登录的用户信息
     */
    @RequestMapping("/info")
    public Res info(){
        return Res.ok().put("user", getUser());
    }

    /**
     * 用户信息
     */
    @RequestMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    public Res info(@PathVariable("userId") Long userId){
        SysUserEntity user = sysUserService.queryByUserId(userId);

//        获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);

        return Res.ok().put("user", user);
    }

    /**
     * 保存学生
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:student:save")
    public Res save(@RequestBody SysUserEntity user){
        if (user.getDeptId()==null){
            return Res.error("请选择班级");
        }
        SysDeptEntity deptEntity = sysDeptService.queryByDeptId(user.getDeptId());
        if (deptEntity==null){
            return Res.error("班级选择有误");
        }
        if (deptEntity.getDeptType()!=1){
            return Res.error("请选择班级，不要选择部门");
        }
        user.setCreateUserId(getUserId());
        user.setUserType(1);
        sysUserService.save(user);
        return Res.ok();
    }

    /**
     * 修改用户
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:student:update")
    public Res update(@RequestBody SysUserEntity user){
        if (user.getDeptId()==null){
            return Res.error("请选择班级");
        }
        SysDeptEntity deptEntity = sysDeptService.queryByDeptId(user.getDeptId());
        if (deptEntity==null){
            return Res.error("班级选择有误");
        }
        if (deptEntity.getDeptType()!=1){
            return Res.error("请选择班级，不要选择部门");
        }
        user.setCreateUserId(getUserId());
        user.setUserType(1);
        sysUserService.update(user);

        return Res.ok();
    }

    /**
     * 删除用户
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:student:delete")
    public Res delete(@RequestBody Long[] userIds){
        if(ArrayUtils.contains(userIds, 1L)){
            return Res.error("系统管理员不能删除");
        }

        if(ArrayUtils.contains(userIds, getUserId())){
            return Res.error("当前用户不能删除");
        }

        sysUserService.deleteBatch(userIds);

        return Res.ok();
    }

    /**
     * 根据部门ID查询
     */
    @RequestMapping("/stulist")
    @RequiresPermissions("sys:student:list")
    public Res stulist(SearchSysUserParam param){
        if (param.getDeptId() == null){
            return Res.error("参数有误");
        }
        param.setUserType(Constant.UserType.STUDENT.getValue());
        PageUtils pageUtil = sysUserService.queryListByDeptId(param);
        return Res.ok().put("page", pageUtil);
    }

}
