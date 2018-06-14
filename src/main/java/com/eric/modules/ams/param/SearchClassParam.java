package com.eric.modules.ams.param;

import com.eric.common.utils.SearchParam;

/**
 * @author Chen 2018/1/13
 */
public class SearchClassParam extends SearchParam {
    public String deptName;
    private Integer deptType;
    private String parentName;

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

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
