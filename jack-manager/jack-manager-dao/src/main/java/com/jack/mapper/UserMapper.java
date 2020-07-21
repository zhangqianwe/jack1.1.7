package com.jack.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jack.customPojo.Parameter;
import com.jack.jackOnline.SysUser;
import com.jack.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Auther: zhangqianwen
 * @Date: 2020/6/15 15:36
 * @Description:
 */
@Mapper
public interface UserMapper extends BaseMapper<SysUser> {
    Integer selectByPrimaryKey();
    List<SysUser> selectBu();

    List<SysUser> selectPage(Parameter pageParam);
}
