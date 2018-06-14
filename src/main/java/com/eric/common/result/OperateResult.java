package com.eric.common.result;

/**
 * @author Chen 2018/2/19
 */
public class OperateResult implements Result {
    private Integer returnValue;//返回值
    private String returnMsg;
    private String errorMessage;//错误信息


    public OperateResult(Integer returnValue,String returnMsg) {
        if(returnValue < 0) {
            throw new IllegalArgumentException("returnValue 参数不能小于 0。");
        } else {
            this.returnValue = returnValue;
            this.returnMsg = returnMsg;
        }
    }
    public OperateResult(String errorMessage) {
        this.errorMessage = errorMessage == null?"":errorMessage;
        this.returnValue = -1;
        this.returnMsg = "";
    }

    public Integer getReturnValue() {
        return this.returnValue;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setReturnValue(Integer returnValue) {
        this.returnValue = returnValue;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * 是否成功
     * @return
     */
    @Override
    public boolean isSuccess() {
        return this.returnValue >= 0;
    }
}
