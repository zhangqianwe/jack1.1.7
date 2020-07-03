package com.jack.api;

import lombok.Data;

import java.io.Serializable;

/*
分页实体
 */
@Data
public class DataRows   implements Serializable {


    private static final long serialVersionUID = 7360043169982210319L;
    //分页数据
    private Object rows;
    //总条数
    private int totalRowCount;

    public Object getRows() {
        return rows;
    }

    public void setRows(Object rows) {
        this.rows = rows;
    }

    public int getTotalRowCount() {
        return totalRowCount;
    }

    public void setTotalRowCount(int totalRowCount) {
        this.totalRowCount = totalRowCount;
    }
}
