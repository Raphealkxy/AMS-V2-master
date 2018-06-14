package com.eric.modules.ams.service.impl;

import com.eric.common.utils.PageUtils;
import com.eric.modules.ams.param.SearchClassParam;
import com.eric.modules.ams.service.AmsClassService;
import com.eric.modules.sys.dao.SysDeptDao;
import com.eric.modules.sys.entity.SysDeptEntity;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Chen 2018/1/14
 */
@Service
public class AmsClassServiceImpl implements AmsClassService {
    @Autowired
    private SysDeptDao sysDeptDao;

    @Override
    public PageUtils queryList(SearchClassParam param) {
        Page<Object> page = PageHelper.startPage(param.getPage(), param.getLimit());
        List<SysDeptEntity> entities =sysDeptDao.queryClassList(param);
        return new PageUtils(entities,page.getTotal(),param.getLimit(),param.getPage());
    }

    @Override
    public void save(SysDeptEntity dept) {
        dept.setDelFlag(new Byte("0"));
        dept.setDeptType(1);//班级
        sysDeptDao.insert(dept);
    }

    @Override
    public SysDeptEntity queryByDeptId(Long deptId) {
        return sysDeptDao.selectByPrimaryKey(deptId);
    }
}
