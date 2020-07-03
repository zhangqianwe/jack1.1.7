package com.jack.jackOnline;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jack.domain.BaseModel;
import lombok.Data;

/**
 * 部门实体类
 * @Auther: zhangqianwen
 * @Date: 2020/7/1 17:41
 * @Description:
 */
@Data
@TableName("department")
public class Department extends BaseModel {
    // 确保反序列化不会出现异常, 该字段只能在当前类中有效, 抽象到父类中是无效的
    private static final long serialVersionUID = 1L;
    //主键
    private Integer deptId;
    //部门名字
    private String name;
    //上级部门
    private Integer pid = null;
    //排名
    private Integer level;
    //简介
    private String remark;
    //1：父节点；0：叶子节点'
    private Integer depType;

    private Integer status=0;


}
