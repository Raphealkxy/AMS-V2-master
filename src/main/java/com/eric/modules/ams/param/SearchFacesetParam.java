package com.eric.modules.ams.param;

import com.eric.common.utils.SearchParam;

/**
 * @author Chen 2018/1/27
 */
public class SearchFacesetParam extends SearchParam {
    private String deptName;
    private String displayName;
    private String loginNum;//用户账号
    private String userName;//用户名
    private Long facesetId;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLoginNum() {
        return loginNum;
    }

    public void setLoginNum(String loginNum) {
        this.loginNum = loginNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getFacesetId() {
        return facesetId;
    }

    public void setFacesetId(Long facesetId) {
        this.facesetId = facesetId;
    }
}
