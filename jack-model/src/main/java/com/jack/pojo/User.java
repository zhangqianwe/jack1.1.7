package com.jack.pojo;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jack.domain.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: zhangqianwen
 * @Date: 2020/6/15 15:36
 * @Description:
 */
@Data
public class User extends BaseModel{
    // 确保反序列化不会出现异常, 该字段只能在当前类中有效, 抽象到父类中是无效的
    private static final long serialVersionUID = 1L;

    private String password;

    private String username;



}
