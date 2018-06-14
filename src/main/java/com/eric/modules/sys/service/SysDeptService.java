package com.eric.modules.sys.service;

import com.eric.modules.sys.entity.SysDeptEntity;
import com.eric.modules.sys.param.SearchDeptParam;

import java.util.List;

/**
 * 部门管理服务
 * @author Chen 2018/1/13
 */
public interface SysDeptService {
    /**
     * 查询列表
     */
    List<SysDeptEntity> queryList(SearchDeptParam param);

    /**
     * 根据部门ID查询
     */
    SysDeptEntity queryByDeptId(Long deptId);

    /**
     * 保存部门
     */
    void save(SysDeptEntity dept);

    /**
     * 更新部门
     */
    void update(SysDeptEntity dept);

    /**
     * 根据部门Id查询list
     */
    List<Long> queryDetpIdList(long deptId);

    /**
     * 删除部门
     */
    void delete(long deptId);

    /**
     * 根据部门Id查询list是否有子部门
     */
    List<Long> queryDetpIdList(Long[] deptId);

    /**
     * 批量删除部门
     * @param deptId
     */
    void deleteBatch(Long[] deptId);

    /**
     * 根据考勤单Id所选该课学生
     */
    String getDeptGroupNameByAttId(Long attId);
}
