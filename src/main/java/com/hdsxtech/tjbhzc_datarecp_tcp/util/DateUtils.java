//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hdsxtech.tjbhzc_datarecp_tcp.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * <B>系统名称：</B>通用系统功能<BR>
 * <B>模块名称：</B>基础通用功能<BR>
 * <B>中文类名：</B>日期时间处理辅助功能<BR>
 * <B>概要说明：</B>进行日期、时间相关处理的辅助功能。<BR>
 *
 * @author 交通运输部规划研究院（邵彧）
 * @since 2010-12-15
 */
public final class DateUtils {
    /** 默认日期格式 */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    /** 默认日期时间格式 */
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /** 默认日期时间戳格式 */
    public static final String DEFAULT_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    /** 日期格式化工具 */
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(
            DEFAULT_DATE_FORMAT);

    /** 日期时间格式化工具 */
    private static final SimpleDateFormat DATETIME_FORMATTER = new SimpleDateFormat(
            DEFAULT_DATETIME_FORMAT);

    /**
     * <B>构造方法</B><BR>
     */
    private DateUtils() {
    }

    /**
     * <B>方法名称：</B>获取当前日期时间<BR>
     * <B>概要说明：</B><BR>
     *
     * @return Date 当前日期时间
     */
    public static Date getCurrentDateTime() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * <B>方法名称：</B>获取当前日期时间戳<BR>
     * <B>概要说明：</B><BR>
     *
     * @return Timestamp 当前日期时间戳
     */
    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * <B>方法名称：</B>获取当前年份<BR>
     * <B>概要说明：</B><BR>
     *
     * @return int 当前年份
     */
    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * <B>方法名称：</B>获取当前月份<BR>
     * <B>概要说明：</B><BR>
     *
     * @return int 当前年份
     */
    public static int getCurrentMonth() {
        return Calendar.getInstance().get(Calendar.MONTH);
    }

    /**
     * <B>方法名称：</B>获取当前季度<BR>
     * <B>概要说明：</B><BR>
     *
     * @return int 当前季度
     */
    public static int getCurrentSeason() {
        int month = getCurrentMonth();
        int season = (month / 3) + 1;
        return season;
    }

    /**
     * <B>方法名称：</B>获取当前日期文本<BR>
     * <B>概要说明：</B><BR>
     *
     * @return String 当前日期文本
     */
    public static String getCurrentDateText() {
        return DATE_FORMATTER.format(getCurrentDateTime());
    }

    /**
     * <B>方法名称：</B>获取当前日期时间文本<BR>
     * <B>概要说明：</B><BR>
     *
     * @return String 当前日期时间文本
     */
    public static String getCurrentDatetimeText() {
        return DATETIME_FORMATTER.format(getCurrentDateTime());
    }

    /**
     * <B>方法名称：</B>获取当前日期时间戳文本<BR>
     * <B>概要说明：</B><BR>
     *
     * @return String 当前时间戳文本
     */
    public static String getCurrentTimestampText() {
        return getCurrentTimestamp().toString();
    }

    /**
     * <B>方法名称：</B>获取当前日期时间自定义格式文本<BR>
     * <B>概要说明：</B><BR>
     *
     * @param format
     *            格式
     * @return String 文本
     */
    public static String getCurrentByFormat(String format) {
        return format(getCurrentDateTime(), format);
    }

    /**
     * <B>方法名称：</B>获取指定日期文本<BR>
     * <B>概要说明：</B><BR>
     *
     * @param date
     *            日期
     * @return String 文本
     */
    public static String formatDate(Date date) {
        return DATE_FORMATTER.format(date);
    }

    /**
     * <B>方法名称：</B>获取指定日期时间文本<BR>
     * <B>概要说明：</B><BR>
     *
     * @param datetime
     *            日期时间
     * @return String 文本
     */
    public static String formatDatetime(Date datetime) {
        return DATETIME_FORMATTER.format(datetime);
    }

    /**
     * <B>方法名称：</B>获取指定日期时间戳文本<BR>
     * <B>概要说明：</B><BR>
     *
     * @param timestamp
     *            日期时间戳
     * @return String 文本
     */
    public static String formatTimestamp(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toString();
    }

    /**
     * <B>方法名称：</B>解析文本为日期<BR>
     * <B>概要说明：</B><BR>
     *
     * @param text
     *            文本
     * @return Date 日期
     * @throws ParseException
     *             解析错误
     */
    public static Date parseToDate(String text) throws ParseException {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        return DATE_FORMATTER.parse(text);
    }

    /**
     * <B>方法名称：</B>解析文本为日期时间<BR>
     * <B>概要说明：</B><BR>
     *
     * @param text
     *            文本
     * @return Date 日期时间
     * @throws ParseException
     *             解析错误
     */
    public static Date parseToDatetime(String text) throws ParseException {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        return DATETIME_FORMATTER.parse(text);
    }

    /**
     * <B>方法名称：</B>解析文本为日期时间戳<BR>
     * <B>概要说明：</B><BR>
     *
     * @param text
     *            文本
     * @return Timestamp 日期时间戳
     */
    public static Timestamp parseToTimestamp(String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        return Timestamp.valueOf(text);
    }

