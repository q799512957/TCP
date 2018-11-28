package com.hdsxtech.tjbhzc_datarecp_tcp.server.handler;


import com.hdsxtech.tjbhzc_datarecp_tcp.bean.DdCaptureImage;
import com.hdsxtech.tjbhzc_datarecp_tcp.bean.DdCheckWeight;
import com.hdsxtech.tjbhzc_datarecp_tcp.factory.MessageID;
import com.hdsxtech.tjbhzc_datarecp_tcp.factory.MsgBean;
import com.hdsxtech.tjbhzc_datarecp_tcp.msg.IHandler;
import com.hdsxtech.tjbhzc_datarecp_tcp.server.ClientManager;
import com.hdsxtech.tjbhzc_datarecp_tcp.server.HKSPDll;
import com.hdsxtech.tjbhzc_datarecp_tcp.server.Traffic;
import com.hdsxtech.tjbhzc_datarecp_tcp.util.*;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.stream.FileImageOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

/**
 *
 *称重图片信息 handler
 *
 */
public class Handler12 implements IHandler {
    private Logger logger = LoggerFactory.getLogger(Handler12.class);
    public static byte[] block = new byte[0];

    public void doHandle(MsgBean msg, ChannelHandlerContext ctx) {
        logger.info("处理客称重图片、视频消息：" );
        try {
            boolean result = false;
            Object msdId = msg.getHeader().getByID(ConstantUtils.HEADER_MSGID_ID).getValue();

             // 设备编码
            byte[] beuiptId = (byte[])msg.getBody().getByID(ConstantUtils.CHECKIMG_EQUIP_ID).getValue();
            String equiptId = new String(beuiptId);
            //byte[] check_no = (byte[])msg.getBody().getByID(ConstantUtils.CHECKIMG_CHECK_NO).getValue();
            String checkType = msg.getBody().getByID(ConstantUtils.CHECKIMG_EQUIPT_STATUS).getValue().toString();
            int year = (int)msg.getBody().getByID(ConstantUtils.CHECKIMG_YEAR).getValue();
            int month = (int)msg.getBody().getByID(ConstantUtils.CHECKIMG_MONTH).getValue();
            int day = (int)msg.getBody().getByID(ConstantUtils.CHECKIMG_DAY).getValue();
            int hour = (int)msg.getBody().getByID(ConstantUtils.CHECKIMG_HOUR).getValue();
            int minute = (int)msg.getBody().getByID(ConstantUtils.CHECKIMG_MINUTES).getValue();
            int second = (int)msg.getBody().getByID(ConstantUtils.CHECKIMG_SECONDS).getValue();
            int datano = (int)msg.getBody().getByID(ConstantUtils.CHECKIMG_DATA_NO).getValue();
            String line = msg.getBody().getByID(ConstantUtils.CHECKIMG_LINE).getValue().toString();
            String imgType = msg.getBody().getByID(ConstantUtils.CHECKIMG_IMG_TYPE).getValue().toString();
            long dlength = (long)msg.getBody().getByID(ConstantUtils.CHECKIMG_LENGTH).getValue();
            byte[] content = (byte[])msg.getBody().getByID(ConstantUtils.CHECKIMG_CONTENT).getValue();
            String reserved1 = msg.getBody().getByID(ConstantUtils.CHECKIMG_RESERVED1).getValue().toString();
            String reserved2 = msg.getBody().getByID(ConstantUtils.CHECKIMG_RESERVED2).getValue().toString();
            //byte[] crc = (byte[]) msg.getBody().getByID(ConstantUtils.CHECKIMG_CRC).getValue();

            //  logger.info("收到客户端传来的单车图片视频数据：" + dci);
          logger.info("服务器端收到的称重图片的信息 : "
                             + " equiptId : " + new String(equiptId) + "\n"
                             + " checkType : " + checkType + "\n"
                             + " year : " + year + "\n"
                             + " month : " + month + "\n"
                             + " day : " + day + "\n"
                             + " hour : " + hour + "\n"
                             + " minute : " + minute + "\n"
                             + " second : " +second + "\n"
                             + " datano : " + datano + "\n"
                             + " line : " + line + "\n"
                             + " imgType : " + imgType + "\n"
                             + " dlength : " + dlength + "\n"
                             + " content : "  + content.length + "\n"
                             + " reserved1 : " + reserved1 + "\n"
                             + " resvered2 : " + reserved2 + "\n"
            );

            // 返回给客户端的信息
            MsgBean response = new MsgBean(msg, MessageID.MESSAGE_CHECKIMG_RESPONSE_ID, ConstantUtils.KEY_CENTER);
            response.getBody().getByID(ConstantUtils.CHECKIMG_RESPONSE_EQUIPT_ID).setValue(beuiptId);
            //response.getBody().getByID(ConstantUtils.CHECKIMG_RESPONSE_CHECK_NO).setValue(check_no);
            response.getBody().getByID(ConstantUtils.CHECKIMG_RESPONSE_YEAR).setValue(year);
            response.getBody().getByID(ConstantUtils.CHECKIMG_RESPONSE_MONTH).setValue(month);
            response.getBody().getByID(ConstantUtils.CHECKIMG_RESPONSE_DAY).setValue(day);
            response.getBody().getByID(ConstantUtils.CHECKIMG_RESPONSE_DATA_NO).setValue(datano);
            response.getBody().getByID(ConstantUtils.CHECKIMG_RESPONSE_IMGDATA_TYPE).setValue(imgType);
//            if(!ConstantUtils.isEquiptIdExist(new String(equiptId)) ) {
//                response.getBody().getByID(ConstantUtils.CHECKIMG_RESPONSE_RESULT).setValue(0x02);
//                ClientManager.sendAnswerData(response, ctx);
//                return;
//            }
//
//            if(!ConstantUtils.isRightHeartTime(year,month,day,hour,minute,second) ) {
//                response.getBody().getByID(ConstantUtils.CHECKIMG_RESPONSE_RESULT).setValue(0x03);
//                ClientManager.sendAnswerData(response, ctx);
//                return;
//            }


            byte [] datas = msg.tobytes();
            byte [] tempbytes = new byte[datas.length - 12 ];
            System.arraycopy(datas, 7, tempbytes, 0, datas.length - 12);
            String newCrc = CRCUtil.GetSmallEndCRC(tempbytes);
            //将string转byte对比校验
            byte[] data = new byte[2];
            data[0] = (byte)Integer.parseInt(newCrc.substring(0, 2),16);
            data[1] = (byte)Integer.parseInt(newCrc.substring(2, 4),16);
//            if (!(crc[0]==data[0] && crc[1]==data[1])){
//                response.getBody().getByID(ConstantUtils.CHECKIMG_RESPONSE_RESULT).setValue(0x04);
//                ClientManager.sendAnswerData(response, ctx);
//                return;
//            }
            response.getBody().getByID(ConstantUtils.CHECKIMG_RESPONSE_RESULT).setValue(0xFFFF);
            //response.getBody().getByID(ConstantUtils.CHECKIMG_RESPONSE_CRC).setValue(crc);
            byte [] tails = new byte[2];
            tails[0] = (byte)238;
            tails[1] = (byte)238;
            //response.getBody().getByID(ConstantUtils.CHECKIMG_RESPONSE_TAIL).setValue(tails);

            ClientManager.sendAnswerData(response, ctx);


            //获取配置文件上传地址
            String fileurl = PropertiesUtil.getProperties().getProperty("uploadfile.uploadPicUrl");
            FileImageOutputStream fio = null;
            File f = null;
            try{
                //判断地址是否存在，不存在创建
                File file = new File(fileurl);
                if( !file.exists()) {
                    f.mkdir();
                }
                //图片名称是否存在，存在删除重新创建
                f = new File(fileurl+ "/" + new Date().getTime()  +".jpg");
                if( f.exists()) {
                    f.delete();
                }
                fio = new FileImageOutputStream(f);
                fio.write(content);
            } catch( Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fio.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //--------称重图片入库start---------
            DdCaptureImage ddCaptureImage =  new DdCaptureImage();
            //ddCaptureImage.setCheckNo(new String(check_no));
            //拼接创建时间
            String str = year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
            try {
                ddCaptureImage.setCheckTime(str);
            }catch (Exception e){
                e.printStackTrace();
            }
 //           ddCaptureImage.setSiteId();
            ddCaptureImage.setType(imgType);
            ddCaptureImage.setCheckType(checkType);
            ddCaptureImage.setLine(line);
            String strKey = UUID.randomUUID().toString().replace("-", "");
            ddCaptureImage.setKey(strKey);
            ddCaptureImage.setName(f.getName());

            DbUtil db = new DbUtil();
            boolean b = true;
            b =db.insertCheckImageData(ddCaptureImage);
            System.out.println("称重图片入库:"+b);
            //--------称重图片入库end---------
            //入库失败删除图片
//            if(!b){
//                f.delete();
//            }

        } catch (NumberFormatException e) {
            logger.error("接口单车图片信息异常");
            e.printStackTrace();
        }
    }

    @Override
    public void doHandle(byte[] m, ChannelHandlerContext ctx) {
        Traffic myTF = new Traffic();
        DbUtil db = new DbUtil();
        //System.out.println("m:---------------"+Arrays.toString(m));
        System.out.println("m.length :---------------"+m.length);
        try {
            myTF.DWImageTrafficInfoStream(m);
            System.out.println("myTF.getImage().length :---------------"+myTF.getImage().length);
            StringBuffer stationID = new StringBuffer("");
            Site site = db.getZCStation(myTF.getEquipCode(), stationID);
            String checkId = "";
            String distCode = db.getDistCode(myTF.getEquipCode());
            checkId = DataIdGenerator.generate32Length(distCode, myTF.getPassTime());
            myTF.setStationID(stationID.toString());
            if (myTF.getImageType() == 129) {
                this.logger.info("视频数据~~~id:" + myTF.getSingleVehicleID() + "~~~" + checkId + "~~~" + stationID + "~~~" + myTF.getYear() + "~~~" + String.format("%02d", new Object[]{Integer.valueOf(myTF.getMonth())}) + "~~~" + String.format("%02d", new Object[]{Integer.valueOf(myTF.getDay())}) + "~~~" + String.format("%02d", new Object[]{Integer.valueOf(myTF.getHour())}) + "~~~" + String.format("%02d", new Object[]{Integer.valueOf(myTF.getMinute())}) + "~~~" + String.format("%02d", new Object[]{Integer.valueOf(myTF.getSecond())}) + "~~~" + myTF.getStationCode() + "~~~OK");
                System.out.println("m:---------------"+Arrays.toString(m));
            } else {
                this.logger.info("图片数据~~~id:" + myTF.getSingleVehicleID() + "~~~" + checkId + "~~~" + stationID + "~~~" + myTF.getYear() + "~~~" + String.format("%02d", new Object[]{Integer.valueOf(myTF.getMonth())}) + "~~~" + String.format("%02d", new Object[]{Integer.valueOf(myTF.getDay())}) + "~~~" + String.format("%02d", new Object[]{Integer.valueOf(myTF.getHour())}) + "~~~" + String.format("%02d", new Object[]{Integer.valueOf(myTF.getMinute())}) + "~~~" + String.format("%02d", new Object[]{Integer.valueOf(myTF.getSecond())}) + "~~~" + myTF.getStationCode() + "~~~OK");
            }

            byte i = 0;

            String plate=String.format("%02x", new Object[]{Integer.valueOf(myTF.getImageType())})+new Date().getTime();
            myTF.setPlate(plate);
            if (myTF.getImageType() == 129 && myTF.getImage().length > 0 ) {
                HKSPDll.instanceDll.transbyte(myTF.getStationID() + ".mp4", myTF.getImage(), myTF.getImage().length);
                FileInputStream fis = new FileInputStream(plate + ".mp4.trans");
                ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
                byte[] b = new byte[1000];
                int n;
                while ((n = fis.read(b)) != -1) {
                    bos.write(b, 0, n);
                }
                fis.close();
                bos.close();
                myTF.setImage(bos.toByteArray());
                String fileurl = PropertiesUtil.getProperties().getProperty("uploadfile.uploadPicUrl");
                FileImageOutputStream fio = null;
                File f = null;
                try{
                    //判断地址是否存在，不存在创建
                    File file = new File(fileurl);
                    if( !file.exists()) {
                        f.mkdir();
                    }
                    //图片名称是否存在，存在删除重新创建
                    f = new File(fileurl+ "/" + plate  +".MP4");
                    if( f.exists()) {
                        f.delete();
                    }
                    fio = new FileImageOutputStream(f);
                    fio.write(myTF.getImage());
                } catch( Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fio.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                String fileurl = PropertiesUtil.getProperties().getProperty("uploadfile.uploadPicUrl");
                FileImageOutputStream fio = null;
                File f = null;
                try{
                    //判断地址是否存在，不存在创建
                    File file = new File(fileurl);
                    if( !file.exists()) {
                        f.mkdir();
                    }
                    //图片名称是否存在，存在删除重新创建
                    f = new File(fileurl+ "/" + plate +".jpg");
                    if( f.exists()) {
                        f.delete();
                    }
                    fio = new FileImageOutputStream(f);
                    fio.write(myTF.getImage());
                } catch( Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fio.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (i >= 0 && !stationID.toString().equals("") && stationID != null) {
                db.insertImageTrafficData(checkId, myTF);
            }
            myTF.getImageReplyStream();
            ClientManager.sendData(myTF.getFeedback(), ctx);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
