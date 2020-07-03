package com.jack.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: zhangqianwen
 * @Date: 2020/6/23 09:43
 * @Description:
 */
@Data
public class Publicfield implements Serializable {
    private static final long serialVersionUID = 6437114463749744698L;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;
}
