package com.eric.modules.ams.controller;

import com.eric.common.utils.BaseController;
import com.eric.common.utils.Res;
import com.eric.modules.ams.entity.AmsCourseEntity;
import com.eric.modules.ams.param.SearchCourseParam;
import com.eric.modules.ams.service.AmsCourseService;
import com.eric.modules.sys.entity.SysDeptEntity;
import com.eric.modules.sys.entity.SysUserEntity;
import com.eric.modules.sys.service.SysUserService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 课程管理
 * @author Chen 2018/1/14
 */
@RestController
@RequestMapping("/ams/course")
public class AmsCourseController extends BaseController {

    @Autowired
    private AmsCourseService amsCourseService;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:course:list")
    public Res list(SearchCourseParam param){
        param.setUserId(getUserId());
        return amsCourseService.queryList(param);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:course:save")
    public Res save(@RequestBody AmsCourseEntity courseEntity){
        String check = dataCheck(courseEntity);
        if (check !=null)
            return Res.error(check);
        amsCourseService.save(courseEntity);
        return Res.ok();
    }

    /**
     * 课程信息信息
     */
    @RequestMapping("/info/{courseId}")
    @RequiresPermissions("sys:course:info")
    public Res info(@PathVariable("courseId") Long courseId){
        AmsCourseEntity course = amsCourseService.queryByCourseId(courseId);
        return Res.ok().put("course", course);
    }

    /**
     * 修改课程
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:course:update")
    public Res update(@RequestBody AmsCourseEntity courseEntity){
        String check = dataCheck(courseEntity);
        if (check !=null)
            return Res.error(check);
        amsCourseService.update(courseEntity);
        return Res.ok();
    }

    /**
     * 删除课程
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:course:delete")
    public Res delete(@RequestBody Long[] courseIds){
        amsCourseService.deleteBatch(courseIds);
        return Res.ok();
    }

    /**
     * 查询该老师课程
     */
    @RequestMapping("/select")
    public Res select(SearchCourseParam param){
        if (getUserId() ==1) {
            param.setUserId(getUserId());
            return amsCourseService.queryList(param);
        }
        return amsCourseService.selectCourseByTeacherId(param,getUserId());
    }
    private String dataCheck(AmsCourseEntity courseEntity){
        if (StringUtils.isBlank(courseEntity.getCourseCode())) return "课程代码不能为空";
        if (StringUtils.isBlank(courseEntity.getCourseName())) return "课程名称不能为空";
        if (StringUtils.isBlank(courseEntity.getTeacherName()))return "请选择任课教师";
        if (courseEntity.getTeacherId()== null) return "任课教师选择有误";
        return null;
    }
}
