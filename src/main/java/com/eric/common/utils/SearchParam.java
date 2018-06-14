package com.eric.common.utils;

import com.google.common.base.CaseFormat;
import org.apache.commons.lang.StringUtils;

/**
 * 通用SearchParam
 * @author Chen 2018/1/13
 */
public class SearchParam {
    private int page;//当前页码
    private int limit;//每页条数
    private String sidx;//排序列
    private String  order;//排序方式
    private int offset;//查询起始行


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getSidx() {
        if (StringUtils.isNotBlank(sidx))
            return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, sidx);
        else return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getOffset() {
        return this.offset = (page - 1) * limit;
    }


}
