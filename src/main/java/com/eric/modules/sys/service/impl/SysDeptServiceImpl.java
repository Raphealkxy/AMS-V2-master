package com.eric.modules.sys.service.impl;

import com.eric.common.utils.BaseExample;
import com.eric.modules.ams.dao.AmsAttlistDao;
import com.eric.modules.ams.dao.AmsCourseDao;
import com.eric.modules.ams.entity.AmsAttlistEntity;
import com.eric.modules.sys.dao.SysDeptDao;
import com.eric.modules.sys.entity.SysDeptEntity;
import com.eric.modules.sys.param.SearchDeptParam;
import com.eric.modules.sys.service.SysDeptService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;

/**
 * @author Chen 2018/1/13
 */
@Service
public class SysDeptServiceImpl implements SysDeptService{
    @Autowired
    private SysDeptDao sysDeptDao;
    @Autowired
    private AmsAttlistDao amsAttlistDao;
    @Autowired
    private AmsCourseDao amsCourseDao;

    /**
     * 查询列表
     */
    @Override
    public List<SysDeptEntity> queryList(SearchDeptParam param) {
        Page<Object> page = PageHelper.startPage(param.getPage(), param.getLimit());
        Example example = BaseExample.getExample(SysDeptEntity.class, param);
        Example.Criteria criteria = example.createCriteria();
        if (param.getDeptType()!=null)
            criteria.andEqualTo("deptType",param.getDeptType());
        if (StringUtils.isNotBlank(param.getDeptName()))
            criteria.andLike("name",param.getDeptName());
        criteria.andEqualTo("delFlag",0);
        List<SysDeptEntity> entities = sysDeptDao.selectByExample(example);
        for (SysDeptEntity entity :entities){
            SysDeptEntity parentEntity = sysDeptDao.selectByPrimaryKey(entity.getParentId());
            if(parentEntity != null)
                entity.setParentName(parentEntity.getName());
        }
        return entities;
    }

    /**
     * 根据部门ID查询
     */
    @Override
    public SysDeptEntity queryByDeptId(Long deptId) {
        return sysDeptDao.selectByPrimaryKey(deptId);
    }

    /**
     * 增加部门
     */
    @Override
    public void save(SysDeptEntity dept) {
        dept.setDelFlag(new Byte("0"));
        sysDeptDao.insert(dept);
    }

    /**
     * 更新部门
     */
    @Override
    public void update(SysDeptEntity dept) {
        sysDeptDao.updateByPrimaryKey(dept);
    }

    /**
     * 根据部门Id查询list
     */
    @Override
    public List<Long> queryDetpIdList(long deptId) {
        return sysDeptDao.queryDetpIdList(deptId);
    }

    /**
     * 根据Id删除
     */
    @Override
    public void delete(long deptId) {
        sysDeptDao.deleteByPrimaryKey(deptId);
    }

    @Override
    public List<Long> queryDetpIdList(Long[] deptId) {
        List<Long> longs = Arrays.asList(deptId);
        return sysDeptDao.querySonDeptList(longs);
    }

    @Override
    public void deleteBatch(Long[] deptId) {
        List<Long> longs = Arrays.asList(deptId);
        sysDeptDao.deleteBatch(longs);
    }

    @Override
    public String getDeptGroupNameByAttId(Long attId) {
        AmsAttlistEntity amsAttlistEntity = amsAttlistDao.selectByPrimaryKey(attId);
        if (amsAttlistEntity == null) return null;
        Long courseId = amsAttlistEntity.getCourseId();
        List<Long> studentIds = amsCourseDao.queryBindedStudentIds(courseId);
        if (studentIds == null || studentIds.size()<1) return null;
        List<Long> deptIds = sysDeptDao.selectDeptIdsByStuIds(studentIds);
        if (deptIds == null || deptIds.size()<1) return null;
        return sysDeptDao.getDeptGroupsNameByIds(deptIds);
    }

}