    /**
     * <B>方法名称：</B>根据指定格式，格式化日期时间<BR>
     * <B>概要说明：</B><BR>
     *
     * @param date
     *            日期时间
     * @param format
     *            格式
     * @return String 文本
     */
    public static String format(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * <B>方法名称：</B>根据指定格式，解析日期时间<BR>
     * <B>概要说明：</B><BR>
     *
     * @param text
     *            文本
     * @param format
     *            格式
     * @return Date 日期时间
     * @throws ParseException
     *             解析错误
     */
    public static Date parse(String text, String format) throws ParseException {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(text);
    }

    /**
     * <B>方法名称：</B>创建新的日期时间戳对象<BR>
     * <B>概要说明：</B><BR>
     *
     * @param year
     *            年
     * @return Timestamp 日期时间戳
     */
    public static Timestamp newTimestamp(int year) {
        return newTimestamp(year, 1, 1, 0, 0, 0, 0);
    }

    /**
     * <B>方法名称：</B>创建新的日期时间戳对象<BR>
     * <B>概要说明：</B><BR>
     *
     * @param year
     *            年
     * @param month
     *            月
     * @return Timestamp 日期时间戳
     */
    public static Timestamp newTimestamp(int year, int month) {
        return newTimestamp(year, month, 1, 0, 0, 0, 0);
    }

    /**
     * <B>方法名称：</B>创建新的日期时间戳对象<BR>
     * <B>概要说明：</B><BR>
     *
     * @param year
     *            年
     * @param month
     *            月
     * @param day
     *            日
     * @return Timestamp 日期时间戳
     */
    public static Timestamp newTimestamp(int year, int month, int day) {
        return newTimestamp(year, month, day, 0, 0, 0, 0);
    }

    /**
     * <B>方法名称：</B>创建新的日期时间戳对象<BR>
     * <B>概要说明：</B><BR>
     *
     * @param year
     *            年
     * @param month
     *            月
     * @param day
     *            日
     * @param hour
     *            小时
     * @return Timestamp 日期时间戳
     */
    public static Timestamp newTimestamp(int year, int month, int day, int hour) {
        return newTimestamp(year, month, day, hour, 0, 0, 0);
    }

    /**
     * <B>方法名称：</B>创建新的日期时间戳对象<BR>
     * <B>概要说明：</B><BR>
     *
     * @param year
     *            年
     * @param month
     *            月
     * @param day
     *            日
     * @param hour
     *            小时
     * @param minute
     *            分钟
     * @return Timestamp 日期时间戳
     */
    public static Timestamp newTimestamp(int year, int month, int day,
                                         int hour, int minute) {
        return newTimestamp(year, month, day, hour, minute, 0, 0);
    }

    /**
     * <B>方法名称：</B>创建新的日期时间戳对象<BR>
     * <B>概要说明：</B><BR>
     *
     * @param year
     *            年
     * @param month
     *            月
     * @param day
     *            日
     * @param hour
     *            小时
     * @param minute
     *            分钟
     * @param second
     *            秒
     * @return Timestamp 日期时间戳
     */
    public static Timestamp newTimestamp(int year, int month, int day,
                                         int hour, int minute, int second) {
        return newTimestamp(year, month, day, hour, minute, second, 0);
    }

    /**
     * <B>方法名称：</B>创建新的日期时间戳对象<BR>
     * <B>概要说明：</B><BR>
     *
     * @param year
     *            年
     * @param month
     *            月
     * @param day
     *            日
     * @param hour
     *            小时
     * @param minute
     *            分钟
     * @param second
     *            秒
     * @param nano
     *            毫秒
     * @return Timestamp 日期时间戳
     */
    @SuppressWarnings("deprecation")
    public static Timestamp newTimestamp(Integer year, Integer month,
                                         Integer day, Integer hour, Integer minute, Integer second,
                                         Integer nano) {
        if (year == null) {
            year = getCurrentYear();
        }
        year = year - 1900;
        if (month == null) {
            month = 1;
        }
        if (day == null) {
            day = 1;
        }
        if (hour == null) {
            hour = 0;
        }
        if (minute == null) {
            minute = 0;
        }
        if (second == null) {
            second = 0;
        }
        if (nano == null) {
            nano = 0;
        }
        nano = nano * 1000;
        return new Timestamp(year, (month - 1), day, hour, minute, second, nano);
    }

    /**
     * <B>方法名称：</B>将字符串类型的日期转换为一个Date（java.sql.Date）<BR>
     * <B>概要说明：</B><BR>
     *
     * @param dateString
     *            字符型，需要转换为Date的字符串
     * @return java.sql.Date 转换后日期
     * @throws ParseException
     *             解析错误
     */
    public static java.sql.Date string2Date(String dateString)
            throws ParseException {
        DateFormat dateFormat;
        dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.ENGLISH);
        dateFormat.setLenient(false);
        Date timeDate = dateFormat.parse(dateString); // util类型
        java.sql.Date dateTime = new java.sql.Date(timeDate.getTime()); // sql类型
        return dateTime;
    }

    /**
     * <B>方法名称：</B>将字符串类型的日期转换为一个timestamp（时间戳记java.sql.Timestamp）<BR>
     * <B>概要说明：</B><BR>
     *
     * @param dateString
     *            需要转换为timestamp的字符串
     * @return dataTime timestamp
     * @throws ParseException
     *             解析错误
     */
    public static Timestamp string2Time(String dateString)
            throws ParseException {
        DateFormat dateFormat;
        dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.ENGLISH);
        dateFormat.setLenient(false);
        Date timeDate = dateFormat.parse(dateString);
        Timestamp dateTime = new Timestamp(timeDate.getTime());
        return dateTime;
    }

    /**
     * <B>方法名称：</B>获取指定月份的最大天<BR>
     * <B>概要说明：</B><BR>
     *
     * @param year
     *            年
     * @param month
     *            月
     * @return int 指定月份的最大天
     */
    public static int getMonthMaxDay(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        return cal.getActualMaximum(Calendar.DATE);
    }
}
