package com.eric.modules.ams.param;

import com.eric.common.utils.SearchParam;

/**
 * @author Chen 2018/1/22
 */
public class SearchAttDetallParam extends SearchParam {
    private String loginNum;
    private String userName;
    private Long attlistId;
    private Long userId;
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

    public Long getAttlistId() {
        return attlistId;
    }

    public void setAttlistId(Long attlistId) {
        this.attlistId = attlistId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
