package com.jack.jackOnline;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jack.domain.BaseModel;
import lombok.Data;
/**
 * @Auther: zhangqianwen
 * @Date: 2020/6/17 10:01
 * @Description:
 */
@Data
@TableName("sys_user")
public class SysUser extends BaseModel {

    @TableId(value = "ID",type = IdType.AUTO)
    private Integer id;

    private Integer status;

    private String login_name;
    private String password;

    private String roleCode;

    private String realname;

    private String name;

    private String icon;

    private String guid;

    private Integer onLine;

    private String depDate;


    private String unionid;

    private Integer subscribe;

    private String telphone;

    /**
     * 在职状态(0:离职;1:在职;)
     */
    private Integer serviceStatus ;

    /**
     * 职位类型(1:全职;2:兼职;)
     */
    private Integer serviceType ;

    private String email;


    private Integer sex;

    private Integer deptId;

    private String post;
}
