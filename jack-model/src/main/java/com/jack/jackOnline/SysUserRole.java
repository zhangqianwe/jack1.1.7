package com.jack.jackOnline;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jack.domain.BaseModel;
import lombok.Data;

/**
 * @Auther: zhangqianwen
 * @Date: 2020/7/8 16:48
 * @Description:
 */
@Data
@TableName("sys_user_role")
public class SysUserRole extends BaseModel {
    @TableId(value = "ID",type = IdType.AUTO)
    private Integer id;

    private Integer status;
    private Integer user_id;
    private Integer role_id;
}
