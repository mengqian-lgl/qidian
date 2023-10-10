package com.lgl.qidian.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @auther 刘广林
 */
public class DateUtils {

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getTime(Date date){
        String format = dateFormat.format(date);
        return format;
    }
}
