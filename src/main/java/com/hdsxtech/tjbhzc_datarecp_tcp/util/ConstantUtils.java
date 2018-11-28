package com.hdsxtech.tjbhzc_datarecp_tcp.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * 系统常量及参数配置工具类 将整个系统所需的常量和参数配置都放在这里
 * 
 */
public class ConstantUtils {

    public static final String HEADER_BSW_ID      = "1";      //消息头中标识位对应的位置（即Field中id）
    public static final String HEADER_LENGTH_ID   = "2";      //消息头中包大小对应的位置（即Field中id）
    public static final String HEADER_MSGID_ID    = "3";      //消息头中消息id对应的位置（即Field中id）


    // 设备心跳信息
    public static final String HEARTBEAT_EQUIPT_ID = "1";  // 心跳信息 设备编码
    public static final String HEARTBEAT_YEAR = "2";  // 年份 设备编码
    public static final String HEARTBEAT_MONTH = "3";  // 月份 设备编码
    public static final String HEARTBEAT_DAY = "4";  // 日期 设备编码
    public static final String HEARTBEAT_HOUR = "5";  // 小时 设备编码
    public static final String HEARTBEAT_MINUTES = "6";  // 分钟 设备编码
    public static final String HEARTBEAT_SECONDS = "7";  // 秒 设备编码
    public static final String HEARTBEAT_IP = "8";  // IP 设备编码
    public static final String HEARTBEAT_PTX = "9";  // 经度 设备编码
    public static final String HEARTBEAT_PTY = "10";  // 纬度 设备编码


    public static final String HEARTBEAT_ANWSER_EQUIPT_ID = "1";  // 纬度 设备识别码
    public static final String HEARTBEAT_ANWSER_DATA = "2";  // 纬度 服务器回复消息


    // 称重数据
    public static final String CHECKWEIGHT_EQUIPT_ID = "1";  // 称重信息 设备编码
    public static final String CHECKWEIGHT_EQUIPT_STATUS = "2";  // 称重信息 设备硬件错误码
    //public static final String CHECKWEIGHT_CHECK_NO = "3";  // 称重信息 设备编码
    public static final String CHECKWEIGHT_CHECK_YEAR = "3";  // 称重信息 年
    public static final String CHECKWEIGHT_CHECK_MONTH = "4";  // 称重信息 月
    public static final String CHECKWEIGHT_CHECK_DAY = "5";  // 称重信息 日
    public static final String CHECKWEIGHT_CHECK_HOUR = "6";  // 称重信息 时
    public static final String CHECKWEIGHT_CHECK_MINUTES = "7";  // 称重信息 分
    public static final String CHECKWEIGHT_CHECK_SECONDS = "8";  // 称重信息 秒
    //public static final String CHECKWEIGHT_CHECK_MILLISECONDS = "10";  // -----称重信息 毫秒
    public static final String CHECKWEIGHT_CHECK_DATANO = "9";  // 称重信息 数据序号
    public static final String CHECKWEIGHT_CHECK_LINE = "10";  // 称重信息 车道号
    public static final String CHECKWEIGHT_CHECK_VEHICLE_TYPE = "11";  // 称重信息 车型
    public static final String CHECKWEIGHT_CHECK_SPEED= "12";  // 称重信息 车速
    public static final String CHECKWEIGHT_CHECK_VEHICLE_NO = "13";  // 称重信息 车牌号码
    public static final String CHECKWEIGHT_CHECK_AXLES = "14";  // 称重信息 车轴数
    public static final String CHECKWEIGHT_CHECK_VEHICLENO_COLOR= "15";  // 称重信息 车牌颜色
    public static final String CHECKWEIGHT_CHECK_TOTAL = "16";  // 称重信息 车辆总重
    public static final String CHECKWEIGHT_CHECK_WEIGHT1 = "17";  // 称重信息 车辆轴一重量
    public static final String CHECKWEIGHT_CHECK_WEIGHT2 = "18";  // 称重信息 车辆轴二重量
    public static final String CHECKWEIGHT_CHECK_WEIGHT3 = "19";  // 称重信息 车辆轴三重量
    public static final String CHECKWEIGHT_CHECK_WEIGHT4 = "20";  // 称重信息 车辆轴四重量
    public static final String CHECKWEIGHT_CHECK_WEIGHT5 = "21";  // 称重信息 车辆轴五重量
    public static final String CHECKWEIGHT_CHECK_WEIGHT6 = "22";  // 称重信息 车辆轴六重量
    public static final String CHECKWEIGHT_CHECK_WEIGHT_OTHER= "23";  // 称重信息 其他轴重量
    public static final String CHECKWEIGHT_CHECK_WEIGHT_LIMIT= "24";  // 称重信息 其他轴重量
    public static final String CHECKWEIGHT_CHECK_VEHICLE_LENGTH= "25";  // 称重信息 车长
    public static final String CHECKWEIGHT_CHECK_VEHICLE_WIDTH = "26";  // 称重信息 车宽
    public static final String CHECKWEIGHT_CHECK_VEHICLE_HEIGHT = "27";  // 称重信息 车高
    public static final String CHECKWEIGHT_CHECK_RESERVED1 = "28";  // 称重信息 预留1
    public static final String CHECKWEIGHT_CHECK_RESERVED2 = "29";  // 称重信息 预留2
    //public static final String CHECKWEIGHT_CHECK_CRC = "32";  // ----称重信息 CRC 校验码
    //public static final String CHECKWEIGHT_CHECK_TAIL = "33";  //  ----称重信息 帧尾

