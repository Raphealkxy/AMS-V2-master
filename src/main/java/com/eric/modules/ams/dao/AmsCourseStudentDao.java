package com.eric.modules.ams.dao;

import com.eric.common.utils.BaseDao;
import com.eric.modules.ams.entity.AmsCourseEntity;
import com.eric.modules.ams.entity.AmsCourseStudentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AmsCourseStudentDao extends BaseDao<AmsCourseStudentEntity> {
    /**
     * 查询课程关联学生ID
     */
    List<Long> queryStuIdByCourseId(@Param("courseId") Long courseId);
}