package com.xxxx.server.utils;

import org.apache.commons.lang.time.DateUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {
    public static String getCurrentDateStr(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date dateTime = new Date(System.currentTimeMillis());
        String date = formatter.format(dateTime);
        return date;
    }

    public static Date geQuerytCurrentDate() throws ParseException {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Date dateTime = new Date(System.currentTimeMillis());
        String defaultTime = formatter.format(dateTime);
        Date date = formatter.parse(defaultTime);
        return date;
    }

    public static Date ConvertTimeToLong(String time) throws ParseException {
        Date parse = DateUtils.parseDate(time, new String[]{"yyyy-MM-dd"});
        return parse;
    }

    public static String getCurrentDateTimeStr(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateTime = new Date(System.currentTimeMillis());
        String defaultTime = formatter.format(dateTime);
        return defaultTime;
    }
    public static String getQueryCurrentTimeStr(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Date dateTime = new Date(System.currentTimeMillis());
        String defaultTime = formatter.format(dateTime);
        return defaultTime;
    }
    public static String getRunAutoTestHistoryDateTimeStr(int duration){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyyMMddHHmmss");
        Date dateTime = new Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateTime);
        cal.add(Calendar.SECOND, duration);
        dateTime = cal.getTime();
        String defaultTime = formatter.format(dateTime);
        return defaultTime;
    }

    public static Timestamp getCurrentDateTime() throws ParseException {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date dateTime = new Date(System.currentTimeMillis());
        String defaultTime = formatter.format(dateTime) + " 00:00:00";
        Date date = formatter.parse(defaultTime);
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp;
    }

    public static String DayForWeek(String pTime) throws Throwable {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date tmpDate = format.parse(pTime);
        Calendar cal = Calendar.getInstance();
        String[] weekDays = { "7", "1", "2", "3", "4", "5", "6" };
        try {
            cal.setTime(tmpDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static Timestamp getCurrentDateAndTime() throws ParseException {
        Timestamp timestamp = null;
        try{
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateTime = new Date(System.currentTimeMillis());
            String defaultTime = formatter.format(dateTime);
            Date date = formatter.parse(defaultTime);
            timestamp = new Timestamp(date.getTime());
        }catch (Exception ex){

        }
        return timestamp;
    }

}
