package com.eric.common.result;

import java.io.Serializable;
import java.util.List;

/**
 * @author Chen 2018/2/23
 */
public class ListQueryResult<T> implements Result, Serializable {
    private List<T> items;
    private String errorMessage;

    public ListQueryResult(List<T> items) {
        if (items == null) {
            throw new IllegalArgumentException("items 参数不能为 null。");
        } else {
            this.items = items;
        }
    }

    public ListQueryResult(String errorMessage) {
        this.errorMessage = errorMessage == null ? "" : errorMessage;
    }

    public List<T> getItems() {
        return this.items;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public boolean isSuccess() {
        return this.items != null;
    }
}