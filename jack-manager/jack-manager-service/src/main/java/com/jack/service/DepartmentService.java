package com.jack.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.jackOnline.Department;
import com.jack.jackOnline.SysUser;
import com.jack.pojo.DepPojo;

import java.util.List;

/**
 * @Auther: zhangqianwen
 * @Date: 2020/6/23 09:58
 * @Description:
 */
public interface DepartmentService extends IService<Department> {


    List<DepPojo> getAllDep(String s);
}
