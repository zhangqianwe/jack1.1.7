package com.jack.customPojo;

import com.jack.api.PageParam;
import lombok.Data;

import java.util.List;

/**
 * @Auther: zhangqianwen
 * @Date: 2020/7/1 17:22
 * @Description:
 */
@Data
public class Parameter extends PageParam {
    private String deptId;
    private String status;
    private String name;
    private Integer pid;
    private Integer level;

    private String password;
    private String realname;
    private String login_name;

    private String roleIds;
    private String telphone;
    private String email;
    private String post;
    private Integer sex;
    private List<Integer> roleId;

    private Integer sysRoleId;

    private String userIds;



    private String roleName;
}
