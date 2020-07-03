package com.jack.api;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: zhangqianwen
 * @Date: 2020/6/16 16:02
 * @Description:
 */
@Data
public class PageParam implements Serializable {

    private static final long serialVersionUID = 2819330310166386487L;

    /**
     * 默认第一页
     */
    private Integer pageNum;

    /**
     * 默认每页 20 条
     */
    private Integer pageSize;

    /**
     * 当前偏移量
     */
    private Integer pageCurrent;

    public Integer getPageNum() {
        return pageNum != null ? pageNum : 1;
    }

    public Integer getPageSize() {
        return pageSize != null ? pageSize : 20;
    }

}