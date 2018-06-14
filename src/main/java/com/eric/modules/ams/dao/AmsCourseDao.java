package com.eric.modules.ams.dao;

import com.eric.common.utils.BaseDao;
import com.eric.modules.ams.entity.AmsCourseEntity;
import com.eric.modules.sys.entity.SysUserEntity;
import com.eric.modules.sys.param.SearchChoseParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AmsCourseDao extends BaseDao<AmsCourseEntity> {

    /**
     * 查询已绑定学生
     */
    List<SysUserEntity> queryBindedStudent(@Param("param") SearchChoseParam param);

    /**
     * 查询绑定该课程的学生Id
     */
    List<Long> queryBindedStudentIds(@Param("courseId") Long courseId);

}