package com.jack.utils;

/**
 * @Auther: zhangqianwen
 * @Date: 2020/7/1 18:49
 * @Description:
 */

public class Sort  implements Comparable<Sort> {
    //主键
    private Integer id;
    //部门名字
    private String name;
    //上级部门
    private Integer pid;
    private String label;
    private Integer level;


    @Override
    public int compareTo(Sort o) {
        if (this.level > o.getLevel()) {
            return (this.level - o.getLevel());
        }
        if (this.level < o.getLevel()) {
            return (this.level - o.getLevel());
        }
        return 0;
}

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
