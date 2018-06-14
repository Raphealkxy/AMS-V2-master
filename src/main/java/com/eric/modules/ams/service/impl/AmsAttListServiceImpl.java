package com.eric.modules.ams.service.impl;

import com.eric.common.utils.*;
import com.eric.modules.ams.dao.AmsAttdetallDao;
import com.eric.modules.ams.dao.AmsAttlistDao;
import com.eric.modules.ams.dao.AmsCourseDao;
import com.eric.modules.ams.entity.AmsAttlistEntity;
import com.eric.modules.ams.entity.AmsCourseEntity;
import com.eric.modules.ams.param.SearchAmsAttlistParam;
import com.eric.modules.ams.service.AmsAttListService;
import com.eric.modules.ams.service.AmsAttdetallService;
import com.eric.modules.sys.dao.SysUserDao;
import com.eric.modules.sys.entity.SysUserEntity;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Chen 2018/1/21
 */
@Service
public class AmsAttListServiceImpl implements AmsAttListService {

    @Autowired
    private AmsAttlistDao amsAttlistDao;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private AmsCourseDao amsCourseDao;
    @Autowired
    private AmsAttdetallService amsAttdetallService;
    /**
     * 分页查询
     */
    @Override
    public PageUtils queryList(SearchAmsAttlistParam param) {
        Example example  = BaseExample.getExample(AmsAttlistEntity.class,param);
        example.setOrderByClause("create_time desc");
        Example.Criteria criteria = example.createCriteria();
        Page<Object> page = PageHelper.startPage(param.getPage(), param.getLimit());
        if (param.getUserId()!=1)
            criteria.andEqualTo("createId",param.getUserId());
        if (StringUtils.isNotBlank(param.getCourseName()))
            criteria.andLike("courseName","%"+param.getCourseName()+"%");
        if (StringUtils.isNotBlank(param.getCreateName()))
            criteria.andLike("","%"+param.getCreateName()+"%");
        List<AmsAttlistEntity> entities = amsAttlistDao.selectByExample(example);
        return new PageUtils(entities,page.getTotal(),param.getLimit(),param.getPage());
    }

    /**
     * 创建考勤单
     */
    @Override
    public Res add(Long courseId, Long userId) {
        SysUserEntity user = sysUserDao.selectByPrimaryKey(userId);
        AmsCourseEntity course = amsCourseDao.selectByPrimaryKey(courseId);
        if (user == null)
            return Res.error("数据异常-未查询该用户");
        if (user.getUserType() == 1)
            return Res.error("数据异常-学生无法创建");
        if (course == null)
            return Res.error("数据异常-未查询到该课程");
        AmsAttlistEntity entity = new AmsAttlistEntity();
        entity.setAttCode(AmsUtils.getAttListCode());
        entity.setCourseId(courseId);
        entity.setCourseName(course.getCourseName());
        entity.setTermNum(AmsUtils.getTermStr());
        entity.setCreateId(userId);
        entity.setCreateName(user.getUserName());
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        amsAttlistDao.insert(entity);
        return amsAttdetallService.add(courseId, entity.getAttId());
    }

    @Override
    public void deleteBatch(Long[] attlistIds) {
        List<Long> longs = Arrays.asList(attlistIds);
        if (longs.size() <1)
            return ;
        amsAttdetallService.deleteByAttlistIds(attlistIds);
        //删除
        Example example = new Example(AmsAttlistEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("attId",longs);
        amsAttlistDao.deleteByExample(example);
    }

    @Override
    public AmsAttlistEntity queryByAttlistId(Long attlistId) {
        return amsAttlistDao.selectByPrimaryKey(attlistId);
    }

    @Override
    public void updateStatusByAttIdAndStatus(Long attId, Constant.AttListStatus status) {
        AmsAttlistEntity amsAttlistEntity = amsAttlistDao.selectByPrimaryKey(attId);
        amsAttlistEntity.setStatus(status.getValue());
        amsAttlistDao.updateByPrimaryKey(amsAttlistEntity);
    }

    @Override
    public PageUtils queryMyList(SearchAmsAttlistParam param) {
        //根据用户ID查询该用户关联的考勤单ID
        List<Long> attIdList = amsAttdetallService.selectAttlistIdsByUserId(param.getUserId());
        if (attIdList == null) return new PageUtils(new ArrayList(),0,param.getLimit(),param.getPage());
        Example example  = BaseExample.getExample(AmsAttlistEntity.class,param);
        example.setOrderByClause("create_time desc");
        Example.Criteria criteria = example.createCriteria();
        Page<Object> page = PageHelper.startPage(param.getPage(), param.getLimit());
        criteria.andIn("attId",attIdList);
        if (StringUtils.isNotBlank(param.getCourseName()))
            criteria.andLike("courseName","%"+param.getCourseName()+"%");
        if (StringUtils.isNotBlank(param.getCreateName()))
            criteria.andLike("","%"+param.getCreateName()+"%");
        List<AmsAttlistEntity> entities = amsAttlistDao.selectByExample(example);
        for (AmsAttlistEntity entity:entities){
            Integer status = amsAttdetallService.getAttStatusByAttlistIdAndStuId(entity.getAttId(),param.getUserId());
            entity.setAttStatus(status);
        }
        return new PageUtils(entities,page.getTotal(),param.getLimit(),param.getPage());
    }
}
