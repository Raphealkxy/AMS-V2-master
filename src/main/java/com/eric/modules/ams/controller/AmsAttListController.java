package com.eric.modules.ams.controller;

import com.eric.common.utils.BaseController;
import com.eric.common.utils.PageUtils;
import com.eric.common.utils.Res;
import com.eric.modules.ams.entity.AmsAttlistEntity;
import com.eric.modules.ams.param.SearchAmsAttlistParam;
import com.eric.modules.ams.service.AmsAttListService;
import com.eric.modules.ams.service.AmsAttdetallService;
import com.eric.modules.sys.entity.SysUserEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 考勤管理
 * @author Chen 2018/1/14
 */
@RestController
@RequestMapping("/ams/attlist")
public class AmsAttListController extends BaseController {
    @Autowired
    private AmsAttListService amsAttListService;
    @Autowired
    private AmsAttdetallService amsAttdetallService;

    @GetMapping("/list")
    @RequiresPermissions("sys:attlist:list")
    public Res list(SearchAmsAttlistParam param){
        param.setUserId(getUserId());
        PageUtils pageUtil = amsAttListService.queryList(param);
        return Res.ok().put("page", pageUtil);
    }

    @GetMapping("/mylist")
    public Res mylist(SearchAmsAttlistParam param){
        param.setUserId(getUserId());
        PageUtils pageUtil = amsAttListService.queryMyList(param);
        return Res.ok().put("page", pageUtil);
    }

    @GetMapping("/add/{courseId}")
    @RequiresPermissions("sys:attlist:save")
    public Res add(@PathVariable("courseId") Long courseId){
        if (courseId == null) return Res.error("创建失败-请求数据有误");
        Res res = amsAttListService.add(courseId, getUserId());
        return res;
    }

    @RequestMapping("/delete")
    @RequiresPermissions("sys:attlist:delete")
    public Res delete(@RequestBody Long[] attlistIds){
        amsAttListService.deleteBatch(attlistIds);
        return Res.ok();
    }

    @RequestMapping("/info/{attlistId}")
    @RequiresPermissions("sys:attlist:info")
    public Res info(@PathVariable("attlistId") Long attlistId){
        AmsAttlistEntity entity = amsAttListService.queryByAttlistId(attlistId);
        return Res.ok().put("attlist",entity);
    }

}
