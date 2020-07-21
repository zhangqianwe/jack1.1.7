package com.jack.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.api.ResponseMessage;
import com.jack.customPojo.Parameter;
import com.jack.jackOnline.SysUser;

import java.util.List;
import java.util.Map;

/**
 * @Auther: zhangqianwen
 * @Date: 2020/6/15 15:13
 * @Description:
 */

public interface UserService extends IService<SysUser> {

    IPage<Map<String, Object>> userService(Parameter pageParam);

    IPage selectUserPage(Page page, String normal);

    ResponseMessage selectByUserNameAndPassWord(String userName, String passWord) throws Exception;

    List<SysUser> selectList(Parameter pageParam);
    Integer selectByPrimaryKey();
    List<SysUser> selectBu();

    Page<SysUser> selectPage(Parameter pageParam);
}
