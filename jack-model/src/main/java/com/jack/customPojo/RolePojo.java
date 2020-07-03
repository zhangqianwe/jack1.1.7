package com.jack.customPojo;

import lombok.Data;

import java.util.HashMap;

/**
 * @Auther: zhangqianwen
 * @Date: 2020/7/3 14:48
 * @Description:
 */
@Data
public class RolePojo {
    private Integer isHidden;
    private Integer roleId;
    private String roleName;
    private HashMap<String,Object> rules =new HashMap<>();
}
