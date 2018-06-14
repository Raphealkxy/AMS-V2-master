package com.eric.modules.sys.dao;

import com.eric.common.utils.BaseDao;
import com.eric.modules.ams.param.SearchClassParam;
import com.eric.modules.sys.entity.SysDeptEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SysDeptDao extends BaseDao<SysDeptEntity> {
    /**
     * 根据部门id查询子部门
     */
    List<Long> queryDetpIdList(long deptId);

    /**
     * 根据参数查询【班级】
     * @param param
     * @return
     */
    List<SysDeptEntity> queryClassList(@Param("param") SearchClassParam param);

    /**
     * 根据部门idList查询子部门
     */
    List<Long> querySonDeptList(@Param("ids") List<Long> longs);

    /**
     *
     */
    void deleteBatch(@Param("ids") List<Long> longs);

    /**
     * 根据学生ID查出部门ID列表，distint
     */
    List<Long> selectDeptIdsByStuIds(@Param("studentIds") List<Long> studentIds);

    /**
     * 根据部门IDs查出组名
     */
    String getDeptGroupsNameByIds(@Param("deptIds") List<Long> deptIds);
}