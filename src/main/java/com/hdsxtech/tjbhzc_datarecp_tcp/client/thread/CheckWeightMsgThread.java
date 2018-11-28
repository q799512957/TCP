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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 发送车辆的称重信息
 */
public class CheckWeightMsgThread extends AbsThread {
    private static final Logger logger = LoggerFactory.getLogger(CheckWeightMsgThread.class);
    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    private static CheckWeightMsgThread obj;

    private int heartbeatdelay = Integer.parseInt(PropertiesUtil.getProperties().getProperty("tcp.heartbeatdelay"));// 心跳间隔
    public static Date lastTime = new Date();

    public static CheckWeightMsgThread getInstance() {
        if (obj == null) {
            synchronized (CheckWeightMsgThread.class) {
                if (obj == null) {
                    obj = new CheckWeightMsgThread();
                }
            }
            obj = new CheckWeightMsgThread();
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
           // Map<String,Object> param = sendTestCheckData();
          /*Map<String,Object> param = new HashMap<String,Object>();
          param.put("check_no","");
          param.put("data_no",123);*/
            Map<String,Object> param =   sendTestCheckData();
            sentTestImgData(param);
        } catch (Exception e) {
            logger.error("客户端发送称重信息异常：" + e.toString());
            e.printStackTrace();
        }
    }


