//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hdsxtech.tjbhzc_datarecp_tcp.util;



import java.util.Calendar;
import java.util.UUID;

public class DataIdGenerator {
    /** 日期时间戳格式 */
    public static final String KEY_TIMESTAMP_FORMAT = "yyyyMMddHHmmss";

    /**
     * <B>方法名称：</B>生成新的数据标识<BR>
     * <B>概要说明：</B><BR>
     *
     * @param prefix
     *            前缀
     * @return String 数据标识
     */
    public static String generate(String prefix) {
        String key = "";
        if (!StringUtils.isBlank(prefix)) {
            key += prefix;
        }
        key += DateUtils.getCurrentByFormat(KEY_TIMESTAMP_FORMAT);
        key += StringUtils.getRandomString(9);
        return key;
    }

    public static String generate(String prefix, Calendar passTime) {
        String key = "";
        if (!StringUtils.isBlank(prefix)) {
            key += prefix;
        }
        key += DateUtils.format(passTime.getTime(), KEY_TIMESTAMP_FORMAT);
        key += StringUtils.getRandomString(9);
        return key;
    }

    public static String generate32Length(String prefix, Calendar passTime) {
        String key = "";
        if (!StringUtils.isBlank(prefix)) {
            key += prefix;
        }
        key += DateUtils.format(passTime.getTime(), KEY_TIMESTAMP_FORMAT);
        key += UUID.randomUUID().toString().replaceAll("-", "")
                .toUpperCase().substring(18);
        return key;
    }

    public static String MaxStrGenerate(String prefix, Calendar passTime) {
        String key = "";
        if (!StringUtils.isBlank(prefix)) {
            key += prefix;
        }
        key += DateUtils.format(passTime.getTime(), KEY_TIMESTAMP_FORMAT);
        key += "zzzzzzzzz";
        return key;
    }

    public static String MinStrGenerate(String prefix, Calendar passTime) {
        String key = "";
        if (!StringUtils.isBlank(prefix)) {
            key += prefix;
        }
        key += DateUtils.format(passTime.getTime(), KEY_TIMESTAMP_FORMAT);
        key += "         ";// 9个空格
        return key;
    }
}
