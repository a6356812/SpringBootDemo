package com.bozhi.common;

import java.io.Serializable;

/**
 * Created by zxd on 2020/4/20
 **/
public class Result<T> implements Serializable {

    /**
     * 成功:success 失败:fail
     */
    private String status;
    private Integer code;
    private String message;
    private T data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
