package com.eric.modules.sys.param;

import com.eric.common.utils.SearchParam;

/**
 * 用户搜索参数
 * @author Chen 2018/1/10
 */
public class SearchSysUserParam extends SearchParam {
    private String loginNum;
    private Integer userType;
    private String userName;
    private Long deptId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getLoginNum() {
        return loginNum;
    }

    public void setLoginNum(String loginNum) {
        this.loginNum = loginNum;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
}
