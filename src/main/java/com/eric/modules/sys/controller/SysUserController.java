package com.eric.modules.sys.controller;

import com.eric.common.utils.BaseController;
import com.eric.common.utils.PageUtils;
import com.eric.common.utils.Res;
import com.eric.modules.sys.entity.SysUserEntity;
import com.eric.modules.sys.param.SearchSysUserParam;
import com.eric.modules.sys.service.SysUserRoleService;
import com.eric.modules.sys.service.SysUserService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 系统用户
 * @author Chen 2018/1/10
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @GetMapping("/list")
    @ResponseBody
    @RequiresPermissions("sys:user:list")
    public Res list(SearchSysUserParam param){
        param.setUserType(0);
        PageUtils pageUtil = sysUserService.queryList(param);
        return Res.ok().put("page", pageUtil);
    }

    @GetMapping("/test")
    @ResponseBody
    public String test(){
        return "123123";
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
     * 保存用户
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:user:save")
    public Res save(@RequestBody SysUserEntity user){
        if (StringUtils.isBlank(user.getLoginNum())){
            return Res.error("工号不能为空");
        }
        if (StringUtils.isBlank(user.getUserName())){
            return Res.error("用户名不能为空");
        }
        if (StringUtils.isBlank(user.getPassword())){
            return Res.error("密码不能为空");
        }
        if (user.getStatus() == null){
            return Res.error("状态不能为空");
        }
        user.setCreateUserId(getUserId());
        user.setUserType(0);
        sysUserService.save(user);
        return Res.ok();
    }

    /**
     * 修改用户
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:user:update")
    public Res update(@RequestBody SysUserEntity user){
        user.setCreateUserId(getUserId());
        user.setUserType(0);
        sysUserService.update(user);

        return Res.ok();
    }


    /**
     * 删除用户
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:user:delete")
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
     * 修改登录用户密码
     */
    @RequestMapping("/password")
    public Res password(String password, String newPassword){
        if (StringUtils.isBlank(newPassword))
            Res.error("新密码不为能空");

        //sha256加密
        password = new Sha256Hash(password, getUser().getSalt()).toHex();
        //sha256加密
        newPassword = new Sha256Hash(newPassword, getUser().getSalt()).toHex();

        //更新密码
        int count = sysUserService.updatePassword(getUserId(), password, newPassword);
        if(count == 0){
            return Res.error("原密码不正确");
        }

        return Res.ok();
    }
}
