package com.jack.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseSpecialaMessage implements Serializable {
    private static final long serialVersionUID = 7974214252471721409L;
    private int code;
    private Object data;
    private String msg;
    private int total;
    private Object list;
    private boolean success;
    private long sysTime;
    private String filePath;
    private String path;


}
