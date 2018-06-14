package com.eric.modules.ams.controller;

import com.eric.common.utils.BaseController;
import com.eric.common.utils.Res;
import com.eric.modules.ams.entity.AmsCourseEntity;
import com.eric.modules.ams.param.SearchCourseParam;
import com.eric.modules.ams.service.AmsCourseService;
import com.eric.modules.sys.entity.SysUserEntity;
import com.eric.modules.sys.param.SearchChoseParam;
import com.eric.modules.sys.service.SysUserService;
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
@RequestMapping("/ams/chose")
public class AmsChoseController extends BaseController {

    @Autowired
    private AmsCourseService amsCourseService;

    /**
     * 已绑定学生列表
     */
    @RequestMapping("/binded")
    public Res bindedList(SearchChoseParam param){
        if (param == null || param.getCourseId()==null){
            return Res.error("加载已绑定学生失败");
        }
        return amsCourseService.queryBindedStudent(param);
    }

    /**
     * 未绑定学生列表
     */
    @RequestMapping("/unbinded")
    public Res list(SearchChoseParam param){
        if (param == null || param.getCourseId()==null){
            return Res.error("加载已绑定学生失败");
        }
        return amsCourseService.queryUnBindedStudent(param);
    }

    /**
     * 解绑
     */
    @RequestMapping("/unbind")
    public Res unbind(Long courseId,Long[] userIds){
        if (courseId == null || userIds == null) return Res.error("参数有误");
        return amsCourseService.unbind(courseId,userIds);
    }

    /**
     * 绑定
     */
    @RequestMapping("/bind")
    public Res bind(Long courseId,Long[] userIds){
        if (courseId == null || userIds == null) return Res.error("参数有误");
        return amsCourseService.bind(courseId,userIds);
    }
}
