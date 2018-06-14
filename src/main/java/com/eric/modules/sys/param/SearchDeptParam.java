package com.eric.modules.sys.param;

import com.eric.common.utils.SearchParam;

/**
 * @author Chen 2018/1/13
 */
public class SearchDeptParam extends SearchParam {
    public String deptName;
    private Integer deptType;

    public Integer getDeptType() {
        return deptType;
    }

    public void setDeptType(Integer deptType) {
        this.deptType = deptType;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
