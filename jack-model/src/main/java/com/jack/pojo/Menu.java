package com.jack.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: zhangqianwen
 * @Date: 2020/6/22 19:05
 * @Description:
 */
@Data
public class Menu {
    private Long id;
    //访问路径
    private String path;
    //资源路径
    private String component;
    //重定向
    private String redirect;
    //菜单名字
    private String name;
    //包含菜单名称.图标ico
    private Object meta;
    //子菜单对象
    private List<Menu> children=new ArrayList<>();

}
