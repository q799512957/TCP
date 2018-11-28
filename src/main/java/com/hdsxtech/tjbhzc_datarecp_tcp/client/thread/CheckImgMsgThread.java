package com.hdsxtech.tjbhzc_datarecp_tcp.client.thread;

import com.hdsxtech.tjbhzc_datarecp_tcp.client.CenterClient;
import com.hdsxtech.tjbhzc_datarecp_tcp.factory.MessageID;
import com.hdsxtech.tjbhzc_datarecp_tcp.factory.MsgBean;
import com.hdsxtech.tjbhzc_datarecp_tcp.util.CRCUtil;
import com.hdsxtech.tjbhzc_datarecp_tcp.util.ConstantUtils;
import com.hdsxtech.tjbhzc_datarecp_tcp.util.Constants;
import com.hdsxtech.tjbhzc_datarecp_tcp.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.stream.FileImageInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *  定时发送称重图片数据
 */
@Deprecated
public class CheckImgMsgThread extends AbsThread {
    private static final Logger logger = LoggerFactory.getLogger(CheckImgMsgThread.class);
    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    private static CheckImgMsgThread obj;
    private int heartbeatdelay = Integer.parseInt(PropertiesUtil
            .getProperties().getProperty("tcp.heartbeatdelay"));// 心跳间隔
    public static Date lastTime = new Date();

    public static CheckImgMsgThread getInstance() {
        if (obj == null) {
            synchronized (CheckImgMsgThread.class) {
                if (obj == null) {
                    obj = new CheckImgMsgThread();
                }
            }
            obj = new CheckImgMsgThread();
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

                @Override
                public void run() {
                        Date nowDate = new Date();
                        lastTime = nowDate;
                        MsgBean mm = new MsgBean(MessageID.MESSAGE_CHECKIMG_ID, ConstantUtils.KEY_CENTER);
                        mm.getBody().getByID(ConstantUtils.CHECKIMG_EQUIP_ID).setValue("1234567890123456".getBytes());
                        mm.getBody().getByID(ConstantUtils.CHECKIMG_EQUIPT_STATUS).setValue(0x1);

                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);//获取年份
                        int month=cal.get(Calendar.MONTH);//获取月份
                        int day=cal.get(Calendar.DATE);//获取日
                        int hour=cal.get(Calendar.HOUR);//小时
                        int minute=cal.get(Calendar.MINUTE);//分
                        int second=cal.get(Calendar.SECOND);//秒

                        mm.getBody().getByID(ConstantUtils.CHECKIMG_YEAR).setValue(year);
                        mm.getBody().getByID(ConstantUtils.CHECKIMG_MONTH).setValue(month);
                        mm.getBody().getByID(ConstantUtils.CHECKIMG_DAY).setValue(day);
                        mm.getBody().getByID(ConstantUtils.CHECKIMG_HOUR).setValue(hour);
                        mm.getBody().getByID(ConstantUtils.CHECKIMG_MINUTES).setValue(minute);
                        mm.getBody().getByID(ConstantUtils.CHECKIMG_SECONDS).setValue(second);
                        mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_DATANO ).setValue(Constants.getMsg_sn());
                        mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_LINE ).setValue(222);
                        mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_VEHICLE_TYPE ).setValue(11);
                        mm.getBody().getByID(ConstantUtils.CHECKIMG_IMG_TYPE).setValue(0xaa);
                        mm.getBody().getByID(ConstantUtils.CHECKIMG_RESERVED1).setValue(0x15);
                        mm.getBody().getByID(ConstantUtils.CHECKIMG_RESERVED2).setValue(0x16);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

                        String timeStamp = sdf.format(new Date());
                        File f = new File("E:/abc.jpg");
                        FileImageInputStream in = null;
                        long img_length = 0;
                        byte[] imgs = null;
                        try{
                            in = new FileImageInputStream(f);
                            imgs = new byte[(int)in.length()];
                            in.read(imgs);
                            img_length = in.length();
                        }catch(Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                in.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        mm.getBody().getByID(ConstantUtils.CHECKIMG_LENGTH).setValue(img_length);
                        mm.getBody().getByID(ConstantUtils.CHECKIMG_CONTENT).setValue(imgs);

                        byte [] datas = mm.tobytes();
                        String crcString = CRCUtil.GetSmallEndCRC(datas);
                        //mm.getBody().getByID(ConstantUtils.CHECKIMG_CRC).setValue(crcString);
                        byte [] tails = new byte[2];
                        tails[0] = (byte)238;
                        tails[1] = (byte)238;
                        //mm.getBody().getByID(ConstantUtils.CHECKIMG_TAIL).setValue(tails);
                        logger.info("客户端发送称重图片信息");
                        CenterClient.getInstance().send(mm);
                }
            }, delay, period, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("客户端发送称重图片信息异常：" + e.toString());
            e.printStackTrace();
        }
    }

    public void stop() {
        isRun = false;
        service.shutdown();
        obj = null;
    }
}