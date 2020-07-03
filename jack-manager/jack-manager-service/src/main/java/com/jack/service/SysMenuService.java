package com.jack.service;

import com.jack.pojo.Menu;

import java.util.List;

/**
 * @Auther: zhangqianwen
 * @Date: 2020/6/23 09:58
 * @Description:
 */
public interface SysMenuService {
    List<Menu> getRoutes(String s);
}
