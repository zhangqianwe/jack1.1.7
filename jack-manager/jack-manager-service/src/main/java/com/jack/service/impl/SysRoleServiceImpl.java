package com.jack.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.customPojo.RolePojo;
import com.jack.jackOnline.SysRole;
import com.jack.mapper.SysRoleMapper;
import com.jack.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: zhangqianwen
 * @Date: 2020/7/3 14:35
 * @Description:
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Override
    public List<RolePojo> getRoleList() {
        List<RolePojo> list = new ArrayList<>();
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper();
        queryWrapper.eq("status",1);
        List<SysRole> sysRoles = sysRoleMapper.selectList(queryWrapper);
        for (SysRole sysRole : sysRoles) {
            RolePojo rolePojo = new RolePojo();
            rolePojo.setIsHidden(1);
            rolePojo.setRoleName(sysRole.getRoleName());
            rolePojo.setRoleId(sysRole.getId());
            list.add(rolePojo);
        }
        return list;
    }
}
