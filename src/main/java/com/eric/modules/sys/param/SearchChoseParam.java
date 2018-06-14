package com.eric.modules.sys.param;


import com.eric.common.utils.SearchParam;

/**
 * @author Chen 2018/1/20
 */
public class SearchChoseParam extends SearchParam{
    private Long courseId;
    private String userName;
    private String loginNum;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginNum() {
        return loginNum;
    }

    public void setLoginNum(String loginNum) {
        this.loginNum = loginNum;
    }
}