    private Map<String,Object> sendTestCheckData() {

        int data_no = Constants.getMsg_sn();
        String check_no = Constants.getCheckNo();

        Date nowDate = new Date();
        MsgBean mm = new MsgBean(MessageID.MESSAGE_CHECKWEIGHT_ID, ConstantUtils.KEY_CENTER);

        byte [] datas1 = mm.tobytes();
        mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_EQUIPT_ID).setValue("6C3ED3260C472094".getBytes());
        mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_EQUIPT_STATUS).setValue(11);
        //mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_NO).setValue( "12111111111122222222223333333333".getBytes());

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);// 获取年份
        int month=cal.get(Calendar.MONTH)+1;// 获取月份
        int day=cal.get(Calendar.DATE);// 获取日
        int hour=cal.get(Calendar.HOUR_OF_DAY);// 小时
        int minute=cal.get(Calendar.MINUTE);// 分
        int second=cal.get(Calendar.SECOND);// 秒
        int millisecond = cal.get(Calendar.MILLISECOND); // 毫秒

        mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_YEAR).setValue(year);
        mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_MONTH).setValue(month);
        mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_DAY).setValue(day);
        mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_HOUR).setValue(hour);
        mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_MINUTES).setValue(minute);
        mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_SECONDS).setValue(second);
        //mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_MILLISECONDS).setValue(millisecond);
     //   mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_DATANO).setValue(data_no);
        mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_DATANO).setValue(10);
        mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_LINE ).setValue(11);
        mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_VEHICLE_TYPE ).setValue(12);
        mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_SPEED ).setValue(13);
        mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_VEHICLE_NO).setValue("京JAU090   ".getBytes());
     //   mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_VEHICLE_NO).setValue(new byte[]{1,2,3,4,5,6,7,8,9,0,1,2});
        mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_AXLES ).setValue(14);
        mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_VEHICLENO_COLOR).setValue(15);
        mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_TOTAL ).setValue("111".getBytes());
        mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_WEIGHT1 ).setValue(16);
        mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_WEIGHT2 ).setValue(17);
        mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_WEIGHT3 ).setValue(18);
        mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_WEIGHT4).setValue(19);
        mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_WEIGHT5).setValue(20);
        mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_WEIGHT6).setValue(21);
        mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_WEIGHT_OTHER).setValue(22);
        mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_WEIGHT_LIMIT).setValue(23);
        mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_VEHICLE_LENGTH).setValue(24);
        mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_VEHICLE_WIDTH).setValue(25);
        mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_VEHICLE_HEIGHT).setValue(26);
        mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_RESERVED1).setValue(27);
        mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_RESERVED2 ).setValue(28);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeStamp = sdf.format(new Date());

        //mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_CRC).setValue("12".getBytes());
        byte [] tails = new byte[2];
        tails[0] = (byte)238;
        tails[1] = (byte)238;
        //mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_TAIL).setValue(tails);

        byte [] datas = mm.tobytes();
        byte[] tempdatas = new byte[datas.length - 12];
        System.arraycopy(datas, 7, tempdatas, 0, datas.length - 12);
        String crcString = CRCUtil.GetSmallEndCRC(tempdatas);
        //cec应为2字节，将返回的string的4个字节转成byte两个存储，
        byte[] data = new byte[2];
        data[0] = (byte)Integer.parseInt(crcString.substring(0, 2),16);
        data[1] = (byte)Integer.parseInt(crcString.substring(2, 4),16);
        //mm.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_CRC).setValue(data);
        byte[] datas2 = mm.tobytes();
        logger.info("客户端发送称重信息");
        CenterClient.getInstance().send(mm);
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("data_no",data_no);
        map.put("check_no",check_no);
        return map;
    }


    private void sentTestImgData (Map<String,Object> param) {

        String check_no = (String)param.get("check_no");
        int data_no = (int)param.get("data_no");

        Date nowDate = new Date();
        lastTime = nowDate;
        MsgBean mm = new MsgBean(MessageID.MESSAGE_CHECKIMG_ID, ConstantUtils.KEY_CENTER);

        mm.getBody().getByID(ConstantUtils.CHECKIMG_EQUIP_ID).setValue("6C3ED3260C472094".getBytes());
        mm.getBody().getByID(ConstantUtils.CHECKIMG_EQUIPT_STATUS).setValue(0x1);
        //mm.getBody().getByID(ConstantUtils.CHECKIMG_CHECK_NO).setValue(check_no.getBytes());

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);//获取年份
        int month=cal.get(Calendar.MONTH)+1;//获取月份
        int day=cal.get(Calendar.DATE);//获取日
        int hour=cal.get(Calendar.HOUR_OF_DAY);//小时
        int minute=cal.get(Calendar.MINUTE);//分
        int second=cal.get(Calendar.SECOND);//秒
        int millisecond=cal.get(Calendar.MILLISECOND);

        mm.getBody().getByID(ConstantUtils.CHECKIMG_YEAR).setValue(year);
        mm.getBody().getByID(ConstantUtils.CHECKIMG_MONTH).setValue(month);
        mm.getBody().getByID(ConstantUtils.CHECKIMG_DAY).setValue(day);
        mm.getBody().getByID(ConstantUtils.CHECKIMG_HOUR).setValue(hour);
        mm.getBody().getByID(ConstantUtils.CHECKIMG_MINUTES).setValue(minute);
        mm.getBody().getByID(ConstantUtils.CHECKIMG_SECONDS).setValue(second);
       // mm.getBody().getByID(ConstantUtils.CHECKIMG_MILLISECONDS).setValue(millisecond);
  //      mm.getBody().getByID(ConstantUtils.CHECKIMG_DATA_NO).setValue(data_no);
        mm.getBody().getByID(ConstantUtils.CHECKIMG_DATA_NO).setValue(64);
        mm.getBody().getByID(ConstantUtils.CHECKIMG_LINE).setValue(65);
        mm.getBody().getByID(ConstantUtils.CHECKIMG_IMG_TYPE ).setValue(66);
        mm.getBody().getByID(ConstantUtils.CHECKIMG_RESERVED1).setValue(67);
        mm.getBody().getByID(ConstantUtils.CHECKIMG_RESERVED2).setValue(68);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        String timeStamp = sdf.format(new Date());
        File f = new File("D:/test.jpg");
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
//        img_length = 2;
//        mm.getBody().getByID(ConstantUtils.CHECKIMG_LENGTH).setValue(img_length);
//        mm.getBody().getByID(ConstantUtils.CHECKIMG_CONTENT).setValue("1".getBytes());

        byte [] datas = mm.tobytes();
        byte [] tempbytes = new byte[datas.length - 12 ];
        System.arraycopy(datas, 7, tempbytes, 0, datas.length - 12);
        String crcString = CRCUtil.GetSmallEndCRC(tempbytes);
        byte[] data = new byte[2];
        data[0] = (byte)Integer.parseInt(crcString.substring(0, 2),16);
        data[1] = (byte)Integer.parseInt(crcString.substring(2, 4),16);
        //mm.getBody().getByID(ConstantUtils.CHECKIMG_CRC).setValue(data);
        byte [] tails = new byte[2];
        tails[0] = (byte)238;
        tails[1] = (byte)238;
        //mm.getBody().getByID(ConstantUtils.CHECKIMG_TAIL).setValue(tails);
        logger.info("客户端发送称重图片信息");
        datas = mm.tobytes();
        //byte[] b = (byte[])mm.getBody().getByID(ConstantUtils.CHECKIMG_CRC).getValue();
        CenterClient.getInstance().send(mm);
    }

    public void stop() {
        isRun = false;
        service.shutdown();
        obj = null;
    }

    public static void main(String[] args) {
        byte [] bs = "overload".getBytes();
        System.out.println(bs);
    }

}