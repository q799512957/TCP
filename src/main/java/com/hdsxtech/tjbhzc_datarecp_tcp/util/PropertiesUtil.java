package com.hdsxtech.tjbhzc_datarecp_tcp.util;

import com.hdsxtech.tjbhzc_datarecp_tcp.msg.util.DataTypeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * 配置文件加载工具
 *
 */
public class PropertiesUtil {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
    private static volatile Properties p = null;

    public synchronized static Properties getProperties() {
        if (p == null || !DataTypeUtil.isNotEmpty(p.get("upa_hostname"))) {
            p = new Properties();
            try {
                p.load(PropertiesUtil.class.getResourceAsStream("/tcp.properties"));
            } catch (IOException e) {
                logger.error("配置文件properties初始化错误：", e);
                e.printStackTrace();
            }
        }
        return p;
    }
}
