package com.eric.modules.ams.service.impl;

import com.eric.common.result.OperateResult;
import com.eric.common.utils.BaseExample;
import com.eric.common.utils.Constant;
import com.eric.common.utils.PageUtils;
import com.eric.common.utils.Res;
import com.eric.modules.ams.dao.AmsAttdetallDao;
import com.eric.modules.ams.dao.AmsAttlistDao;
import com.eric.modules.ams.dao.AmsCourseStudentDao;
import com.eric.modules.ams.entity.AmsAttdetallEntity;
import com.eric.modules.ams.entity.AmsAttlistEntity;
import com.eric.modules.ams.param.SearchAttDetallParam;
import com.eric.modules.ams.service.AmsAttListService;
import com.eric.modules.ams.service.AmsAttdetallService;
import com.eric.modules.sys.dao.SysUserDao;
import com.eric.modules.sys.entity.SysUserEntity;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.aspectj.weaver.IUnwovenClassFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Chen 2018/1/22
 */
@Service
public class AmsAttdetallServiceImpl implements AmsAttdetallService {
    @Autowired
    private AmsAttdetallDao amsAttdetallDao;
    @Autowired
    private AmsCourseStudentDao amsCourseStudentDao;
    @Autowired
    private SysUserDao sysUserDao;
    @Override
    public Res add(Long courseId, Long attlistId) {
        //查询课程关联的学生
        List<Long> stuIds = amsCourseStudentDao.queryStuIdByCourseId(courseId);
        List<AmsAttdetallEntity> list = new ArrayList<>();
        for (Long stuId : stuIds){
            SysUserEntity entity = sysUserDao.selectByPrimaryKey(stuId);
            if (entity == null) continue;
            AmsAttdetallEntity amsAttdetallEntity = new AmsAttdetallEntity();
            amsAttdetallEntity.setAttlistId(attlistId);
            amsAttdetallEntity.setUserName(entity.getUserName());
            amsAttdetallEntity.setLoginNum(entity.getLoginNum());
            amsAttdetallEntity.setStatus(2);
            list.add(amsAttdetallEntity);
        }
        amsAttdetallDao.insertList(list);
        return Res.ok();
    }

    /**
     * 根据考勤单Id删除
     */
    @Override
    public void deleteByAttlistIds(Long[] attlistIds) {
        List<Long> longs = Arrays.asList(attlistIds);
        Example example = new Example(AmsAttdetallEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("attlistId",longs);
        amsAttdetallDao.deleteByExample(example);
    }

    @Override
    public PageUtils queryList(SearchAttDetallParam param) {
        Example example = BaseExample.getExample(AmsAttdetallEntity.class,param);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("attlistId",param.getAttlistId());
        if (StringUtils.isNotBlank(param.getLoginNum()))
            criteria.andLike("loginNum","%"+param.getLoginNum()+"%");
        if (StringUtils.isNotBlank(param.getUserName()))
            criteria.andLike("userName","%"+param.getUserName()+"%");
        Page<Object> page = PageHelper.startPage(param.getPage(), param.getLimit());
        List<AmsAttdetallEntity> entities = amsAttdetallDao.selectByExample(example);
        return new PageUtils(entities,page.getTotal(),param.getLimit(),param.getPage());
    }

    @Override
    public void updateStatus(Long[] attdetallIds,int status) {
        List<Long> longs = Arrays.asList(attdetallIds);
        for (Long id : longs){
            AmsAttdetallEntity entity = amsAttdetallDao.selectByPrimaryKey(id);
            entity.setStatus(status);
            amsAttdetallDao.updateByPrimaryKey(entity);
        }
    }

    @Override
    public OperateResult updateStatusByAttIdAndStudentIds(Long attId, List<String> loginNums) {
        if (attId == null) return new OperateResult("参数有误");
        if (loginNums == null || loginNums.size()<1) return new OperateResult("未识别出任何人脸");
        //根据考勤单ID查询考勤细则
        Example example = new Example(AmsAttdetallEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("attlistId",attId);
        criteria.andIn("loginNum",loginNums);
        List<AmsAttdetallEntity> entities = amsAttdetallDao.selectByExample(example);
        for (AmsAttdetallEntity entity :entities){
            entity.setStatus(Constant.AttStatus.ATT.getValue());
            amsAttdetallDao.updateByPrimaryKey(entity);
        }
        return new OperateResult(entities.size(),"更新成功");
    }

    @Override
    public List<Long> selectAttlistIdsByUserId(Long userId) {
        SysUserEntity sysUserEntity = sysUserDao.selectByPrimaryKey(userId);
        if (sysUserEntity == null) return null;
        List<Long> ids =  amsAttdetallDao.selectAttlistIdsByLoginNum(sysUserEntity.getLoginNum());
        if (ids == null || ids.size()<1)
            return null;
        return ids;
    }

    @Override
    public Integer getAttStatusByAttlistIdAndStuId(Long attId, Long userId) {
        SysUserEntity sysUserEntity = sysUserDao.selectByPrimaryKey(userId);
        Example example = new Example(AmsAttdetallEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("attlistId",attId);
        criteria.andEqualTo("loginNum",sysUserEntity.getLoginNum());
        List<AmsAttdetallEntity> entities = amsAttdetallDao.selectByExample(example);
        if (entities==null || entities.size()!=1) return 0;
        return entities.get(0).getStatus();
    }
}
