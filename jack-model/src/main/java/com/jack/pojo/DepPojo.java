package com.jack.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门实体类
 * @Auther: zhangqianwen
 * @Date: 2020/7/1 17:41
 * @Description:
 */
@Data
public class DepPojo implements Comparable<DepPojo> {
    //主键
    private Integer id;
    //部门名字
    private String name;
    //上级部门
    private Integer pid;
    private String label;
    private Integer level;
    private List<DepPojo> children=new ArrayList<>();

    public DepPojo(Integer id, String name, Integer pid, String label,Integer level) {
        this.id = id;
        this.name = name;
        this.pid = pid;
        this.label = label;
        this.level = level;
    }
    public DepPojo() {
    }

    @Override
    public int compareTo(DepPojo o) {
        if (this.level > o.getLevel()) {
            return (this.level - o.getLevel());
        }
        if (this.level < o.getLevel()) {
            return (this.level - o.getLevel());
        }
        return 0;
    }
}
