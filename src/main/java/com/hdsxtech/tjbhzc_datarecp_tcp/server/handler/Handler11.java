package com.hdsxtech.tjbhzc_datarecp_tcp.server.handler;

import com.hdsxtech.tjbhzc_datarecp_tcp.bean.DdCheckWeight;
import com.hdsxtech.tjbhzc_datarecp_tcp.factory.MessageID;
import com.hdsxtech.tjbhzc_datarecp_tcp.factory.MsgBean;
import com.hdsxtech.tjbhzc_datarecp_tcp.msg.IHandler;
import com.hdsxtech.tjbhzc_datarecp_tcp.server.ClientManager;
import com.hdsxtech.tjbhzc_datarecp_tcp.server.Traffic;
import com.hdsxtech.tjbhzc_datarecp_tcp.util.*;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * 客户端车辆状态信息handler
 *
 */
public class Handler11 implements IHandler {
    private Logger logger = LoggerFactory.getLogger(Handler11.class);


    public void doHandle(MsgBean msg, ChannelHandlerContext ctx) {
        logger.info("处理客称重消息：");
        try {
            boolean result = false;
            //      System.out.println("车辆消息："+msg);
  /*          Object lsh = msg.getHeader().getByID(ConstantUtils.HEADER_LSH_ID).getValue();
            Object msdId = msg.getHeader().getByID(ConstantUtils.HEADER_MSGID_ID).getValue();
            int sx = (int)msg.getHeader().getByID(ConstantUtils.HEADER_SX_ID).getValue();
            //分包标识（暂时未用）
            int fb = (sx & 0x2000) >> 13;
            //加密标识（暂时未用）
            int jm = (sx & 0xe00) >> 10;
            //消息体长度（暂时未用）
            int length = sx & 0x1ff;

             // 设备编码
            String pageType = msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_PACKAGE_TYPE).getValue().toString();
            byte[] bcheckNo = (byte[])msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_EQUIPT_ID).getValue();
            String checkNo = new String(bcheckNo);
            String checkType = msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_EQUIPT_STATUS).getValue().toString();
*/
            byte[] equiptid=(byte[])msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_EQUIPT_ID).getValue();
            int checktype=(int)msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_EQUIPT_STATUS).getValue();
            //byte[] check_no=(byte[])msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_NO).getValue();
            int year=(int)msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_YEAR).getValue();
            int month=(int)msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_MONTH).getValue();
            int day=(int)msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_DAY).getValue();
            int hour=(int)msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_HOUR).getValue();
            int minute=(int)msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_MINUTES).getValue();
            int second=(int)msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_SECONDS).getValue();
            //int millisecond=(int)msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_MILLISECONDS).getValue();
            int datano=(int)msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_DATANO).getValue();
            int checkline=(int)msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_LINE ).getValue();
            int vehicletype=(int)msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_VEHICLE_TYPE ).getValue();
            int speed=(int)msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_SPEED ).getValue();
            byte[] vehicleno=(byte[])msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_VEHICLE_NO ).getValue();
            int axles=(int)msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_AXLES ).getValue();
            int vehiclenocolor=(int)msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_VEHICLENO_COLOR).getValue();
            byte[] total=(byte[])msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_TOTAL ).getValue();
            int total1=ConstantUtils.bytesToInt(total);
            int weight1=(int)msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_WEIGHT1 ).getValue();
            int weight2=(int)msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_WEIGHT2 ).getValue();
            int weight3=(int)msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_WEIGHT3 ).getValue();
            int weight4=(int)msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_WEIGHT4).getValue();
            int weight5=(int)msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_WEIGHT5).getValue();
            int weight6=(int)msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_WEIGHT6).getValue();
            int weightoth=(int)msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_WEIGHT_OTHER).getValue();
            int weightlimit=(int)msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_WEIGHT_LIMIT).getValue();
            int vehiclelength=(int)msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_VEHICLE_LENGTH).getValue();
            int vehiclewidth=(int)msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_VEHICLE_WIDTH).getValue();
            int vehicleheight=(int)msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_VEHICLE_HEIGHT).getValue();
            //int reservd1=(int)msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_RESERVED1).getValue();
            //int reservd2=(int)msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_RESERVED2 ).getValue();

            //byte[] crc = (byte[])msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_CHECK_CRC).getValue();



            MsgBean response = new MsgBean(msg, MessageID.MESSAGE_CHECKWEIGHT_RESPONSE_ID, ConstantUtils.KEY_CENTER);
            Calendar cal = Calendar.getInstance();
            int r_year = cal.get(Calendar.YEAR);//获取年份
            int r_month=cal.get(Calendar.MONTH);//获取月份
            int r_day=cal.get(Calendar.DATE);//获取日
            logger.info("服务器端收到的单车数据包 : "
                    + " equiptid : " + new String(equiptid) + "\n"
                    + " checktype : " + checktype + "\n"
                    + " year : " + year + "\n"
                    + " month : " + month + "\n"
                    + " day : " + day + "\n"
                    + " hour : " + hour + "\n"
                    + " minute : " + minute + "\n"
                    + " second : " +second + "\n"
                    + " datano : " + datano + "\n"
                    + " line : " + checkline + "\n"
                    + " vehicletype : " + vehicletype + "\n"
                    + " speed : " + speed + "\n"
                    + " vehicleno : "  + new String(vehicleno) + "\n"
                    + " axles : " + axles + "\n"
                    + " vehiclenocolor : " + vehiclenocolor + "\n"
                    + " total1 : " + total1 + "\n"
                    + " weight1 : " + weight1 + "\n"
                    + " weight2 : " + weight2 + "\n"
                    + " weight3 : " + weight3 + "\n"
                    + " weight4 : " + weight4 + "\n"
                    + " weight5 : " + weight5 + "\n"
                    + " weight6 : " + weight6 + "\n"
                    + " weightoth : " + weightoth + "\n"
                    + " weightlimit : " + weightlimit + "\n"
                    + " vehiclelength : " + vehiclelength + "\n"
                    + " vehiclewidth : " + vehiclewidth + "\n"
                    + " vehicleheight : " + vehicleheight + "\n"
//                    + " reservd1 : " + reservd1 + "\n"
//                    + " reservd2 : " + reservd2 + "\n"

            );
            response.getBody().getByID(ConstantUtils.CHECKWEIGHT_RESPONSE_EQUIPT_ID).setValue(equiptid);
            //response.getBody().getByID(ConstantUtils.CHECKWEIGHT_RESPONSE_CHECK_NO).setValue(check_no);
            response.getBody().getByID(ConstantUtils.CHECKWEIGHT_RESPONSE_YEAR).setValue(r_year);
            response.getBody().getByID(ConstantUtils.CHECKWEIGHT_RESPONSE_MONTH).setValue(r_month);
            response.getBody().getByID(ConstantUtils.CHECKWEIGHT_RESPONSE_DAY).setValue(r_day);
            response.getBody().getByID(ConstantUtils.CHECKWEIGHT_RESPONSE_DATA_NO).setValue(datano);
