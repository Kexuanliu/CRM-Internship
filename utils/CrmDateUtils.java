package com.xuebei.crm.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Rong Weicheng
 */
public class CrmDateUtils {

    public static Date getTodayJournalFirstTime() {
        return getJournalDayFirstTime(new Date());
    }

    /**
     * 获取日志所属日期的开始时间.
     * 日志所属日期计算：当天早上08:30到第二天早上08:30。
     * @param date 目标日期时间
     * @return 日志所属日期的开始时间
     */
    public static Date getJournalDayFirstTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (isBeforeHalfPassEight(calendar)) {
            calendar.add(Calendar.DAY_OF_YEAR, -1);
        }
        setAtHalfPassEight(calendar);
        return calendar.getTime();
    }

    public static void setAtHalfPassEight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    /**
     * 获取日志所属日期的结束时间.
     * 日志所属日期计算：当天早上08:30到第二天早上08:30。
     * @param date 目标日期时间
     * @return 日志所属日期的结束时间
     */
    public static Date getJournalDayLastTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (isAfterHalfPassEight(calendar)) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        setAtHalfPassEight(calendar);
        return calendar.getTime();
    }

    public static boolean isAfterHalfPassEight(Calendar calendar) {
        return calendar.get(Calendar.HOUR_OF_DAY) > 8
                || (calendar.get(Calendar.HOUR_OF_DAY) == 8 && calendar.get(Calendar.MINUTE) >= 30);
    }

    public static boolean isBeforeHalfPassEight(Calendar calendar) {
        return calendar.get(Calendar.HOUR_OF_DAY) < 8
                || (calendar.get(Calendar.HOUR_OF_DAY) == 8 && calendar.get(Calendar.MINUTE) < 30);
    }


    /**
     * 一天的毫秒数
     */
    public static final long ONE_DAY_MILL_SECONDS = 86400000L;

    /**
     * 完整时间 yyyy-MM-dd HH:mm:ss
     */
    public static final String SIMPLE = "yyyy-MM-dd HH:mm:ss";

    /**
     * 年月日 yyyy-MM-dd
     */
    public static final String DT_SIMPLE = "yyyy-MM-dd";

    /**
     * 年月日 yyyy/MM/dd 中间为斜线
     */
    public static final String DT_SIMPLE_SLASH = "yyyy/MM/dd";

    /**
     * 中文习惯年月日 yyyy年MM月dd日
     */
    public static final String DT_SIMPLE_CHINESE = "yyyy年MM月dd日";

    /**
     * 中文习惯年月日 MM月dd日HH时mm分
     */
    public static final String DT_LONG_CHINESE = "MM月dd日HH时mm分";

    /**
     * 短日期格式 yyyyMMdd
     */
    public static final String DT_SHORT = "yyyyMMdd";

    /**
     * 长时间格式 yyyyMMddHHmmss
     */
    public static final String DT_LONG = "yyyyMMddHHmmss";

    /**
     * 时间格式 HH:mm:ss
     */
    public static final String HMS_FORMAT = "HH:mm:ss";

    /**
     * 时间格式 HH:mm
     */
    public static final String HM_FORMAT = "HH:mm";

    /**
     * 月日时分格式 MM.dd HH:mm
     */
    public static final String MDHM_FORMAT = "MM.dd HH:mm";

    /**
     * 时间格式 HH
     */
    public static final String HH_FORMAT = "HH";

    /**
     * 短时间格式 yyyy-MM-dd HH:mm
     */
    public static final String SIMPLE_FORMAT = "yyyy-MM-dd HH:mm";

    /**
     * 日志打印时间格式 yyyy-MM-dd HH:mm:ss,sss
     */
    public static final String LOG_HMSS_FORMAT = "yyyy-MM-dd HH:mm:ss,SSS";

    /**
     * 长时间格式 yyyyMMddHHmmssS
     */
    public static final String DT_LONG_MILL = "yyyyMMddHHmmssS";

    /**
     * 一分钟的毫秒数
     */
    public static final long ONE_MINUTE = 60000L;

    /**
     * 一个小时的毫秒数
     */
    public static final long ONE_HOUR = 3600000L;

    /**
     * 一天的毫秒数
     */
    public static final long ONE_DAY = 86400000L;

    /**
     * 一周的毫秒数
     */
    public static final long ONE_WEEK = 604800000L;

    public static final String DAY_START_TIME = "00:00:00";

    public static final String DAY_END_TIME = "23:59:59";

    public static final List<String> DAY_LABELS = Arrays.asList("00:00-06:00", "06:00-08:00", "08:00-10:00", "10:00-12:00", "12:00-14:00",
            "14:00-16:00", "16:00-18:00", "18:00-20:00", "20:00-22:00", "22:00-24:00");

    public static final List<String> WEEK_LABELS = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");

    public static final String WEEK_FORMAT = "EEEE";

    private static final String ONE_SECOND_AGO_ZH = "秒前";
    private static final String ONE_MINUTE_AGO_ZH = "分钟前";
    private static final String ONE_HOUR_AGO_ZH = "小时前";
    private static final String ONE_DAY_AGO_ZH = "天前";
    private static final String ONE_MONTH_AGO_ZH = "月前";
    private static final String ONE_YEAR_AGO_ZH = "年前";
    private static final String YESTERDAY_ZH = "昨天";

    private static final String ONE_SECOND_AGO_EN = "one second ago";
    private static final String SECONDS_AGO_EN = " seconds ago";
    private static final String ONE_MINUTE_AGO_EN = "one minute ago";
    private static final String MINUTES_AGO_EN = " minutes ago";
    private static final String ONE_HOUR_AGO_EN = "one hour ago";
    private static final String HOURS_AGO_EN = " hours ago";
    private static final String ONE_DAY_AGO_EN = "one day ago";
    private static final String DAYS_AGO_EN = " days ago";
    private static final String ONE_MONTH_AGO_EN = "one month ago";
    private static final String MONTHS_AGO_EN = " months ago";
    private static final String ONE_YEAR_AGO_EN = "one year ago";
    private static final String YEARS_AGO_EN = " years ago";
    private static final String YESTERDAY_EN = "Yesterday";

    private static final Locale LOCALE_EN = new Locale("en");


    /**
     * 时间格式输出
     *
     * @param date   时间对象
     * @param format 格式
     * @return 时间字符串
     */
    public static final String format(Date date, String format) {
        if (date == null) {
            return "";
        }

        return getFormat(format).format(date);
    }

    /**
     * 时间字符串解析
     *
     * @param format     格式
     * @param stringDate 时间
     * @return 时间对象
     */
    public static final Date parse(String format, String stringDate) {
        if (stringDate == null) {
            return null;
        }

        return innerParse(format, stringDate);
    }

    /**
     * 时间字符解析
     *
     * @param stringDate 时间字符串
     * @return 时间对象
     */
    private static Date innerParse(String format, String stringDate) {
        Date date = null;

        try {
            date = getFormat(format).parse(stringDate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return date;
    }


    /**
     * 获取当前天的最大时间如:2015-09-18 23:59:59
     *
     * @param date 日期
     * @return 返回当前天的最大时间
     */
    public static Date endOfADay(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DT_SIMPLE);
        try {
            Date time = sdf.parse(sdf.format(date));
            Calendar cal = Calendar.getInstance();
            cal.setTime(time);
            return new Date(cal.getTimeInMillis() + ONE_DAY_MILL_SECONDS - 1);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取系统当前时间
     * <p>为后期集群拓展,时间获取一致性做准备</p>
     *
     * @return
     */
    public static Date getNowDate() {
        return new Date();
    }

    public static String relativeTimeParse(Date date, Locale locale) {
        return LOCALE_EN.equals(locale) ? relativeTimeParse4EN(date) : relativeTimeParse(date);
    }

    /**
     * 距离时间
     *
     * @param date 当前时间
     * @return 距离时间
     */
    public static String relativeTimeParse(Date date) {
        long delta = System.currentTimeMillis() - date.getTime();

        if (delta < 1L * ONE_MINUTE) {
            long seconds = toSeconds(delta);
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO_ZH;
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO_ZH;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO_ZH;
        }
        if (delta < 48L * ONE_HOUR) {
            return YESTERDAY_ZH;
        }
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO_ZH;
        }
        if (delta < 12L * 4L * ONE_WEEK) {
            long months = toMonths(delta);
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO_ZH;
        } else {
            long years = toYears(delta);
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO_ZH;
        }
    }

    public static String parseDuration(Integer duration, Locale locale) {

        return LOCALE_EN.equals(locale) ? parseENDuration(duration) : parseCNDuration(duration);

    }


    public static String parseENDuration(Integer duration) {
        String length = "";

        if (duration == null) {
            return length;
        }

        if (duration > 60) {
            Integer h = (duration / 60);
            length = h > 1 ? h + " hours" : h + " hour";
            duration %= 60;
        }

        if (duration != 0) {
            length += " and " + duration + " minutes";
        }

        return length;
    }


    public static String parseCNDuration(Integer duration) {
        String length = "";

        if (duration == null) {
            return length;
        }
        if (duration > 60) {
            length = (duration / 60) + "小时";
            duration %= 60;
        }
        if (duration != 0) {
            length += duration + "分钟";
        }

        return length;
    }

    /**
     * 距离时间英文版
     *
     * @param date 当前时间
     * @return 距离时间
     */
    public static String relativeTimeParse4EN(Date date) {
        long delta = System.currentTimeMillis() - date.getTime();

        if (delta < 1L * ONE_MINUTE) {
            long seconds = toSeconds(delta);
            return seconds <= 0 ? ONE_SECOND_AGO_EN : seconds + SECONDS_AGO_EN;
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return minutes <= 0 ? ONE_MINUTE_AGO_EN : minutes + MINUTES_AGO_EN;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return hours <= 0 ? ONE_HOUR_AGO_EN : hours + HOURS_AGO_EN;
        }
        if (delta < 48L * ONE_HOUR) {
            return YESTERDAY_EN;
        }
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            return days <= 0 ? ONE_DAY_AGO_EN : days + DAYS_AGO_EN;
        }
        if (delta < 12L * 4L * ONE_WEEK) {
            long months = toMonths(delta);
            return months <= 0 ? ONE_MONTH_AGO_EN : months + MONTHS_AGO_EN;
        } else {
            long years = toYears(delta);
            return years <= 0 ? ONE_YEAR_AGO_EN : years + YEARS_AGO_EN;
        }
    }

    /**
     * 获取当年最后一天日期
     *
     * @return Date
     */
    public static Date getCurrYearLast() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        int year = Integer.parseInt(sdf.format(date));
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }

    public static Date calDateAfterDays(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    public static Date calDateAfterDays(Date startDate, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    public static Date getCurrMonthFirstTime() {
        Calendar calendar = Calendar.getInstance();
        return getThisMonthFirstTime(calendar);
    }

    public static Date getThisMonthFirstTime(Calendar targetCalendar) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(targetCalendar.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return getFirstTimeOfDay(calendar);
    }

    public static Date getFirstTimeOfDay(Calendar nowCalendar) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowCalendar.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
        return calendar.getTime();
    }

    public static Date getCurrMonthLastTime() {
        Calendar calendar = Calendar.getInstance();
        return getThisMonthLastTime(calendar);
    }

    public static Date getThisMonthLastTime(Calendar targetCalendar) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(targetCalendar.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return getLastTimeOfDay(calendar);
    }

    public static Date getLastTimeOfDay(Calendar targetCalendar) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(targetCalendar.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
        return calendar.getTime();
    }

    public static Date getMondayFirstTimeInCurrWeek() {
        Calendar calendar = Calendar.getInstance();
        if (Calendar.SUNDAY == calendar.get(Calendar.DAY_OF_WEEK)) {
            calendar.add(Calendar.DAY_OF_YEAR, -7);
        }
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return getFirstTimeOfDay(calendar);
    }

    public static Date getSundayLastTimeInCurrWeek() {
        Calendar calendar = Calendar.getInstance();
        if (Calendar.SUNDAY != calendar.get(Calendar.DAY_OF_WEEK)) {
            calendar.add(Calendar.DAY_OF_YEAR, 7);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        }
        return getLastTimeOfDay(calendar);
    }

    /**
     * 私有构造器
     *
     * @param format 格式串
     * @return 格式对象
     */
    private static final DateFormat getFormat(String format) {
        return new SimpleDateFormat(format);
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }

    public static boolean isTheDayBefore(Date dateToCompare, Date targetDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateToCompare);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        int yesterdayDate = calendar.get(Calendar.DAY_OF_YEAR);
        int yesterdayYear = calendar.get(Calendar.YEAR);
        calendar.setTime(targetDate);
        return yesterdayDate == calendar.get(Calendar.DAY_OF_YEAR)
                && yesterdayYear == calendar.get(Calendar.YEAR);
    }

    public static String getWeekdayOfCN(Integer weekday) {
        switch (weekday) {
            case 1:
                return "一";
            case 2:
                return "二";
            case 3:
                return "三";
            case 4:
                return "四";
            case 5:
                return "五";
            case 6:
                return "六";
            case 7:
                return "日";
            default:
                return "";
        }
    }

    public static int calDaysAfterToday(String dateStr) {
        String todayStr = format(new Date(), DT_SIMPLE);
        Date startOfToday = parse(DT_SIMPLE, todayStr);
        Date compareDate = parse(DT_SIMPLE, dateStr);

        return calDaysDiff(compareDate, startOfToday);
    }

    public static int calDaysBeforeToday(String dateStr) {
        String todayStr = format(new Date(), DT_SIMPLE);
        Date startOfToday = parse(DT_SIMPLE, todayStr);
        Date compareDate = parse(DT_SIMPLE, dateStr);

        return calDaysDiff(startOfToday, compareDate);
    }

    private static int calDaysDiff(Date laterDate, Date earlierDate) {
        if (laterDate != null && earlierDate != null) {
            return (int) (laterDate.getTime() - earlierDate.getTime()) / (24 * 3600 * 1000);
        } else {
            return -1;
        }
    }

    public static Date calDateBeforerDays(Date startDate, Integer startInDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE, -1 * startInDays);
        return calendar.getTime();
    }


    /**
     * 返回yyyyMMdd整数型
     *
     * @return
     */
    public static Integer getShortDate(Date input) {
        String tmp = format(input, DT_SHORT);
        if (StringUtils.isNotBlank(tmp)) {
            return Integer.valueOf(tmp);
        } else {
            return null;
        }
    }


    /**
     * 获取传入天数和基准线之间的星期数
     *
     * @return
     */
    public static Integer getAbstractWeek(Date input) {
        Date zero = new Date(631123200);
        int days = getTimeDistance(zero, input);
        return days / 7;
    }

    /**
     * 返回yyyyMM整数型
     *
     * @return
     */
    public static Integer getShortMonthDate(Date input) {
        Integer day = getShortDate(input);
        return day / 100;
    }

    /**
     * 获取两个时间之间的天数
     *
     * @return
     */
    public static int getTimeDistance(Date beginDate, Date endDate) {
        Calendar beginCalendar = Calendar.getInstance();
        beginCalendar.setTime(beginDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        long beginTime = beginCalendar.getTime().getTime();
        long endTime = endCalendar.getTime().getTime();
        int betweenDays = (int) ((endTime - beginTime) / (1000 * 60 * 60 * 24));//先算出两时间的毫秒数之差大于一天的天数
        endCalendar.add(Calendar.DAY_OF_MONTH, -betweenDays);//使endCalendar减去这些天数，将问题转换为两时间的毫秒数之差不足一天的情况
        endCalendar.add(Calendar.DAY_OF_MONTH, -1);//再使endCalendar减去1天
        if (beginCalendar.get(Calendar.DAY_OF_MONTH) == endCalendar.get(Calendar.DAY_OF_MONTH))//比较两日期的DAY_OF_MONTH是否相等
            return betweenDays + 1;    //相等说明确实跨天了
        else
            return betweenDays + 0;    //不相等说明确实未跨天
    }
}
