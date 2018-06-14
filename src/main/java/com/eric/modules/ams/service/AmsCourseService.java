package com.eric.modules.ams.service;

import com.eric.common.utils.Res;
import com.eric.modules.ams.entity.AmsCourseEntity;
import com.eric.modules.ams.param.SearchCourseParam;
import com.eric.modules.sys.param.SearchChoseParam;

/**
 * @author Chen 2018/1/16
 */
public interface AmsCourseService {

    /**
     * 列表查询
     */
    Res queryList(SearchCourseParam param);

    /**
     * 保存课程
     */
    void save(AmsCourseEntity courseEntity);

    /**
     * 课程信息信息
     */
    AmsCourseEntity queryByCourseId(Long courseId);

    /**
     * 更新
     */
    void update(AmsCourseEntity courseEntity);

    /**
     * 批量删除
     */
    void deleteBatch(Long[] courseIds);

    /**
     * 查询已绑定学生
     */
    Res queryBindedStudent(SearchChoseParam param);

    /**
     * 解绑
     */
    Res unbind(Long courseId, Long[] userIds);

    /**
     * 查询未绑定改课程的学生
     */
    Res queryUnBindedStudent(SearchChoseParam param);

    Res bind(Long courseId, Long[] userIds);

    /**
     * 根据老师ID查询课程
     */
    Res selectCourseByTeacherId(SearchCourseParam param,Long teacherId);
}
