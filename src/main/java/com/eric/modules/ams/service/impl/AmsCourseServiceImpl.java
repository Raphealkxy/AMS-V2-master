package com.eric.modules.ams.service.impl;

import cn.hutool.db.PageResult;
import com.eric.common.utils.BaseExample;
import com.eric.common.utils.PageUtils;
import com.eric.common.utils.Res;
import com.eric.modules.ams.dao.AmsCourseDao;
import com.eric.modules.ams.dao.AmsCourseStudentDao;
import com.eric.modules.ams.entity.AmsCourseEntity;
import com.eric.modules.ams.entity.AmsCourseStudentEntity;
import com.eric.modules.ams.param.SearchCourseParam;
import com.eric.modules.ams.service.AmsCourseService;
import com.eric.modules.sys.dao.SysUserDao;
import com.eric.modules.sys.entity.SysUserEntity;
import com.eric.modules.sys.param.SearchChoseParam;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.catalina.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Chen 2018/1/16
 */
@Service
public class AmsCourseServiceImpl implements AmsCourseService {
    @Autowired
    private AmsCourseDao amsCourseDao;
    @Autowired
    private AmsCourseStudentDao amsCourseStudentDao;
    @Autowired
    private SysUserDao sysUserDao;
    /**
     * 列表查询
     */
    @Override
    public Res queryList(SearchCourseParam param){
        Page<Object> page = PageHelper.startPage(param.getPage(), param.getLimit());
        Example example = BaseExample.getExample(AmsCourseEntity.class,param);
        Example.Criteria criteria = example.createCriteria();
        if (param.getUserId() !=1)
            criteria.andEqualTo("teacherId",param.getUserId());
        if(StringUtils.isNotBlank(param.getCourseName()))
            criteria.andLike("courseName","%"+param.getCourseName()+"%");
        if (StringUtils.isNotBlank(param.getTeacherName()))
            criteria.andLike("teacherName","%"+param.getTeacherName()+"%");
        List<AmsCourseEntity> entities = amsCourseDao.selectByExample(example);
        PageUtils pageUtils = new PageUtils(entities,page.getTotal(),param.getLimit(),param.getPage());
        return Res.ok().put("page",pageUtils);
    }

    /**
     * 保存课程
     */
    @Override
    public void save(AmsCourseEntity courseEntity) {
        amsCourseDao.insert(courseEntity);
    }

    /**
     * 获取课程信息
     */
    @Override
    public AmsCourseEntity queryByCourseId(Long courseId) {
        return amsCourseDao.selectByPrimaryKey(courseId);
    }

    /**
     * 更新
     */
    @Override
    public void update(AmsCourseEntity courseEntity) {
       amsCourseDao.updateByPrimaryKeySelective(courseEntity);
    }

    /**
     * 批量删除
     */
    @Override
    public void deleteBatch(Long[] courseIds) {
        for (Long courseId : courseIds)
            amsCourseDao.deleteByPrimaryKey(courseId);
    }

    /**
     * 查询已绑定学生
     */
    @Override
    public Res queryBindedStudent(SearchChoseParam param) {
        Page<Object> page = PageHelper.startPage(param.getPage(), param.getLimit());
        List<SysUserEntity> entities = amsCourseDao.queryBindedStudent(param);
        PageUtils result = new PageUtils(entities,page.getTotal(),param.getLimit(),param.getPage());
        return Res.ok().put("page",result);
    }

    /**
     * 解绑
     */
    @Override
    public Res unbind(Long courseId, Long[] userIds) {
        List<Long> studentIds = Arrays.asList(userIds);
        AmsCourseEntity entity = amsCourseDao.selectByPrimaryKey(courseId);
        if (entity == null) return Res.ok("未查询到该课程");
        Example example = new Example(AmsCourseStudentEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("courseId",courseId);
        criteria.andIn("studentId",studentIds);
        int i = amsCourseStudentDao.deleteByExample(example);
        return Res.ok();
    }

    /**
     * 查询未绑定改课程的学生
     */
    @Override
    public Res queryUnBindedStudent(SearchChoseParam param) {
        List<Long> bindedIds = amsCourseDao.queryBindedStudentIds(param.getCourseId());
        Example example = BaseExample.getExample(SysUserEntity.class,param);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status",1);
        criteria.andEqualTo("userType",1);
        if (bindedIds!=null && bindedIds.size()>0)
            criteria.andNotIn("userId",bindedIds);
        if(StringUtils.isNotBlank(param.getLoginNum()))
            criteria.andLike("loginNum","%"+param.getLoginNum()+"%");
        if (StringUtils.isNotBlank(param.getUserName()))
            criteria.andLike("userName","%"+param.getUserName()+"%");
        Page<Object> page = PageHelper.startPage(param.getPage(), param.getLimit());
        List<SysUserEntity> entities = sysUserDao.selectByExample(example);
        PageUtils result = new PageUtils(entities,page.getTotal(),param.getLimit(),param.getPage());
        return Res.ok().put("page",result);
    }

    @Override
    public Res bind(Long courseId, Long[] userIds) {
        List<Long> studentIds = Arrays.asList(userIds);
        AmsCourseEntity entity = amsCourseDao.selectByPrimaryKey(courseId);
        if (entity == null) return Res.ok("未查询到该课程");
        Example example = new Example(AmsCourseStudentEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("courseId",courseId);
        List<AmsCourseStudentEntity> entities = amsCourseStudentDao.selectByExample(example);
        //判断是否已绑定
        for(AmsCourseStudentEntity acse :entities){
            if (studentIds.contains(acse.getStudentId())){
                Res.error("数据异常");
            }
        }
        entities = new ArrayList<>();
        for (Long studentId : studentIds){
            AmsCourseStudentEntity entityToinsert = new AmsCourseStudentEntity();
            entityToinsert.setCourseId(courseId);
            entityToinsert.setStudentId(studentId);
            entities.add(entityToinsert);
        }
        amsCourseStudentDao.insertList(entities);
        return Res.ok();
    }

    @Override
    public Res selectCourseByTeacherId(SearchCourseParam param,Long teacherId) {
        Page<Object> page = PageHelper.startPage(param.getPage(), param.getLimit());
        Example example = BaseExample.getExample(AmsCourseEntity.class,param);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("teacherId",teacherId);
        if(StringUtils.isNotBlank(param.getCourseName()))
            criteria.andLike("courseName","%"+param.getCourseName()+"%");
        if (StringUtils.isNotBlank(param.getTeacherName()))
            criteria.andLike("teacherName","%"+param.getTeacherName()+"%");
        List<AmsCourseEntity> entities = amsCourseDao.selectByExample(example);
        PageUtils pageUtils = new PageUtils(entities,page.getTotal(),param.getLimit(),param.getPage());
        return Res.ok().put("page",pageUtils);
    }
}
