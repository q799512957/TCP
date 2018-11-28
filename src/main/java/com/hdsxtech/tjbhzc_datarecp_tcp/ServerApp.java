package com.hdsxtech.tjbhzc_datarecp_tcp;



import com.hdsxtech.tjbhzc_datarecp_tcp.factory.CenterMsgConfig;
import com.hdsxtech.tjbhzc_datarecp_tcp.server.TcpServer;
import com.hdsxtech.tjbhzc_datarecp_tcp.server.thread.ParseMsgThreadManager;
import com.hdsxtech.tjbhzc_datarecp_tcp.util.Constants;
import com.hdsxtech.tjbhzc_datarecp_tcp.util.DbUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 启动服务端服务
 */
public class ServerApp {
    private static final Logger logger = LoggerFactory.getLogger(ServerApp.class);
    public static boolean isBase = false;

    public static void run() {
        //读取xml配置
        String xmlfile = "/msgcenter.xml";
        CenterMsgConfig.getInstance().readFromXml(xmlfile);
        logger.info("读取xml完毕");
        //启动TCP服务 监听客户端连接
        initJk();
        logger.info("启动TCP服务 监听客户端连接完成");
    }

    public static void initDb() {
        logger.info("初始化 设备ID 数组 .... ");
        List<String> ids = DbUtil.getCityEquiptIDs();
        if( ids != null && ids.size() > 0 ) {
            Constants.EQUIPT_IDS = ids;
        } else {
            throw new  RuntimeException("系统没有查询任何设备信息");
        }
    }

    public static void initJk() {
        initDb();
        TcpServer.getInstance().start();
        logger.info("客户端监听启动完成");

        // 启动临时客户端管理(秒)
    /*    CheckTemClientThread.getInstance().run(
                Integer.parseInt(PropertiesUtil.getProperties().getProperty("tcp.temclientdelay")),
                Integer.parseInt(PropertiesUtil.getProperties().getProperty("tcp.temclientdelay")));*/
    //    logger.info("启动临时客户端管理完成");

        // 启动消息处理
        ParseMsgThreadManager.getInstance().runThread(0, 0);
        logger.info("启动消息处理完成");

        //启动客户端管理
   /*     CheckClientThread.getInstance().run(
                Integer.parseInt(PropertiesUtil.getProperties().getProperty("tcp.clientdelay")),
                Integer.parseInt(PropertiesUtil.getProperties().getProperty("tcp.clientdelay")));
        logger.info("启动客户端管理完成");
*/
        //检查客户端待恢复消息的时间
       /* ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        int delay = Integer.parseInt(PropertiesUtil.getProperties().getProperty("msgcheck"));
        final int overtime = Integer.parseInt(PropertiesUtil.getProperties().getProperty("overtime"));
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Vector<ClientMsg> v = ClientManager.clientMsg_sn;
                for (int i = v.size(); i >= 0; i--) {
                    if (v.get(i).getSendTime() - System.currentTimeMillis() > overtime * 1000) {
                        v.remove(i);
                    }
                }
            }
        }, delay, delay, TimeUnit.SECONDS);*/
    }



    public static void main(String[] args) {
        logger.info("服务启动开始");
        run();//暂时默认启动这个无参数传递
        logger.info("正常启动程序");
    }
}
