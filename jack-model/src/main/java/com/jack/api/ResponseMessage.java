package com.jack.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//@Data

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMessage implements Serializable {
    private static final long serialVersionUID = -96338617749438474L;
    private int code;
    private Object data;
    private String message;
    private Long time=new Date().getTime();

    public ResponseMessage(int code, String message, Object data) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    //根据枚举值初始化返回状态码和message，根据参数data初始化返回data
    public ResponseMessage(ResponseCode code, Object data) {
        this.code = code.getIndex();
        this.data = data;
        this.message = code.getName();
    }

    //根据枚举值初始化返回状态码，根据参数message初始化具体返回信息
    public ResponseMessage(ResponseCode code, String message) {
        this.code = code.getIndex();
        this.message = message;
    }

    //根据枚举值初始化返回状态码和message
    public ResponseMessage(ResponseCode code) {
        this.code = code.getIndex();
        this.message = code.getName();
    }

    public ResponseMessage() {

    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getData() {
        if (this.data==null)
        {
            Map<String,Object> map=new HashMap<>();
            return map;
        }
        return this.data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public Long getTime() {
        return this.time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
