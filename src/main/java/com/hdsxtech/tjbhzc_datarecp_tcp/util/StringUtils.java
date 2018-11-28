//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hdsxtech.tjbhzc_datarecp_tcp.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * <B>系统名称：</B>通用系统功能<BR>
 * <B>模块名称：</B>基础通用功能<BR>
 * <B>中文类名：</B>字符串处理辅助功能<BR>
 * <B>概要说明：</B>进行字符串相关处理的辅助功能。<BR>
 *
 * @author 交通运输部规划研究院（邵彧）
 * @since 2010-12-17
 */
public final class StringUtils {
    /** 随即字符字典 */
    public static final String RANDOM_CHAR_DICT = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * <B>构造方法</B><BR>
     */
    private StringUtils() {
    }

    /**
     * <B>方法名称：</B>文本空判断<BR>
     * <B>概要说明：</B>判断指定文本内容是否为null或空白。<BR>
     *
     * @param str
     *            文本
     * @return boolean true:文本为空
     */
    public static boolean isBlank(String str) {
        if (str == null || str.trim().length() < 1) {
            return true;
        }
        return false;
    }

    /**
     * <B>方法名称：</B>判断是否为整数<BR>
     * <B>概要说明：</B><BR>
     *
     * @param text
     *            文本
     * @return boolean 是否为整数
     */
    public static boolean isInteger(String text) {
        return isMatch(text, "^\\d+$");
    }

    /**
     * <B>方法名称：</B>判断是否为定长整数<BR>
     * <B>概要说明：</B><BR>
     *
     * @param text
     *            文本
     * @param length
     *            长度
     * @return boolean 是否为整数
     */
    public static boolean isInteger(String text, int length) {
        return isMatch(text, "^\\d{" + length + "}$");
    }

    /**
     * <B>方法名称：</B>判断是否为日期时间文本<BR>
     * <B>概要说明：</B><BR>
     *
     * @param text
     *            文本
     * @return boolean 是否为日期时间文本
     */
    public static boolean isDate(String text) {
        return isDateByFormat(text, DateUtils.DEFAULT_DATE_FORMAT);
    }

    /**
     * <B>方法名称：</B>判断是否为日期时间文本<BR>
     * <B>概要说明：</B><BR>
     *
     * @param text
     *            文本
     * @return boolean 是否为日期时间文本
     */
    public static boolean isDatetime(String text) {
        return isDateByFormat(text, DateUtils.DEFAULT_DATETIME_FORMAT);
    }

    /**
     * <B>方法名称：</B>判断是否为日期时间戳文本<BR>
     * <B>概要说明：</B><BR>
     *
     * @param text
     *            文本
     * @return boolean 是否为日期时间戳文本
     */
    public static boolean isTimestamp(String text) {
        return isDateByFormat(text, DateUtils.DEFAULT_TIMESTAMP_FORMAT);
    }

