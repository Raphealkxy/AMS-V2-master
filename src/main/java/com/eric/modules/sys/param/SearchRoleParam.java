package com.eric.modules.sys.param;

import com.eric.common.utils.SearchParam;

/**
 * @author Chen 2018/1/14
 */
public class SearchRoleParam extends SearchParam {
    private Long createUserId;
    private String roleName;

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
