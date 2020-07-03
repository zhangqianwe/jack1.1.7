package com.jack.jackOnline;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jack.domain.BaseModel;
import lombok.Data;

/**
 * @Auther: zhangqianwen
 * @Date: 2020/6/23 09:42
 * @Description:
 */
@Data
@TableName("sys_menu")
public class SysMenu extends BaseModel {
    //主键
    private Integer id;
    //菜单名称
    private String menuName;

    private String structure;
    //父节点ID
    private Long fatherId;

    //菜单顺序号
    private long menuLevel;

    //节点编码（叶子节点时不为空）
    private String menuCode;

    //1：父节点；0：叶子节点
    private Integer menuType;

    //访问路径（叶子节点时不为空）',
    private String menuUrl;

    //图标
    private String icon;
    //状态（1:正常；0:删除）',
    private Integer status;

    //访问路径
    private String path;

//    @OneToMany(fetch= FetchType.LAZY,cascade= CascadeType.ALL)
//    private List<SysMenu> listMenu = new ArrayList<SysMenu>();


}