    // 设备称重反馈包结构
    public static final String CHECKWEIGHT_RESPONSE_EQUIPT_ID = "1";  // 称重反馈信息 设备编码
    //public static final String CHECKWEIGHT_RESPONSE_CHECK_NO = "2";  // 称重反馈信息 设备编码
    public static final String CHECKWEIGHT_RESPONSE_YEAR = "2";  // 称重反馈信息 年
    public static final String CHECKWEIGHT_RESPONSE_MONTH = "3";  // 称重反馈信息 月
    public static final String CHECKWEIGHT_RESPONSE_DAY = "4";  // 称重反馈信息 日
    public static final String CHECKWEIGHT_RESPONSE_DATA_NO = "5";  // 称重反馈信息 数据序号
    public static final String CHECKWEIGHT_RESPONSE_RESULT = "6";  // 称重反馈信息 校验结果代码
//    public static final String CHECKWEIGHT_RESPONSE_CRC = "7";  // ----称重反馈信息 CRC 校验码
//    public static final String CHECKWEIGHT_RESPONSE_TAIL = "8";  //  ----称重反馈信息 帧尾

    // 设备称重图片

    public static final String CHECKIMG_EQUIP_ID = "1";  // 称重图片信息 设备编码
    public static final String CHECKIMG_EQUIPT_STATUS = "2";  // 称重图片信息  设备硬件错误码 00 正常 非00 异常
    //public static final String CHECKIMG_CHECK_NO = "3";  // 称重图片数据
    public static final String CHECKIMG_YEAR = "3";  // 称重图片信息 年
    public static final String CHECKIMG_MONTH = "4";  // 称重图片信息 月
    public static final String CHECKIMG_DAY = "5";  // 称重图片信息 日
    public static final String CHECKIMG_HOUR = "6";  // 称重图片信息 小时
    public static final String CHECKIMG_MINUTES = "7";  // 称重图片信息 分钟
    public static final String CHECKIMG_SECONDS = "8";  // 称重图片信息 秒
//    public static final String CHECKIMG_MILLISECONDS = "9";  // ----称重图片信息 毫秒
    public static final String CHECKIMG_DATA_NO = "9";  // 称重图片信息 数据序号
    public static final String CHECKIMG_LINE = "10";  // 称重图片信息 车道号
    public static final String CHECKIMG_IMG_TYPE = "11";  // 称重图片信息 数据类型
    public static final String CHECKIMG_LENGTH = "12";  // 称重图片信息 数据长度
    public static final String CHECKIMG_CONTENT = "13";  // 称重图片信息 文件内容
    public static final String CHECKIMG_RESERVED1 = "14";  // 称重图片信息 预留
    public static final String CHECKIMG_RESERVED2 = "15";  // 称重图片信息 预留
//    public static final String CHECKIMG_CRC = "17";  //----称重图片信息 CRC 校验
//    public static final String CHECKIMG_TAIL = "18";  // ----称重图片信息 帧尾

