package com.hdsxtech.tjbhzc_datarecp_tcp.client.thread;

import com.hdsxtech.tjbhzc_datarecp_tcp.client.CenterClient;
import com.hdsxtech.tjbhzc_datarecp_tcp.factory.MessageID;
import com.hdsxtech.tjbhzc_datarecp_tcp.factory.MsgBean;
import com.hdsxtech.tjbhzc_datarecp_tcp.util.ConstantUtils;
import com.hdsxtech.tjbhzc_datarecp_tcp.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 发送车辆的称重信息
 */
public class HeartBeatMsgThread extends AbsThread {

    private static final Logger logger = LoggerFactory.getLogger(HeartBeatMsgThread.class);
    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    private static HeartBeatMsgThread obj;

    private int heartbeatdelay = Integer.parseInt(PropertiesUtil.getProperties().getProperty("tcp.heartbeatdelay"));// 心跳间隔
    public static Date lastTime = new Date();

    public static HeartBeatMsgThread getInstance() {
        if (obj == null) {
            synchronized (HeartBeatMsgThread.class) {
                if (obj == null) {
                    obj = new HeartBeatMsgThread();
                }
            }
            obj = new HeartBeatMsgThread();
        }
        return obj;
    }

    /**
     * @param delay
     * @param period
     */
    @Override
    protected void runThread(long delay, long period) {
        try {
            service.scheduleAtFixedRate(new Runnable() {
                int count = 0;
                @Override
                public void run() {
                    sendTestData();
                    count++;
                    logger.info("第" + count  + "发送心跳信息");
                    if( count == 3 ) {
                        stop();
                    }
                }
            },3,5, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("客户端发送称重信息异常：" + e.toString());
            e.printStackTrace();
        }
    }


    private void sendTestData() {
        Date nowDate = new Date();
        MsgBean mm = new MsgBean(MessageID.MESSAGE_HEARTBEAT_ID, ConstantUtils.KEY_CENTER);
        mm.getBody().getByID(ConstantUtils.HEARTBEAT_EQUIPT_ID).setValue("6C3ED3260C472094E050007F01004AF2".getBytes());
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);//获取年份
        int month=cal.get(Calendar.MONTH);//获取月份
        int day=cal.get(Calendar.DATE);//获取日
        int hour=cal.get(Calendar.HOUR);//小时
        int minute=cal.get(Calendar.MINUTE);//分
        int second=cal.get(Calendar.SECOND);//秒
        mm.getBody().getByID(ConstantUtils.HEARTBEAT_YEAR).setValue(year);
        mm.getBody().getByID(ConstantUtils.HEARTBEAT_MONTH).setValue(month);
        mm.getBody().getByID(ConstantUtils.HEARTBEAT_DAY).setValue(day);
        mm.getBody().getByID(ConstantUtils.HEARTBEAT_HOUR).setValue(hour);
        mm.getBody().getByID(ConstantUtils.HEARTBEAT_MINUTES).setValue(minute);
        mm.getBody().getByID(ConstantUtils.HEARTBEAT_SECONDS).setValue(second);
        byte[] ips = new byte[4];
        ips[0] = 127;
        ips[1] = 0;
        ips[2] = 0;
        ips[3] = 1;
        mm.getBody().getByID(ConstantUtils.HEARTBEAT_IP).setValue(ips);
        double ptx = 0;
        double pty = 0;
        mm.getBody().getByID(ConstantUtils.HEARTBEAT_PTX).setValue(ptx);
        mm.getBody().getByID(ConstantUtils.HEARTBEAT_PTY).setValue(pty);

        logger.info("客户端发送称重信息");
        CenterClient.getInstance().send(mm);
    }

    public void stop() {
        isRun = false;
        service.shutdown();
        obj = null;
    }
}