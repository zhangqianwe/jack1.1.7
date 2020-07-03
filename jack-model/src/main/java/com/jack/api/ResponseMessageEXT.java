package com.jack.api;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//@Data

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMessageEXT implements Serializable {
    private static final long serialVersionUID = 671636452591662211L;
    private int code;
    private Object data;
    private String msg;
    private Boolean success;
    private Long time=new Date().getTime();

    public ResponseMessageEXT(int code, String msg, Object data) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    //根据枚举值初始化返回状态码和msg，根据参数data初始化返回data
    public ResponseMessageEXT(ResponseCode code, Object data) {
        this.code = code.getIndex();
        this.data = data;
        this.msg = code.getName();
    }

    //根据枚举值初始化返回状态码，根据参数msg初始化具体返回信息
    public ResponseMessageEXT(ResponseCode code, String msg) {
        this.code = code.getIndex();
        this.msg = msg;
    }

    //根据枚举值初始化返回状态码和msg
    public ResponseMessageEXT(ResponseCode code) {
        this.code = code.getIndex();
        this.msg = code.getName();
    }

    public ResponseMessageEXT() {

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

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
