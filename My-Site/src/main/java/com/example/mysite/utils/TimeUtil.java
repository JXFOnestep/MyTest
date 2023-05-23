package com.example.mysite.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Xufeng Jiang
 * @date 2023年04月19日 20:45
 * @description
 */
public class TimeUtil {
    private DateTimeFormatter dateTimeFormatter;
    public TimeUtil() {
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:MM");
    }


    public static String getTime() {
        TimeUtil timeUtil = new TimeUtil();
        return timeUtil.dateTimeFormatter.format(LocalDateTime.now());
    }

    public static void main(String[] args) {
        System.out.println(TimeUtil.getTime());
    }
}
