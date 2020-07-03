package com.bozhi.common;

/**
 * Created by zxd on 2020/4/20
 **/
public enum MessageCode {

    REQUEST_SUCCESS(20000,"请求成功!"),
    REQUEST_FAILED(3000,"请求失败");


    private Integer code;
    private String message;
    MessageCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
