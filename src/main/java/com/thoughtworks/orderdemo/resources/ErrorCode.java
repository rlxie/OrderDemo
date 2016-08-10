package com.thoughtworks.orderdemo.resources;

/**
 * Created by rlxie on 8/9/16.
 */
public class ErrorCode {

    private int errorCode;

    private Object resp;

    public ErrorCode(int errorCode, Object resp){
        this.errorCode = errorCode;
        this.resp = resp;
    }

    public ErrorCode(Object resp){
        this.resp = resp;
    }

    public ErrorCode(){}

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setResp(Object resp) {
        this.resp = resp;
    }

    public Object getResp() {
        return resp;
    }
}
