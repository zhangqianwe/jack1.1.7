package com.jack.jackOnline;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 角色表
 * @Auther: zhangqianwen
 * @Date: 2020/7/3 14:31
 * @Description:
 */
@Data
@TableName("sys_role")
public class SysRole {

//    id   int auto_increment
//    role_name varchar(32)      null comment '名称',
//    remark    varchar(100)     null comment '备注',
//    status    int(3) default 1 null comment '1 启用 0 禁用'
    @TableId(value = "ID",type = IdType.AUTO)
    private Integer id;

    private String roleName;

    private String remark;

    private Integer status;

}
