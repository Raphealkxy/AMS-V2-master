package com.eric.modules.ams.param;

import com.eric.common.utils.SearchParam;

/**
 * @author Chen 2018/1/21
 */
public class SearchAmsAttlistParam extends SearchParam {
    private String courseName;
    private String createName;
    private Long userId;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