    /**
     * <B>方法名称：</B>判断是否为指定格式的日期文本<BR>
     * <B>概要说明：</B><BR>
     *
     * @param text
     *            文本
     * @param format
     *            格式
     * @return boolean 是否为指定格式的日期文本
     */
    public static boolean isDateByFormat(String text, String format) {
        if (isBlank(text)) {
            return false;
        }
        try {
            DateUtils.parse(text, format);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * <B>方法名称：</B>判断是否为英文数字<BR>
     * <B>概要说明：</B><BR>
     *
     * @param text
     *            文本
     * @return boolean 是否为英文数字
     */
    public static boolean isAlphaNumeric(String text) {
        return isMatch(text, "^[A-Za-z0-9]+$");
    }

    /**
     * <B>方法名称：</B>判断是否为数字<BR>
     * <B>概要说明：</B><BR>
     *
     * @param text
     *            文本
     * @return boolean 是否为数字
     */
    public static boolean isNumeric(String text) {
        return isNumeric(text, 0, 0, true);
    }

    /**
     * <B>方法名称：</B>判断是否为数字<BR>
     * <B>概要说明：</B><BR>
     *
     * @param text
     *            文本
     * @param precision
     *            精度
     * @return boolean 是否为数字
     */
    public static boolean isNumeric(String text, int precision) {
        return isNumeric(text, precision, 0, true);
    }

    /**
     * <B>方法名称：</B>判断是否为数字<BR>
     * <B>概要说明：</B><BR>
     *
     * @param text
     *            文本
     * @param negative
     *            是否可为负数
     * @return boolean 是否为数字
     */
    public static boolean isNumeric(String text, boolean negative) {
        return isNumeric(text, 0, 0, negative);
    }

    /**
     * <B>方法名称：</B>判断是否为数字<BR>
     * <B>概要说明：</B><BR>
     *
     * @param text
     *            文本
     * @param precision
     *            精度
     * @param negative
     *            是否可为负数
     * @return boolean 是否为数字
     */
    public static boolean isNumeric(String text, int precision, boolean negative) {
        return isNumeric(text, precision, 0, negative);
    }

    /**
     * <B>方法名称：</B>判断是否为数字<BR>
     * <B>概要说明：</B><BR>
     *
     * @param text
     *            文本
     * @param precision
     *            精度
     * @param scale
     *            刻度
     * @return boolean 是否为数字
     */
    public static boolean isNumeric(String text, int precision, int scale) {
        return isNumeric(text, precision, scale, true);
    }

    /**
     * <B>方法名称：</B>判断是否为数字<BR>
     * <B>概要说明：</B><BR>
     *
     * @param text
     *            文本
     * @param precision
     *            精度
     * @param scale
     *            刻度
     * @param negative
     *            是否可为负数
     * @return boolean 是否为数字
     */
    public static boolean isNumeric(String text, int precision, int scale,
                                    boolean negative) {
        StringBuffer pattern = new StringBuffer();
        if (negative) {
            pattern.append("-?");
        }
        pattern.append("\\d");
        if (precision > 0) {
            pattern.append("{1,").append(precision).append("}");
        } else {
            pattern.append("+");
        }
        pattern.append("(.\\d");
        if (scale > 0) {
            pattern.append("{1,").append(scale).append("}");
        } else {
            pattern.append("+");
        }
        pattern.append(")?");
        return isMatch(text, pattern.toString());
    }

    /**
     * <B>方法名称：</B>判断是否存在中文<BR>
     * <B>概要说明：</B><BR>
     *
     * @param text
     *            文本
     * @return boolean 是否存在中文
     */
    public static boolean hasChinese(String text) {
        return isMatch(text, ".*[\\u4e00-\\u9fa5].*");
    }

    /**
     * <B>方法名称：</B>判断是否为中文<BR>
     * <B>概要说明：</B><BR>
     *
     * @param text
     *            文本
     * @return boolean 是否为中文
     */
    public static boolean isChinese(String text) {
        return isMatch(text, "^[\\u4e00-\\u9fa5]+$");
    }

    /**
     * <B>方法名称：</B>判断是否为电话号码<BR>
     * <B>概要说明：</B><BR>
     *
     * @param text
     *            文本
     * @return boolean 是否为电话号码
     */
    public static boolean isTel(String text) {
        return isMatch(text, "^(\\d{2,4}-?)?\\d{7,8}(-\\d{1,5})?$");
    }

    /**
     * <B>方法名称：</B>判断是否为手机号码<BR>
     * <B>概要说明：</B><BR>
     *
     * @param text
     *            文本
     * @return boolean 是否为手机号码
     */
    public static boolean isMobile(String text) {
        return isMatch(text, "^\\d{11}$");
    }

    /**
     * <B>方法名称：</B>判断是否为邮件地址<BR>
     * <B>概要说明：</B><BR>
     *
     * @param text
     *            文本
     * @return boolean 是否为邮件地址
     */
    public static boolean isMail(String text) {
        return isMatch(text, "[\\w-]+@\\w+(\\.\\w+)+");
    }

    /**
     * <B>方法名称：</B>判断是否为邮编<BR>
     * <B>概要说明：</B><BR>
     *
     * @param text
     *            文本
     * @return boolean 是否为邮编
     */
    public static boolean isPostcode(String text) {
        return isMatch(text, "^\\d{6}$");
    }

    /**
     * <B>方法名称：</B>判断是否为IP地址<BR>
     * <B>概要说明：</B><BR>
     *
     * @param text
     *            文本
     * @return boolean 是否为IP地址
     */
    public static boolean isIp(String text) {
        return isIpv4(text) || isIpv6(text);
    }

    /**
     * <B>方法名称：</B>判断是否为IP地址(V4)<BR>
     * <B>概要说明：</B><BR>
     *
     * @param text
     *            文本
     * @return boolean 是否为IP地址(V4)
     */
    public static boolean isIpv4(String text) {
        if (isBlank(text)) {
            return false;
        }
        String[] strs = text.split("\\.");
        if (strs == null || strs.length != 4) {
            return false;
        }
        int val = 0;
        for (String str : strs) {
            if (!isNumeric(str)) {
                return false;
            }
            val = Integer.parseInt(str);
            if (val < 0 || val > 255) {
                return false;
            }
        }
        return true;
    }

    /**
     * <B>方法名称：</B>判断是否为IP地址(V6)<BR>
     * <B>概要说明：</B><BR>
     *
     * @param text
     *            文本
     * @return boolean 是否为IP地址(V6)
     */
    public static boolean isIpv6(String text) {
        if (isBlank(text)) {
            return false;
        }
        String[] strs = text.split(":");
        if (strs == null || strs.length < 3 || strs.length > 8) {
            return false;
        }
        boolean hasBlank = false;
        for (String str : strs) {
            if (isBlank(str)) {
                if (hasBlank) {
                    return false;
                }
                hasBlank = true;
                continue;
            }
            if (!isMatch(str, "^[0-9a-f]{1,4}$")) {
                return false;
            }
        }
        return true;
    }

    /**
     * <B>方法名称：</B>通用匹配方法<BR>
     * <B>概要说明：</B>根据正则表达式，判断文本是否匹配。<BR>
     *
     * @param text
     *            文本
     * @param pattern
     *            样式
     * @return boolean 是否匹配
     */
    public static boolean isMatch(String text, String pattern) {
        if (isBlank(text) || isBlank(pattern)) {
            return false;
        }
        return Pattern.compile(pattern).matcher(text).matches();
    }

    /**
     * <B>方法名称：</B>获取文本字节长度<BR>
     * <B>概要说明：</B><BR>
     *
     * @param text
     *            文本
     * @return int 字节长度
     */
    public static int getByteLength(String text) {
        if (isBlank(text)) {
            return 0;
        }
        return text.getBytes().length;
    }

    /**
     * <B>方法名称：</B>生成随机字符串<BR>
     * <B>概要说明：</B><BR>
     *
     * @param length
     *            长度
     * @return String 随机字符串
     */
    public static String getRandomString(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(RANDOM_CHAR_DICT.charAt(random.nextInt(RANDOM_CHAR_DICT
                    .length())));
        }
        return sb.toString();
    }

    /**
     * <B>方法名称：</B>字符串拆分方法<BR>
     * <B>概要说明：</B><BR>
     *
     * @param params
     *            需拆分的字符串
     * @param separator
     *            字符串拆分标识
     * @return 字符串数组
     */
    public static String[] getStringArrBySeparator(String params,
                                                   String separator) {
        if (params != null && params.length() > 0) {
            String[] sep = params.split(separator);
            return sep;
        } else {
            return null;
        }
    }

    /**
     * <B>方法名称：</B>NULL字符串转换为空<BR>
     * <B>概要说明：</B><BR>
     *
     * @param str
     *            字符串
     * @return String 返回字符串
     */
    public static String nullToBlank(String str) {
        if (str == null || str.equals("null")) {
            str = "";
        }
        return str;
    }

    /**
     * <B>方法名称：</B>NULL字符串转换为0<BR>
     * <B>概要说明：</B><BR>
     *
     * @param str
     *            字符串
     * @return String 返回字符串
     */
    public static int nullToZero(String str) {
        if (str == null || str.equals("null") || "".equals(str)) {
            return 0;
        }
        return Integer.parseInt(str);
    }

    /**
     * <B>方法名称：</B>转换为字符串列表<BR>
     * <B>概要说明：</B><BR>
     *
     * @param str
     *            字符串
     * @return List<String> 字符串列表
     */
    public static List<String> toStringList(String str) {
        return toStringList(str, 1, false);
    }

    /**
     * <B>方法名称：</B>转换为字符串列表<BR>
     * <B>概要说明：</B><BR>
     *
     * @param str
     *            字符串
     * @param step
     *            步增长度
     * @return List<String> 字符串列表
     */
    public static List<String> toStringList(String str, int step) {
        return toStringList(str, step, false);
    }

    /**
     * <B>方法名称：</B>转换为字符串列表<BR>
     * <B>概要说明：</B><BR>
     *
     * @param str
     *            字符串
     * @param filtBlank
     *            是否过滤空白
     * @return List<String> 字符串列表
     */
    public static List<String> toStringList(String str, boolean filtBlank) {
        return toStringList(str, 1, filtBlank);
    }

    /**
     * <B>方法名称：</B>转换为字符串列表<BR>
     * <B>概要说明：</B><BR>
     *
     * @param str
     *            字符串
     * @param step
     *            步增长度
     * @param filtBlank
     *            是否过滤空白
     * @return List<String> 字符串列表
     */
    public static List<String> toStringList(String str, int step,
                                            boolean filtBlank) {

        if (str == null) {
            return null;
        }

        List<String> strList = new ArrayList<String>();
        String s = null;
        int start = 0;
        int end = step;
        while (start < str.length()) {
            if (end > str.length()) {
                end = str.length();
            }
            s = str.substring(start, end);
            strList.add(s);
            start = start + step;
            end = start + step;
        }
        return strList;
    }
}
