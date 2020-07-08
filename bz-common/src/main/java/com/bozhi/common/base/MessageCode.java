package com.bozhi.common.base;

/**
 * Created by zxd on 2020/4/20
 **/
public enum MessageCode {

    REQUEST_SUCCESS(20000,"请求成功!"),
    REMOVE_POSITION_FAILED(3100,"该职位已存在用户，不可删除"),
    SAVE_USER_FAILED(3200,"该用户已存在"),
    REQUEST_EXCEPTION(3300,"系统开小差了"),
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
