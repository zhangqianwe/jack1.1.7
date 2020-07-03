package com.jack.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.customPojo.RolePojo;
import com.jack.jackOnline.SysRole;

import java.util.List;

/**
 * @Auther: zhangqianwen
 * @Date: 2020/7/3 14:34
 * @Description:
 */
public interface SysRoleService extends IService<SysRole> {
    List<RolePojo> getRoleList();
}
