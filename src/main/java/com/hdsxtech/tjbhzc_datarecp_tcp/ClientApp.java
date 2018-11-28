package com.hdsxtech.tjbhzc_datarecp_tcp;

import com.hdsxtech.tjbhzc_datarecp_tcp.client.CenterClient;
import com.hdsxtech.tjbhzc_datarecp_tcp.client.thread.ParseCenterMsgThreadManager;
import com.hdsxtech.tjbhzc_datarecp_tcp.factory.CenterMsgConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 启动服务
 *
 * @author baobao
 */
public class ClientApp {
    private static final Logger logger = LoggerFactory.getLogger(ClientApp.class);
    public static boolean isBase = false;

    private static void initDb() {
        //加载初始化数据进入内存
    }

    public static void run() {
        //读取xml配置
        String xmlfile = "/msgcenter.xml";
        CenterMsgConfig.getInstance().readFromXml(xmlfile);
        initCenter();//连接后台服务
    }

    public static void initCenter() {
        //启动消息处理线程，然后再进行连接
        ParseCenterMsgThreadManager.getInstance().run(0, 0);
        CenterClient.getInstance().start();
        logger.info("连接后台服务启动完成*********");
    }

    public static void main(String[] args) {
        logger.info("客户端服务启动开始");
        run();
        logger.info("正常启动客户端程序");
    }
}
