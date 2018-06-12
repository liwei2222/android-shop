package com.app.androidshop.controller;

import java.io.Serializable;

public class Response<T> implements Serializable {
    private static final long  serialVersionUID = 2757435524710287976L;

    private String             result_code;
    private String             result_message;
    private T                  data;
    //    // 扩展信息
    private Object             ext;

    public Response() {
    }

    public Response(String errCode, String errorMessage) {
        this.result_code = errCode;
        this.result_message = errorMessage;
    }

    public Response(String result_code, String result_message, T data) {
        this.result_code = result_code;
        this.result_message = result_message;
        this.data = data;
    }

    //    public boolean isSuccess() {
    //        return StringUtils.equals(this.getResult_code(), SUCCESS);
    //    }


    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getResult_message() {
        return result_message;
    }

    public void setResult_message(String result_message) {
        this.result_message = result_message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Object getExt() {
        return ext;
    }

    public void setExt(Object ext) {
        this.ext = ext;
    }

    //    public Object getExt() {
    //        return ext;
    //    }
    //
    //    public void setExt(Object ext) {
    //        this.ext = ext;
    //    }

}