//            if(!ConstantUtils.isEquiptIdExist(new String(equiptid)) ) {
//                response.getBody().getByID(ConstantUtils.CHECKWEIGHT_RESPONSE_RESULT).setValue(0x02);
//                ClientManager.sendAnswerData(response, ctx);
//                return;
//            }
//            if(!ConstantUtils.isRightHeartTime(year,month,day,hour,minute,second) ) {
//                response.getBody().getByID(ConstantUtils.CHECKWEIGHT_RESPONSE_RESULT).setValue(0x03);
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
//                //           if( !newCrc.equals(new String(crc))) {
//                response.getBody().getByID(ConstantUtils.CHECKWEIGHT_RESPONSE_RESULT).setValue(0x04);
//                ClientManager.sendAnswerData(response, ctx);
//                return;
//            }
            response.getBody().getByID(ConstantUtils.CHECKWEIGHT_RESPONSE_RESULT).setValue(0xFFFF);
            byte [] tails = new byte[2];
            tails[0] = (byte)238;
            tails[1] = (byte)238;
            //response.getBody().getByID(ConstantUtils.CHECKWEIGHT_RESPONSE_TAIL).setValue(tails);
            ClientManager.sendAnswerData(response, ctx);


            //-----入库 start-----
            //封装称重数据
            DdCheckWeight dw = new DdCheckWeight();
            dw.setEquipId(new String(equiptid));
            dw.setCheckType(checktype + "");
            dw.setAxles(axles + "");
            String checkno = Constants.getCheckNo();//根据规则获取checkno
            dw.setCheckNo(checkno);
            //  dw.setSiteId(checkData.getSiteId());
            dw.setVehicleNo(new String(vehicleno));
            dw.setVehicleType(vehicletype+"");
            dw.setSpeed(speed+"");
            dw.setLine(checkline+"");
            //拼接创建时间
            String str = year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh24:mm:ss");
            try {
                dw.setCheckTime(sdf.parse(str));
            }catch (Exception e){
                e.printStackTrace();
            }
            dw.setColor(vehiclenocolor+"");
            dw.setHeight(vehicleheight);
            dw.setLength(vehiclelength);
            dw.setWidth(vehiclewidth);
                       dw.setTotal(new BigDecimal(total1));
            dw.setWeight1(new BigDecimal(weight1));
            dw.setWeight2(new BigDecimal(weight2));
            dw.setWeight3(new BigDecimal(weight3));
            dw.setWeight4(new BigDecimal(weight4));
            dw.setWeight5(new BigDecimal(weight5));
            dw.setWeight6(new BigDecimal(weight6));
            dw.setWeightOther(new BigDecimal(weightoth));

            //根据轴数、车辆类型获取车辆超重
            String ax = axles+"";//轴数
            String vectype = vehicletype+"";//车辆类型
            Integer t = total1;//总重：车+货
            Integer ot = 0;//
            if("2".equals(ax) && "21".equals(vectype)){//2轴--载货汽车：标重18t=18*1000=18000kg
                ot = t-18000;
            }else
            if("3".equals(ax) && ("31".equals(vectype) || "32".equals(vectype))){//3轴
                if ("31".equals(vectype) || "32".equals(vectype)){//中置轴挂车列车、铰接列车：标重27t=27*1000=27000k
                    ot = t-27000;
                }
                if ("33".equals(vectype) || "34".equals(vectype)){//载货汽车：标重25t=25*1000=25000kg
                    ot = t-25000;
                }
            }else
            if("4".equals(ax)){//4轴
                if ("41".equals(vectype) || "43".equals(vectype)){//中置轴挂车列车、铰接列车：标重36t=36*1000=36000kg
                    ot = t-36000;
                }
                if ("42".equals(vectype)){//中置轴挂车列车：标重35t=35*1000=35000kg
                    ot = t-35000;
                }
//                    if ("44".equals(vectype)){//全挂车列车：
//                        ot = t-0;
//                    }
                if ("45".equals(vectype)){//载货汽车：标重31t=31*1000=31000kg
                    ot = t-31000;
                }
            }else
            if("5".equals(ax)){//5轴
                if ("51".equals(vectype) || "52".equals(vectype) || "53".equals(vectype)
                        || "54".equals(vectype) || "56".equals(vectype) || "57".equals(vectype)){//中置轴挂车列车、铰接列车、全挂汽车列车：标重43t=43*1000=43000kg
                    ot = t-43000;
                }
                if ("55".equals(vectype)){//铰接列车：标重42t=42*1000=42000kg
                    ot = t-42000;
                }
            }else
            if("6".equals(ax)){//轴
                if ("61".equals(vectype) || "63".equals(vectype) || "65".equals(vectype)
                        || "68".equals(vectype) ){//中置轴挂车列车、铰接列车、全挂汽车列车：标重49t=49*1000=49000kg
                    ot = t-49000;
                }
                if ("62".equals(vectype) || "64".equals(vectype) || "66".equals(vectype)
                        || "67".equals(vectype) || "59".equals(vectype) ){//中置轴挂车列车、铰接列车、全挂汽车列车：标重46t=46*1000=46000kg
                    ot = t-46000;
                }

            }
            if (ot > 0){
                dw.setOverTotal(new BigDecimal(ot));//超重
            }

            DbUtil db = new DbUtil();
            boolean b = true;
            b = db.insertCheckWeightData(dw);
                      System.out.println("11入库："+b);
            //-----入库 end-----

        } catch (NumberFormatException e) {
            logger.error("车辆状态消息处理异常");
            e.printStackTrace();
        }
    }

    @Override
    public void doHandle(byte[] m, ChannelHandlerContext ctx) {
        Traffic myTF = new Traffic();
        DbUtil db = new DbUtil();
        try {
            myTF.DWFXCZFTrafficInfoStream(m);
            StringBuffer stationID = new StringBuffer("");
            Site site = db.getZCStation(myTF.getEquipCode(), stationID);
            //if (!stationID.toString().equals("") && stationID != null) {
                myTF.setStationID(stationID.toString());
                db.insertServerSingleTrafficData(myTF, 5);
            //}
            if(site.getSite_type()!=null){
                if (site.getSite_type().equals("10")) {
                    this.logger.info("非现场数据~~~id:" + myTF.getSingleVehicleID() + "~~~" + myTF.getPlate() + "~~~" + myTF.getEquipCode() + "~~~" + myTF.getYear() + "~~~" + String.format("%02d", new Object[]{Integer.valueOf(myTF.getMonth())}) + "~~~" + String.format("%02d", new Object[]{Integer.valueOf(myTF.getDay())}) + "~~~" + String.format("%02d", new Object[]{Integer.valueOf(myTF.getHour())}) + "~~~" + String.format("%02d", new Object[]{Integer.valueOf(myTF.getMinute())}) + "~~~" + String.format("%02d", new Object[]{Integer.valueOf(myTF.getSecond())}) + "~~~" + myTF.getStationCode() + "~~~OK");
                } else if (site.getSite_type().equals("8")) {
                    this.logger.info("预检数据~~~id:" + myTF.getSingleVehicleID() + "~~~" + myTF.getPlate() + "~~~" + myTF.getEquipCode() + "~~~" + myTF.getYear() + "~~~" + String.format("%02d", new Object[]{Integer.valueOf(myTF.getMonth())}) + "~~~" + String.format("%02d", new Object[]{Integer.valueOf(myTF.getDay())}) + "~~~" + String.format("%02d", new Object[]{Integer.valueOf(myTF.getHour())}) + "~~~" + String.format("%02d", new Object[]{Integer.valueOf(myTF.getMinute())}) + "~~~" + String.format("%02d", new Object[]{Integer.valueOf(myTF.getSecond())}) + "~~~" + myTF.getStationCode() + "~~~OK");
                } else {
                    this.logger.info("高清抓拍数据~~~id:" + myTF.getSingleVehicleID() + "~~~" + myTF.getEquipCode() + "~~~" + myTF.getYear() + "~~~" + String.format("%02d", new Object[]{Integer.valueOf(myTF.getMonth())}) + "~~~" + String.format("%02d", new Object[]{Integer.valueOf(myTF.getDay())}) + "~~~" + String.format("%02d", new Object[]{Integer.valueOf(myTF.getHour())}) + "~~~" + String.format("%02d", new Object[]{Integer.valueOf(myTF.getMinute())}) + "~~~" + myTF.getStationCode() + "~~~OK");
                }
            }
            myTF.getFXCZFVehicleReplyStream();
            ClientManager.sendData(myTF.getFeedback(), ctx);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
