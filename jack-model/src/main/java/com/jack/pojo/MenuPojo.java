package com.jack.pojo;

import lombok.Data;

import java.util.List;

/**
 * @Auther: zhangqianwen
 * @Date: 2020/7/7 10:18
 * @Description:
 */
@Data
public class MenuPojo {
    private boolean alwaysShow;
    private List<MenuChildren> children;
    private Meta meta;
    private String name;
    private String path;
    private String redirect;




}