    // 称重图片反馈数据
    public static final String CHECKIMG_RESPONSE_EQUIPT_ID = "1";  // 称重图片反馈信息 设备编码
    //public static final String CHECKIMG_RESPONSE_CHECK_NO = "2";  // 称重图片数据 唯一标识
    public static final String CHECKIMG_RESPONSE_YEAR = "2";  // 称重图片反馈信息 年
    public static final String CHECKIMG_RESPONSE_MONTH = "3";  // 称重图片反馈信息 月
    public static final String CHECKIMG_RESPONSE_DAY = "4";  // 称重图片反馈信息 日
    public static final String CHECKIMG_RESPONSE_DATA_NO = "5";  // 称重图片反馈信息 收到的数据序号
    public static final String CHECKIMG_RESPONSE_IMGDATA_TYPE = "6";  // 称重图片反馈信息 收到的图片数据类型
    public static final String CHECKIMG_RESPONSE_RESULT = "7";  // 称重图片反馈信息校验结果信息代码
//    public static final String CHECKIMG_RESPONSE_CRC = "8";  // ----称重图片反馈信息 CRC 校验
//    public static final String CHECKIMG_RESPONSE_TAIL = "9";  // ----称重图片反馈信息 帧尾


    public static final String KEY_CENTER	            = "overload";	  //XML文件对应消息种类
    public static final String CONFIG_PROPERTY_FILE     = "config.properties";

    public static final String MSG_CENTER      = "msgcenter.xml";
    public static final String MSG_CONFIG      = "msgconfig";
    public static final String MSG_SERIES      = "msgseries";
    public static final String MSG_TYPE        = "type";
    public static final String MSG_HEADER      = "header";
    public static final String MSG_BODY        = "body";
    public static final String MSG_FIELD       = "field";

    public static final String FIELD_ID        = "id";
    public static final String FIELD_TYPE      = "type";
    public static final String FIELD_NAME      = "name";
    public static final String FIELD_CNAME     = "cname";
    public static final String FIELD_LENGTH    = "length";

    public static final String BODY_ID         = "id";
    public static final String BODY_MSGNAME    = "msgname";
    
    public static String byte2hex(byte[] buffer) {
        String h = "";
        for (int i = 0; i < buffer.length; i++) {
            String temp = Integer.toHexString(buffer[i] & 0xFF);
            if (temp.length() == 1) {
                temp = "0" + temp;
            }
            h = h + " " + temp;
        }
        return h;
    }

    public static int bytesToInt(byte[] ary) {
        int value;
        value = (int) ((ary[0]*16*16)+(ary[1]*16)+ary[2]);
        return value;
    }

    // 验证设备是否存在
    public static boolean isEquiptIdExist(String equiptId ) {
        boolean r = false;
        for( String eid : Constants.EQUIPT_IDS ) {
            if( eid.equals(equiptId)) {
                r = true;
                break;
            }
        }
        return r;
    }

    // 判断心跳时间是否正确 相差的时间小于 5 分钟 就 视为 正确
    public static boolean isRightHeartTime(int year, int month, int day,int hour , int minutes, int seconds) {

        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String s = year + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds;
            Date oeDate = sdf.parse(s);
            Date dscDate = new Date();
            return Math.abs(oeDate.getTime()-dscDate.getTime()) <= 1000*60*5 ;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 判断设备的位置信息 是否正确
    public static boolean isRightPosition(Double ptx, Double pty) {
        // TODO 判断设备的位置误差
        return true;
    }

}
