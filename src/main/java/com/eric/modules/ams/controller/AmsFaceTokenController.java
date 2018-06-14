package com.eric.modules.ams.controller;

import com.eric.common.exception.AMSException;
import com.eric.common.result.OperateResult;
import com.eric.common.utils.BaseController;
import com.eric.common.utils.Res;
import com.eric.modules.ams.service.AmsFaceTokenService;
import com.eric.modules.sys.entity.SysUserEntity;
import com.eric.modules.sys.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author Chen 2018/2/19
 */
@RestController
@RequestMapping("/ams/facetoken")
public class AmsFaceTokenController extends BaseController {
    @Autowired
    private AmsFaceTokenService amsFaceTokenService;
    @Autowired
    private SysUserService sysUserService;


    /**
     * 上传文件
     */
    @RequestMapping("/upload")
    @RequiresPermissions("sys:facetoken:upload")
    public Res upload(@RequestParam("picFile") MultipartFile file,@RequestParam("userId") Long userId) throws Exception {
        if (file.isEmpty()) {
            throw new AMSException("上传文件不能为空");
        }
        if (userId == null ) return Res.error("参数有误");
        byte[] bytes = file.getBytes();
        //检测人脸是否合格
        SysUserEntity entity = sysUserService.queryByUserId(userId);
        if (entity == null ) return Res.error("参数有误");
        OperateResult checkResult = amsFaceTokenService.checkStandards(bytes);
        if (!checkResult.isSuccess()) return Res.error(checkResult.getErrorMessage());
        //注册人脸
        OperateResult addResult =amsFaceTokenService.addUser(bytes,userId);
        if (!addResult.isSuccess()) return Res.error(addResult.getErrorMessage());
        return Res.ok();
    }

    /**
     * 更新人脸
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:facetoken:update")
    public Res update(@RequestParam("picFile") MultipartFile file,@RequestParam("userId") Long userId) throws Exception {
        if (file.isEmpty()) {
            throw new AMSException("上传文件不能为空");
        }
        if (userId == null ) return Res.error("参数有误");
        byte[] bytes = file.getBytes();
        //检测人脸是否合格
        SysUserEntity entity = sysUserService.queryByUserId(userId);
        if (entity == null ) return Res.error("参数有误");
        if (entity.getRegister()==null|| !entity.getRegister()) return Res.error("该学生尚未注册，请先注册");
        OperateResult checkResult = amsFaceTokenService.checkStandards(bytes);
        if (!checkResult.isSuccess()) return Res.error(checkResult.getErrorMessage());

        //注册人脸
        OperateResult addResult =amsFaceTokenService.updateUser(bytes,userId);
        if (!addResult.isSuccess()) return Res.error(addResult.getErrorMessage());
        return Res.ok();
    }



    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:facetoken:delete")
    public Res delete(@RequestParam("userId") Long userId) throws Exception {
        if (userId == null ) return Res.error("参数有误");
        SysUserEntity entity = sysUserService.queryByUserId(userId);
        if (entity == null ) return Res.error("参数有误");
        OperateResult checkResult = amsFaceTokenService.delete(userId);
        if (!checkResult.isSuccess()) return Res.error(checkResult.getErrorMessage());
        return Res.ok();
    }
}
