package com.alex.playandroid.net.response;

/**
 * Created by Alex on 2018/6/13.
 */

public class NetResponse<T> {

    /**
     * 0：成功，1：失败
     */
    private int errorCode;

    private String errorMsg;

    private T data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
