package com.jack.pojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Auther: zhangqianwen
 * @Date: 2020/7/1 18:59
 * @Description:
 */
public class Test {
    public static void main(String[] args) {
        Field f1 = new Field("tony", 19);
        Field f2 = new Field("jack", 16);
        Field f3 = new Field("tom", 80);
        Field f4 = new Field("jbson", 44);
        Field f5 = new Field("jason", 44);

        List<Field> list = new ArrayList<Field>();
        list.add(f1);
        list.add(f3);
        list.add(f4);
        list.add(f2);
        list.add(f5);
        for (Field o : list) {
            System.out.println(o.getAge() + "-->" + o.getName());
        }
        System.out.println("_________________");
        Collections.sort(list);

        for (Field o : list) {
            System.out.println(o.getAge() + "-->" + o.getName());
        }
    }
}