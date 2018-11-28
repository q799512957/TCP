package com.hdsxtech.tjbhzc_datarecp_tcp.msg.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * ClassName: LogUtil 
 * Reason: 用于统一日志记录
 * date: 2014年10月15日 下午1:59:51 
 *
 */
public class LogUtil {

    private static Logger log;
    private static String LOGER_NAME = "CZCFWGL";

    private LogUtil() {
    	log = LoggerFactory.getLogger(LogUtil.LOGER_NAME);
    }

    /**
     * 
     * getLogger:获取日志记录器
     *
     * @return
     */
    public static Logger getLogger() {
        if (log == null) {
        	log = LoggerFactory.getLogger(LogUtil.LOGER_NAME);
        }
        return log;
    }
}
