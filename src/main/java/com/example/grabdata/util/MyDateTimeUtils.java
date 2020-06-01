package com.example.grabdata.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author admin
 * @date 2019/5/16 0:08
 */
public class MyDateTimeUtils {

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static Object format(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        String dateTime = formatter.format(time);
        return dateTime;
    }

    public static String getMinusDay(int day){
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.DAY_OF_MONTH,-day);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return format.format(calendar.getTime());
    }

    /**
     * 将世界标准时间转为正常格式，并往前2小时
     * @param dateParam 世界标准时间
     * @return
     */
    public static String getMinusHour(String dateParam){
        Date date = MyDateTimeUtils.transFromUTC(dateParam);
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY)-1);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return format.format(calendar.getTime());
    }

    public static long querySeconds(String start,String end){
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        try {
            Date s = format.parse(start);
            Date e = format.parse(end);
            long re = e.getTime()-s.getTime();
            System.out.println(re / 1000);
            return re/1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static Date transFromUTC(String param){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            if(param.split(":").length<3){
                param = param+":00";
            }
            Date date = format.parse(param);
            SimpleDateFormat f2 = new SimpleDateFormat(DATE_FORMAT);
            String ne = f2.format(date);
            return f2.parse(ne);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String minusHour = MyDateTimeUtils.getMinusHour("2020-05-29T12:11:03");
        System.out.println(minusHour);
    }
}
