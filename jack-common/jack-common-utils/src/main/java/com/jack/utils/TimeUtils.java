package com.jack.utils;

import java.util.Calendar;

/**
 * @Auther: zhangqianwen
 * @Date: 2020/7/3 13:21
 * @Description:
 */
public class TimeUtils {
    public static String getSysYear() {
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        return year;
    }
}
