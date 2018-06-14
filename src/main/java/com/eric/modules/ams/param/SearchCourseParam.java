package com.eric.modules.ams.param;

import com.eric.common.utils.SearchParam;

/**
 * @author Chen 2018/1/16
 */
public class SearchCourseParam extends SearchParam {
    private String courseName;
    private String teacherName;
    private Long userId;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
