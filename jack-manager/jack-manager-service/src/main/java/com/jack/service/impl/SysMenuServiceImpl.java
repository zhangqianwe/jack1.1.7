package com.jack.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.jackOnline.SysMenu;
import com.jack.mapper.SysMenuMapper;
import com.jack.pojo.Menu;
import com.jack.pojo.Meta;
import com.jack.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Auther: zhangqianwen
 * @Date: 2020/6/23 09:58
 * getParentNode 获取父节点
 * @Description:
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<Menu> getRoutes(String role) {
        //拿到所有菜单
        List<SysMenu> status = sysMenuMapper.selectList(new QueryWrapper<SysMenu>().eq("status", 1));
        //1先拿到所有的父节点
        List<Menu> parentlist = getParentNode(status, 1);
        List<SysMenu> childlist = status.stream().filter(SysMenu -> SysMenu.getMenuType() == 0).collect(Collectors.toList());
        System.out.println("父节点信息" + parentlist.toString());
        System.out.println("子节点信息" + childlist.toString());

        //根据父节点拿到子节点
        getChildNode(parentlist, childlist);


        return parentlist;
    }

    /**
     * @param parentlist 父节点信息
     * @param status     全部信息
     * @return
     */
    private void getChildNode(List<Menu> parentlist, List<SysMenu> status) {
        System.out.println(1001L == 1001L);
        //循环父节点
        parentlist.stream().forEach(parent -> {
            //找到所有子节点
            List<SysMenu> collect = status.stream().filter(SysMenu ->
                    SysMenu.getFatherId().longValue() == parent.getId().longValue()
            ).collect(Collectors.toList());
            //变形
            if (collect.size() > 0) {
                List<Menu> list = new ArrayList<>();
                collect.stream().forEach(e -> {
                    Menu menu = new Menu();
                    menu.setName(e.getMenuName());
                    menu.setComponent(e.getMenuUrl());
                    menu.setPath(e.getPath());
                    Meta meta = new Meta();
                    meta.setIcon(e.getIcon());
                    meta.setTitle(e.getMenuName());
                    menu.setMeta(meta);
                    menu.setId(e.getId().longValue());
                    list.add(menu);
                });
                parent.setChildren(list);
                getChildNode(parent.getChildren(), status);
            }

        });
    }

    private List<Menu> getParentNode(List<SysMenu> status, Integer type) {
        List<Menu> menuList = new ArrayList<>();
        List<SysMenu> collect = status.stream().filter(SysMenu -> SysMenu.getMenuType() == type).collect(Collectors.toList());
        collect.forEach(e -> {
            Menu menu = new Menu();
            menu.setName(e.getMenuName());
            menu.setComponent(e.getMenuUrl());
            menu.setPath(e.getPath());
            Meta meta = new Meta();
            meta.setIcon(e.getIcon());
            meta.setTitle(e.getMenuName());
            menu.setMeta(meta);
            menu.setId(e.getId().longValue());
            menuList.add(menu);
        });
        return menuList;
    }
}
