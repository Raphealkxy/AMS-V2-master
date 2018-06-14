package com.eric.modules.ams.service;


import com.eric.common.utils.PageUtils;
import com.eric.modules.ams.param.SearchClassParam;
import com.eric.modules.sys.entity.SysDeptEntity;

/**
 * 班级管理服务
 * @author Chen 2018/1/13
 */
public interface AmsClassService {
    /**
     * 根据参数搜索
     */
    PageUtils queryList(SearchClassParam param);

    /**
     * 保存班级
     */
    void save(SysDeptEntity dept);

    /**
     * 根据Id查询
     */
    SysDeptEntity queryByDeptId(Long deptId);
}
