//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hdsxtech.tjbhzc_datarecp_tcp.server;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;

public class Traffic {
    Logger logger = Logger.getLogger("Traffic");

    private int[] intTrafficData = new int[0];// 发过来的字节流，包括包头，包尾，验证码，数据
    private byte[] byteTrafficData = new byte[0];// 发过来的字节流，包括包头，包尾，验证码，数据
    private byte[] feedback0A = new byte[13];
    private byte[] feedback02and08 = new byte[26];
    private int treatyType = 2010;// 数据协议类型
    private int trafficDataLength = 0;// 数据段长度
    private int realTrafficDataLength = 0;// 实际数据长度
    private int dataType = 0;// 数据类型，实时交通数据包或者是命令包
    private String data06TreatyType = "";// 06协议数据类型，实时交通数据包或者是命令包
    private String equipCode = "";// 设备编号
    private String equipLevel = "";// 设备级别
    private int vehicleTypeLevel = 0;// 车型级别
    private String stationID = "";//站点主键ID
    private String stationCode = "";// 站点编号
    // “01”表示调查包括分车类（型）的交通流量、平均地点车速、跟车百分比、平均车头间距、时间占有率在内的项目，但不调查预留字段内容；
    // “02”表示调查所有项目，包括分车类（型）的交通流量、车速、跟车百分比、平均车头间距、时间占有率等项目以及两个预留字段的内容。
    // “11”表示只调查上行项目，包括分车类（型）的交通流量、平均地点车速、跟车百分比、平均车头间距、时间占有率在内的项目，但不调查预留字段内容；
    // “12”表示只调查上行所有项目，包括分车类（型）的交通流量、车速、跟车百分比、平均车头间距、时间占有率等项目以及两个预留字段的内容。
    // “21”表示只调查下行项目，包括分车类（型）的交通流量、平均地点车速、跟车百分比、平均车头间距、时间占有率在内的项目，但不调查预留字段内容；
    // “22”表示只调查下行所有项目，包括分车类（型）的交通流量、车速、跟车百分比、平均车头间距、时间占有率等项目以及两个预留字段的内容。
    private int contentType = 0;// 调查内容
    private int equipError = 0;// 硬件错误
    private int year = 2000;// 年
    private int month = 0;// 月
    private int day = 0;// 日
    private Calendar passTime = Calendar.getInstance();// 检测时间
    private Calendar acceptTime = Calendar.getInstance();// 接收时间
    private int cycTime = 1;// 数据处理周期
    private int timeSeq = 0;// 时间序号
    // 设备所处调查断面双向车道数，为1及2-18间的整型数，其中2-18间为“2”的整数倍。“01”代表单车道，
    // “02”代表双车道，依此类推
    private int totalLineNum = 0;// 所有车道数目
    // 实际上的车道数，有些发送上来的是单方向的车辆数据，实际的车道数是发送上来的车道数的一半
    int actualTotalLineNum = 0;

    // 单车道代码规则：上行01，下行03。2车道以上公路车道号代码规则：上行从内至外按11、12、13…连续编号；
    // 下行按31、32、33…连续编号。车道号排列规则：先上行、后下行，同一个行驶方向先内侧车道、后外侧车道。
    // 断面调查：0A
    private int lineNum = 0;// 车道号
    private int genCheRate = 0;// 跟车率
    private int cheTouDistance = 0;// 车头距
    private int shiJianZhanYouRate = 0;// 时间占有率
    private String crcCheck = "";// 实际数据的验证码
    private String shouldCRC = "";// 应该生成的验证码
    private String checkResult = "FFFF";// 数据校验结果
    private String checkResultContent = "数据正常";// 数据校验结果内容
    private String iniEquipCode = "";// 最初设备发过来的设备型号
    private int iniTotalLineNum = 0;// 最初所有车道数目

    private Calendar equipTime = Calendar.getInstance();// 设备时间
    private String equipIP = "";// 设备IP
    private int genCheRateTime = 0;// 跟车百分比鉴别时间
    private int transType = 0;// 传输方式
    private double jingDu = 0;// 经度
    private double weiDu = 0;// 纬度
    private int haiBa = 0;// 海拔

    private int isSplitData = 0;// 是否是拆分的数据

    private int hour = 0;// 时
    private int minute = 0;// 分
    private int second = 0;// 秒
    private int millisecond = 0;// 毫秒
    private int singleVehicleID = 0;// 单车序号
    private int lineCode = 0;// 车道号
    private int vehicleType = 0;// 车型
    private int axleCount = 2;// 轴数
    private int vehicleSpeed = 0;// 车速
    private int totalWeight = 0;// 总重
    private int[] singleAxleWeight = { 0, 0, 0, 0, 0, 0, 0, 0 };
    private int limiteWeight = 0;
    private int length = 0;// 长
    private int width = 0;// 宽
    private int height = 0;// 高
    private String plate = "";// 车牌
    private String color = "";// 颜色
    private String vehicleColor = "";//车身颜色
    private int imageType = 0;
    private byte[] image = new byte[0];
    private byte[] feedback = new byte[0];
    private String distCode = "";//行政区划

    static  int      crctab[]           = {
            0, 4129, 8258, 12387, 16516, 20645, 24774, 28903, 33032, 37161,
            41290, 45419, 49548, 53677, 57806, 61935, 4657, 528, 12915, 8786,
            21173, 17044, 29431, 25302, 37689, 33560, 45947, 41818, 54205, 50076,
            62463, 58334, 9314, 13379, 1056, 5121, 25830, 29895, 17572, 21637,
            42346, 46411, 34088, 38153, 58862, 62927, 50604, 54669, 13907, 9842,
            5649, 1584, 30423, 26358, 22165, 18100, 46939, 42874, 38681, 34616,
            63455, 59390, 55197, 51132, 18628, 22757, 26758, 30887, 2112, 6241,
            10242, 14371, 51660, 55789, 59790, 63919, 35144, 39273, 43274, 47403,
            23285, 19156, 31415, 27286, 6769, 2640, 14899, 10770, 56317, 52188,
            64447, 60318, 39801, 35672, 47931, 43802, 27814, 31879, 19684, 23749,
            11298, 15363, 3168, 7233, 60846, 64911, 52716, 56781, 44330, 48395,
            36200, 40265, 32407, 28342, 24277, 20212, 15891, 11826, 7761, 3696,
            65439, 61374, 57309, 53244, 48923, 44858, 40793, 36728, 37256, 33193,
            45514, 41451, 53516, 49453, 61774, 57711, 4224, 161, 12482, 8419,
            20484, 16421, 28742, 24679, 33721, 37784, 41979, 46042, 49981, 54044,
            58239, 62302, 689, 4752, 8947, 13010, 16949, 21012, 25207, 29270,
            46570, 42443, 38312, 34185, 62830, 58703, 54572, 50445, 13538, 9411,
            5280, 1153, 29798, 25671, 21540, 17413, 42971, 47098, 34713, 38840,
            59231, 63358, 50973, 55100, 9939, 14066, 1681, 5808, 26199, 30326,
            17941, 22068, 55628, 51565, 63758, 59695, 39368, 35305, 47498, 43435,
            22596, 18533, 30726, 26663, 6336, 2273, 14466, 10403, 52093, 56156,
            60223, 64286, 35833, 39896, 43963, 48026, 19061, 23124, 27191, 31254,
            2801, 6864, 10931, 14994, 64814, 60687, 56684, 52557, 48554, 44427,
            40424, 36297, 31782, 27655, 23652, 19525, 15522, 11395, 7392, 3265,
            61215, 65342, 53085, 57212, 44955, 49082, 36825, 40952, 28183, 32310,
            20053, 24180, 11923, 16050, 3793, 7920
    };
    static  short[]  auchCRCHi          = new short[]{(short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64, (short) 1, (short) 192, (short) 128, (short) 65, (short) 1, (short) 192, (short) 128, (short) 65, (short) 0, (short) 193, (short) 129, (short) 64};
    static  short[]  auchCRCLo          = new short[]{(short) 0, (short) 192, (short) 193, (short) 1, (short) 195, (short) 3, (short) 2, (short) 194, (short) 198, (short) 6, (short) 7, (short) 199, (short) 5, (short) 197, (short) 196, (short) 4, (short) 204, (short) 12, (short) 13, (short) 205, (short) 15, (short) 207, (short) 206, (short) 14, (short) 10, (short) 202, (short) 203, (short) 11, (short) 201, (short) 9, (short) 8, (short) 200, (short) 216, (short) 24, (short) 25, (short) 217, (short) 27, (short) 219, (short) 218, (short) 26, (short) 30, (short) 222, (short) 223, (short) 31, (short) 221, (short) 29, (short) 28, (short) 220, (short) 20, (short) 212, (short) 213, (short) 21, (short) 215, (short) 23, (short) 22, (short) 214, (short) 210, (short) 18, (short) 19, (short) 211, (short) 17, (short) 209, (short) 208, (short) 16, (short) 240, (short) 48, (short) 49, (short) 241, (short) 51, (short) 243, (short) 242, (short) 50, (short) 54, (short) 246, (short) 247, (short) 55, (short) 245, (short) 53, (short) 52, (short) 244, (short) 60, (short) 252, (short) 253, (short) 61, (short) 255, (short) 63, (short) 62, (short) 254, (short) 250, (short) 58, (short) 59, (short) 251, (short) 57, (short) 249, (short) 248, (short) 56, (short) 40, (short) 232, (short) 233, (short) 41, (short) 235, (short) 43, (short) 42, (short) 234, (short) 238, (short) 46, (short) 47, (short) 239, (short) 45, (short) 237, (short) 236, (short) 44, (short) 228, (short) 36, (short) 37, (short) 229, (short) 39, (short) 231, (short) 230, (short) 38, (short) 34, (short) 226, (short) 227, (short) 35, (short) 225, (short) 33, (short) 32, (short) 224, (short) 160, (short) 96, (short) 97, (short) 161, (short) 99, (short) 163, (short) 162, (short) 98, (short) 102, (short) 166, (short) 167, (short) 103, (short) 165, (short) 101, (short) 100, (short) 164, (short) 108, (short) 172, (short) 173, (short) 109, (short) 175, (short) 111, (short) 110, (short) 174, (short) 170, (short) 106, (short) 107, (short) 171, (short) 105, (short) 169, (short) 168, (short) 104, (short) 120, (short) 184, (short) 185, (short) 121, (short) 187, (short) 123, (short) 122, (short) 186, (short) 190, (short) 126, (short) 127, (short) 191, (short) 125, (short) 189, (short) 188, (short) 124, (short) 180, (short) 116, (short) 117, (short) 181, (short) 119, (short) 183, (short) 182, (short) 118, (short) 114, (short) 178, (short) 179, (short) 115, (short) 177, (short) 113, (short) 112, (short) 176, (short) 80, (short) 144, (short) 145, (short) 81, (short) 147, (short) 83, (short) 82, (short) 146, (short) 150, (short) 86, (short) 87, (short) 151, (short) 85, (short) 149, (short) 148, (short) 84, (short) 156, (short) 92, (short) 93, (short) 157, (short) 95, (short) 159, (short) 158, (short) 94, (short) 90, (short) 154, (short) 155, (short) 91, (short) 153, (short) 89, (short) 88, (short) 152, (short) 136, (short) 72, (short) 73, (short) 137, (short) 75, (short) 139, (short) 138, (short) 74, (short) 78, (short) 142, (short) 143, (short) 79, (short) 141, (short) 77, (short) 76, (short) 140, (short) 68, (short) 132, (short) 133, (short) 69, (short) 135, (short) 71, (short) 70, (short) 134, (short) 130, (short) 66, (short) 67, (short) 131, (short) 65, (short) 129, (short) 128, (short) 64};

    public Traffic() {
    }
    /**
     * @param isSplitData
     *            the isSplitData to set
     */
    public void setIsSplitData(int isSplitData) {
        this.isSplitData = isSplitData;
    }

    /**
     * @return the isSplitData
     */
    public int getIsSplitData() {
        return isSplitData;
    }

    public String getData06TreatyType() {
        return data06TreatyType;
    }

    public void setData06TreatyType(String data06TreatyType) {
        this.data06TreatyType = data06TreatyType;
    }

    public int getTreatyType() {
        return treatyType;
    }

    public void setTreatyType(int treatyType) {
        this.treatyType = treatyType;
    }

    public byte[] getByteTrafficData() {
        return byteTrafficData;
    }

    public void setByteTrafficData(byte[] byteTrafficData) {
        this.byteTrafficData = byteTrafficData;
    }

    public byte[] getFeedback0A() {
        return feedback0A;
    }

    public void setFeedback0A(byte[] feedback0a) {
        feedback0A = feedback0a;
    }

    public byte[] getFeedback02and08() {
        return feedback02and08;
    }

    public void setFeedback02and08(byte[] feedback02and08) {
        this.feedback02and08 = feedback02and08;
    }

    public int[] getIntTrafficData() {
        return intTrafficData;
    }

    public void setIntTrafficData(int[] intTrafficData) {
        this.intTrafficData = intTrafficData;
    }

    public int getTrafficDataLength() {
        return trafficDataLength;
    }

    public void setTrafficDataLength(int trafficDataLength) {
        this.trafficDataLength = trafficDataLength;
    }

    public int getRealTrafficDataLength() {
        return realTrafficDataLength;
    }

    public void setRealTrafficDataLength(int realTrafficDataLength) {
        this.realTrafficDataLength = realTrafficDataLength;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getEquipCode() {
        return equipCode;
    }

    public void setEquipCode(String equipCode) {
        this.equipCode = equipCode;
    }

    public String getEquipLevel() {
        return equipLevel;
    }

    public void setEquipLevel(String equipLevel) {
        this.equipLevel = equipLevel;
    }

    public int getVehicleTypeLevel() {
        return vehicleTypeLevel;
    }

    public void setVehicleTypeLevel(int vehicleTypeLevel) {
        this.vehicleTypeLevel = vehicleTypeLevel;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    /**
     * <B>取得：</B>stationID<BR>
     * @return String
     */
    public String getStationID() {
        return stationID;
    }

    /**
     * <B>设定：</B>stationID<BR>
     * @param stationID
     */
    public void setStationID(String stationID) {
        this.stationID = stationID;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public int getEquipError() {
        return equipError;
    }

    public void setEquipError(int equipError) {
        this.equipError = equipError;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Calendar getPassTime() {
        return passTime;
    }

    public void setPassTime(Calendar passTime) {
        this.passTime = passTime;
    }

    public Calendar getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(Calendar acceptTime) {
        this.acceptTime = acceptTime;
    }

    public int getCycTime() {
        return cycTime;
    }

    public void setCycTime(int cycTime) {
        this.cycTime = cycTime;
    }

    public int getTimeSeq() {
        return timeSeq;
    }

    public void setTimeSeq(int timeSeq) {
        this.timeSeq = timeSeq;
    }

    public int getTotalLineNum() {
        return totalLineNum;
    }

    public void setTotalLineNum(int totalLineNum) {
        this.totalLineNum = totalLineNum;
    }

    public int getActualTotalLineNum() {
        return actualTotalLineNum;
    }

    public void setActualTotalLineNum(int actualTotalLineNum) {
        this.actualTotalLineNum = actualTotalLineNum;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

    public int getGenCheRate() {
        return genCheRate;
    }

    public void setGenCheRate(int genCheRate) {
        this.genCheRate = genCheRate;
    }

    public int getCheTouDistance() {
        return cheTouDistance;
    }

    public void setCheTouDistance(int cheTouDistance) {
        this.cheTouDistance = cheTouDistance;
    }

    public int getShiJianZhanYouRate() {
        return shiJianZhanYouRate;
    }

    public void setShiJianZhanYouRate(int shiJianZhanYouRate) {
        this.shiJianZhanYouRate = shiJianZhanYouRate;
    }

    public String getCrcCheck() {
        return crcCheck;
    }

    public void setCrcCheck(String crcCheck) {
        this.crcCheck = crcCheck;
    }

    public String getShouldCRC() {
        return shouldCRC;
    }

    public void setShouldCRC(String shouldCRC) {
        this.shouldCRC = shouldCRC;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public String getCheckResultContent() {
        return checkResultContent;
    }

    public void setCheckResultContent(String checkResultContent) {
        this.checkResultContent = checkResultContent;
    }

    public String getIniEquipCode() {
        return iniEquipCode;
    }

    public void setIniEquipCode(String iniEquipCode) {
        this.iniEquipCode = iniEquipCode;
    }

    public int getIniTotalLineNum() {
        return iniTotalLineNum;
    }

    public void setIniTotalLineNum(int iniTotalLineNum) {
        this.iniTotalLineNum = iniTotalLineNum;
    }

    public Calendar getEquipTime() {
        return equipTime;
    }

    public void setEquipTime(Calendar equipTime) {
        this.equipTime = equipTime;
    }

    public String getEquipIP() {
        return equipIP;
    }

    public void setEquipIP(String equipIP) {
        this.equipIP = equipIP;
    }

    public int getGenCheRateTime() {
        return genCheRateTime;
    }

    public void setGenCheRateTime(int genCheRateTime) {
        this.genCheRateTime = genCheRateTime;
    }

    public int getTransType() {
        return transType;
    }

    public void setTransType(int transType) {
        this.transType = transType;
    }

    public double getJingDu() {
        return jingDu;
    }

    public void setJingDu(double jingDu) {
        this.jingDu = jingDu;
    }

    public double getWeiDu() {
        return weiDu;
    }

    public void setWeiDu(double weiDu) {
        this.weiDu = weiDu;
    }

    public int getHaiBa() {
        return haiBa;
    }

    public void setHaiBa(int haiBa) {
        this.haiBa = haiBa;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getMillisecond() {
        return millisecond;
    }

    public void setMillisecond(int millisecond) {
        this.millisecond = millisecond;
    }

    public int getSingleVehicleID() {
        return singleVehicleID;
    }

    public void setSingleVehicleID(int singleVehicleID) {
        this.singleVehicleID = singleVehicleID;
    }

    public int getLineCode() {
        return lineCode;
    }

    public void setLineCode(int lineCode) {
        this.lineCode = lineCode;
    }

    public int getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(int vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getVehicleSpeed() {
        return vehicleSpeed;
    }

    public void setVehicleSpeed(int vehicleSpeed) {
        this.vehicleSpeed = vehicleSpeed;
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(int totalWeight) {
        this.totalWeight = totalWeight;
    }

    public int[] getSingleAxleWeight() {
        return singleAxleWeight;
    }

    public void setSingleAxleWeight(int[] singleAxleWeight) {
        this.singleAxleWeight = singleAxleWeight;
    }

    /**
     * <B>取得：</B>limiteWeight<BR>
     * @return int
     */
    public int getLimiteWeight() {
        return limiteWeight;
    }

    /**
     * <B>设定：</B>limiteWeight<BR>
     * @param limiteWeight
     */
    public void setLimiteWeight(int limiteWeight) {
        this.limiteWeight = limiteWeight;
    }

    public int getAxleCount() {
        return axleCount;
    }

    public void setAxleCount(int axleCount) {
        this.axleCount = axleCount;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    /**
     * <B>取得：</B>vehicleColor<BR>
     * @return String
     */
    public String getVehicleColor() {
        return vehicleColor;
    }

    /**
     * <B>设定：</B>vehicleColor<BR>
     * @param vehicleColor
     */
    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    /**
     * <B>取得：</B>imageType<BR>
     * @return int
     */
    public int getImageType() {
        return imageType;
    }

    /**
     * <B>设定：</B>imageType<BR>
     * @param imageType
     */
    public void setImageType(int imageType) {
        this.imageType = imageType;
    }

    /**
     * <B>取得：</B>image<BR>
     * @return byte[]
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * <B>设定：</B>image<BR>
     * @param image
     */
    public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getFeedback() {
        return feedback;
    }

    public void setFeedback(byte[] feedback1a) {
        feedback = feedback1a;
    }

    public String getDistCode() {
        return distCode;
    }

    public void setDistCode(String distCode) {
        this.distCode = distCode;
    }

    public String DWSingleVehicleTrafficInfoStream(byte[] aData) throws Exception {
        int[] tempData;
        int i;
        if ((aData[0] & 255) == 68 && (aData[4] & 255) == 57) {
            tempData = new int[aData.length - 19 - 4];

            for (i = 0; i < aData.length - 19 - 4; ++i) {
                tempData[i] = aData[i + 19] & 255;
            }

            return this.DWBJLZJSingleVehicleTrafficInfoStream(tempData);
        } else if ((aData[0] & 255) == 68 && (aData[4] & 255) == 65) {
            tempData = new int[aData.length];

            for (i = 0; i < aData.length; ++i) {
                tempData[i] = aData[i] & 255;
            }

            return this.DWBJLZJLawSingleVehicleTrafficInfoStream(tempData);
        } else {
            return "";
        }
    }

    public String check10TreatyDataLength(byte[] aData, StringBuffer aCheckResultID, StringBuffer aCheckResultDesc) {
        this.checkResult = "FFFF";
        this.checkResultContent = "数据正常";
        int size = aData.length;
        if (size < 8 && (aData[size - 2] & 255) != 238 || (aData[size - 1] & 255) != 238) {
            this.checkResult = "1301";
            this.checkResultContent = "数据包格式错误";
        }

        aCheckResultID.delete(0, aCheckResultID.length()).append(this.checkResult);
        aCheckResultDesc.delete(0, aCheckResultDesc.length()).append(this.checkResultContent);
        return this.checkResult;
    }

    public String checkAnswerDataVal(StringBuffer aCheckResultID, StringBuffer aCheckResultDesc) {
        this.checkResult = "FFFF";
        this.checkResultContent = "数据正常";
        if (!this.crcCheck.equals(this.shouldCRC)) {
            this.checkResult = "1201";
            this.checkResultContent = "数据内容CRC校验错误";
        }

        aCheckResultID.delete(0, aCheckResultID.length()).append(this.checkResult);
        aCheckResultDesc.delete(0, aCheckResultDesc.length()).append(this.checkResultContent);
        return this.checkResult;
    }

    public int[] get10SingleVehicle1AReplyStream() {
        int[] rData = new int[34];
        rData[0] = 170;
        rData[1] = 170;
        rData[2] = 26;
        rData[3] = 0;
        rData[4] = 26;

        for (int byteCRC = 0; byteCRC < 16; ++byteCRC) {
            rData[5 + byteCRC] = this.equipCode.charAt(byteCRC);
        }

        rData[21] = (byte) (this.year % 256);
        rData[22] = (byte) (this.year / 256);
        rData[23] = (byte) this.month;
        rData[24] = (byte) this.day;
        rData[25] = (byte) (this.singleVehicleID % 256);
        rData[26] = (byte) (this.singleVehicleID / 256);
        rData[27] = (byte) (this.singleVehicleID / 256 / 256);

        try {
            rData[28] = Integer.parseInt(this.checkResult.substring(2, 4), 16);
            rData[29] = Integer.parseInt(this.checkResult.substring(0, 2), 16);
        } catch (Exception var5) {
            rData[28] = 1;
            rData[29] = 19;
        }

        rData[30] = 0;
        rData[31] = 0;
        rData[32] = 238;
        rData[33] = 238;
        int[] var6 = new int[rData.length - 6];
        System.arraycopy(rData, 2, var6, 0, rData.length - 6);
        String tempStr = GetSmallEndCRC(var6);
        rData[30] = Integer.parseInt(tempStr.substring(0, 2), 16);
        rData[31] = Integer.parseInt(tempStr.substring(2, 4), 16);
        this.feedback = new byte[34];

        for (int i = 0; i < rData.length; ++i) {
            this.feedback[i] = (byte) rData[i];
        }

        return rData;
    }

    public int[] getFXCZFVehicleReplyStream() {
        int[] rData = new int[36];
        rData[0] = 170;
        rData[1] = 170;
        rData[2] = 26;
        rData[3] = 0;
        rData[4] = 0;
        rData[5] = 0;
        rData[6] = 17;

        for (int byteCRC = 0; byteCRC < 16; ++byteCRC) {
            rData[7 + byteCRC] = this.equipCode.charAt(byteCRC);
        }

        rData[23] = (byte) (this.year % 256);
        rData[24] = (byte) (this.year / 256);
        rData[25] = (byte) this.month;
        rData[26] = (byte) this.day;
        rData[27] = (byte) (this.singleVehicleID % 256);
        rData[28] = (byte) (this.singleVehicleID / 256);
        rData[29] = (byte) (this.singleVehicleID / 256 / 256);

        try {
            rData[30] = Integer.parseInt(this.checkResult.substring(2, 4), 16);
            rData[31] = Integer.parseInt(this.checkResult.substring(0, 2), 16);
        } catch (Exception var5) {
            rData[30] = 1;
            rData[31] = 19;
        }

        rData[32] = 0;
        rData[33] = 0;
        rData[34] = 238;
        rData[35] = 238;
        int[] var6 = new int[rData.length - 6];
        System.arraycopy(rData, 2, var6, 0, rData.length - 6);
        String tempStr = GetSmallEndCRC(var6);
        rData[32] = Integer.parseInt(tempStr.substring(0, 2), 16);
        rData[33] = Integer.parseInt(tempStr.substring(2, 4), 16);
        this.feedback = new byte[36];

        for (int i = 0; i < rData.length; ++i) {
            this.feedback[i] = (byte) rData[i];
        }

        return rData;
    }

    public int[] getImageReplyStream() {
        int[] rData = new int[37];
        rData[0] = 170;
        rData[1] = 170;
        rData[2] = 27;
        rData[3] = 0;
        rData[4] = 0;
        rData[5] = 0;
        rData[6] = 18;

        for (int byteCRC = 0; byteCRC < 16; ++byteCRC) {
            rData[7 + byteCRC] = this.equipCode.charAt(byteCRC);
        }

        rData[23] = (byte) (this.year % 256);
        rData[24] = (byte) (this.year / 256);
        rData[25] = (byte) this.month;
        rData[26] = (byte) this.day;
        rData[27] = (byte) (this.singleVehicleID % 256);
        rData[28] = (byte) (this.singleVehicleID / 256);
        rData[29] = (byte) (this.singleVehicleID / 256 / 256);
        rData[30] = (byte) this.imageType;

        try {
            rData[31] = Integer.parseInt(this.checkResult.substring(2, 4), 16);
            rData[32] = Integer.parseInt(this.checkResult.substring(0, 2), 16);
        } catch (Exception var5) {
            rData[31] = 1;
            rData[32] = 19;
        }

        rData[33] = 0;
        rData[34] = 0;
        rData[35] = 238;
        rData[36] = 238;
        int[] var6 = new int[rData.length - 6];
        System.arraycopy(rData, 2, var6, 0, rData.length - 6);
        String tempStr = GetSmallEndCRC(var6);
        rData[33] = Integer.parseInt(tempStr.substring(0, 2), 16);
        rData[34] = Integer.parseInt(tempStr.substring(2, 4), 16);
        this.feedback = new byte[37];

        for (int i = 0; i < rData.length; ++i) {
            this.feedback[i] = (byte) rData[i];
        }

        return rData;
    }

    public int[] getHDCaptureReplyStream() {
        int[] rData = new int[36];
        rData[0] = 170;
        rData[1] = 170;
        rData[2] = 26;
        rData[3] = 0;
        rData[4] = 0;
        rData[5] = 0;
        rData[6] = 19;

        for (int byteCRC = 0; byteCRC < 16; ++byteCRC) {
            rData[7 + byteCRC] = this.equipCode.charAt(byteCRC);
        }

        rData[23] = (byte) (this.year % 256);
        rData[24] = (byte) (this.year / 256);
        rData[25] = (byte) this.month;
        rData[26] = (byte) this.day;
        rData[27] = (byte) (this.singleVehicleID % 256);
        rData[28] = (byte) (this.singleVehicleID / 256);
        rData[29] = (byte) (this.singleVehicleID / 256 / 256);

        try {
            rData[30] = Integer.parseInt(this.checkResult.substring(2, 4), 16);
            rData[31] = Integer.parseInt(this.checkResult.substring(0, 2), 16);
        } catch (Exception var5) {
            rData[30] = 1;
            rData[31] = 19;
        }

        rData[32] = 0;
        rData[33] = 0;
        rData[34] = 238;
        rData[35] = 238;
        int[] var6 = new int[rData.length - 6];
        System.arraycopy(rData, 2, var6, 0, rData.length - 6);
        String tempStr = GetSmallEndCRC(var6);
        rData[32] = Integer.parseInt(tempStr.substring(0, 2), 16);
        rData[33] = Integer.parseInt(tempStr.substring(2, 4), 16);
        this.feedback = new byte[36];

        for (int i = 0; i < rData.length; ++i) {
            this.feedback[i] = (byte) rData[i];
        }

        return rData;
    }

    public String DWBJLZJSingleVehicleTrafficInfoStream(int[] aData) throws Exception {
        if (aData != null && aData.length >= 4 && aData[0] == 170 && aData[1] == 170 && (aData[2] & 255) + (aData[3] & 255) * 256 == aData.length - 8) {
            try {
                this.intTrafficData = new int[aData.length];
                this.byteTrafficData = new byte[aData.length];
                this.treatyType = 2015;
                this.trafficDataLength = aData[2] + aData[3] * 256;
                this.realTrafficDataLength = aData.length;
                this.dataType = aData[4];
                this.stationCode = "";

                int e;
                for (e = 0; e < 15; ++e) {
                    if ((char) aData[21 + e] != 0) {
                        this.stationCode = this.stationCode + (char) aData[5 + e];
                    }
                }

                this.stationCode = this.stationCode.trim();
                this.equipCode = "";

                for (e = 0; e < 16; ++e) {
                    this.equipCode = this.equipCode + (char) aData[20 + e];
                }

                this.equipError = aData[36];
                this.singleVehicleID = aData[37] + aData[38] * 256 + aData[39] * 256 * 256 + aData[40] * 256 * 256 * 256;
                this.year = aData[41] + aData[42] * 256;
                this.month = aData[43];
                this.day = aData[44];
                Calendar now = Calendar.getInstance();
                if (this.day != now.get(Calendar.DAY_OF_MONTH)) {
                    return "";
                }
                this.totalLineNum = aData[45];
                if (this.totalLineNum > 90) {
                    this.totalLineNum = 90;
                }

                this.lineCode = aData[46];
                this.hour = aData[47];
                this.minute = aData[48];
                this.second = aData[49];

                try {
                    this.passTime.set(this.year, this.month - 1, this.day, this.hour, this.minute, this.second);
                    this.passTime.set(14, 0);
                } catch (Exception var4) {
                    this.passTime.set(2000, 0, 1, 1, 0, 0);
                    this.passTime.set(14, 0);
                }

                if (this.year > 2018) {
                    return "";
                } else {
                    this.vehicleType = aData[50];
                    this.axleCount = aData[51];
                    this.totalWeight = aData[52] + aData[53] * 256 + aData[54] * 256 * 256 + aData[55] * 256 * 256 * 256;
                    this.vehicleSpeed = aData[56] + aData[57] * 256;

                    for (e = 0; e < this.axleCount; ++e) {
                        this.singleAxleWeight[e] = aData[58 + 2 * e] + aData[59 + 2 * e] * 256;
                    }

                    this.crcCheck = String.format("%02x", new Object[]{Integer.valueOf(aData[aData.length - 4] & 255)}) + String.format("%02x", new Object[]{Integer.valueOf(aData[aData.length - 3] & 255)});
                    System.arraycopy(aData, 0, this.intTrafficData, 0, aData.length);
                    int[] var6 = new int[aData.length - 6];
                    System.arraycopy(aData, 2, var6, 0, aData.length - 6);
                    this.shouldCRC = GetSmallEndCRC(var6);

                    for (int i = 0; i < aData.length; ++i) {
                        this.byteTrafficData[i] = (byte) aData[i];
                    }

                    return "";
                }
            } catch (Exception var5) {
                this.logger.error(" data deal~~~" + var5.toString());
                throw new Exception(var5.toString());
            }
        } else {
            return "error";
        }
    }

    public String DWBJLZJLawSingleVehicleTrafficInfoStream(int[] aData) throws Exception {
        if (aData != null && aData.length >= 4 && (char) aData[0] == 68 && (char) aData[1] == 70 && (aData[2] & 255) + (aData[3] & 255) * 256 == aData.length - 8) {
            try {
                this.intTrafficData = new int[aData.length];
                this.byteTrafficData = new byte[aData.length];
                this.treatyType = 2015;
                this.trafficDataLength = aData[2] + aData[3] * 256;
                this.realTrafficDataLength = aData.length;
                this.dataType = aData[4];
                this.stationCode = "";

                int e;
                for (e = 0; e < 15; ++e) {
                    if ((char) aData[27 + e] != 0) {
                        this.stationCode = this.stationCode + (char) aData[27 + e];
                    }
                }

                this.stationCode = this.stationCode.trim();
                this.equipCode = "";

                for (e = 0; e < 16; ++e) {
                    if ((char) aData[42 + e] != 0) {
                        this.equipCode = this.equipCode + (char) aData[42 + e];
                    }
                }

                this.equipError = aData[58];
                this.singleVehicleID = aData[59] + aData[60] * 256 + aData[61] * 256 * 256 + aData[62] * 256 * 256 * 256;
                this.totalLineNum = aData[63];
                if (this.totalLineNum > 90) {
                    this.totalLineNum = 90;
                }

                this.lineCode = aData[64];
                this.vehicleType = aData[65];
                this.axleCount = aData[66];
                this.length = aData[67] + aData[68] * 256 + aData[69] * 256 * 256 + aData[70] * 256 * 256 * 256;
                this.width = aData[71] + aData[72] * 256 + aData[73] * 256 * 256 + aData[74] * 256 * 256 * 256;
                this.height = aData[75] + aData[76] * 256 + aData[77] * 256 * 256 + aData[78] * 256 * 256 * 256;
                byte[] var10 = new byte[12];

                for (int bColor = 0; bColor < 12; ++bColor) {
                    var10[bColor] = (byte) aData[79 + bColor];
                }

                this.plate = (new String(var10, "gb2312")).trim();
                byte[] var11 = new byte[2];

                for (int bTemp = 0; bTemp < 2; ++bTemp) {
                    var11[bTemp] = (byte) aData[91 + bTemp];
                }

                this.color = (new String(var11, "gb2312")).trim();
                this.totalWeight = aData[97] + aData[98] * 256 + aData[99] * 256 * 256 + aData[100] * 256 * 256 * 256;
                byte[] var12 = new byte[8];

                for (int millis = 0; millis < 8; ++millis) {
                    var12[millis] = (byte) (aData[109 + millis] & 255);
                }

                long var13 = getLong(var12, 0) * 1000L - (long) TimeZone.getDefault().getRawOffset();
                this.passTime.setTimeInMillis(var13);
                this.year = this.passTime.get(1);
                this.month = this.passTime.get(2) + 1;
                this.day = this.passTime.get(5);
                Calendar now = Calendar.getInstance();
                if (this.day != now.get(Calendar.DAY_OF_MONTH)) {
                    return "";
                }
                this.hour = this.passTime.get(11);
                this.minute = this.passTime.get(12);
                this.second = this.passTime.get(13);
                if (this.year > 2018) {
                    return "";
                } else {
                    this.vehicleSpeed = aData[117] + aData[118] * 256 + aData[119] * 256 * 256 + aData[120] * 256 * 256 * 256;

                    for (int byteCRC = 0; byteCRC < this.axleCount; ++byteCRC) {
                        this.singleAxleWeight[byteCRC] = aData[125 + 4 * byteCRC] + aData[126 + 4 * byteCRC] * 256 + aData[127 + 4 * byteCRC] * 256 * 256 + aData[128 + 4 * byteCRC] * 256 * 256 * 256 + aData[157 + 4 * byteCRC] + aData[158 + 4 * byteCRC] * 256 + aData[159 + 4 * byteCRC] * 256 * 256 + aData[160 + 4 * byteCRC] * 256 * 256 * 256;
                    }

                    this.crcCheck = String.format("%02x", new Object[]{Integer.valueOf(aData[aData.length - 4] & 255)}) + String.format("%02x", new Object[]{Integer.valueOf(aData[aData.length - 3] & 255)});
                    System.arraycopy(aData, 0, this.intTrafficData, 0, aData.length);
                    int[] var14 = new int[aData.length - 8];
                    System.arraycopy(aData, 4, var14, 0, aData.length - 8);
                    this.shouldCRC = getCRC(var14);

                    for (int i = 0; i < aData.length; ++i) {
                        this.byteTrafficData[i] = (byte) aData[i];
                    }

                    return "";
                }
            } catch (Exception var9) {
                this.logger.error(" data deal~~~" + var9.toString());
                throw new Exception(var9.toString());
            }
        } else {
            return "error";
        }
    }

    public String DWFXCZFTrafficInfoStream(byte[] data) throws Exception {
        int[] aData = new int[data.length];

        int e;
        for (e = 0; e < data.length; ++e) {
            aData[e] = data[e] & 255;
        }

        if (aData != null && aData.length >= 6 && aData[0] == 170 && aData[1] == 170 && (aData[2] & 255) + (aData[3] & 255) * 256 + (aData[4] & 255) * 256 * 256 + (aData[5] & 255) * 256 * 256 * 256 == aData.length - 10) {
            try {
                this.intTrafficData = new int[aData.length];
                this.byteTrafficData = new byte[aData.length];
                this.treatyType = 2016;
                this.trafficDataLength = aData[2] + aData[3] * 256 + aData[4] * 256 * 256 + aData[5] * 256 * 256 * 256;
                this.realTrafficDataLength = aData.length;
                this.dataType = aData[6];
                this.equipCode = "";

                for (e = 0; e < 16; ++e) {
                    if ((char) aData[7 + e] != 0) {
                        this.equipCode = this.equipCode + (char) aData[7 + e];
                    }
                }

                this.equipError = aData[23];
                this.year = aData[24] + aData[25] * 256;
                this.month = aData[26];
                this.day = aData[27];
                Calendar now = Calendar.getInstance();
                if (this.day != now.get(Calendar.DAY_OF_MONTH)) {
                    return "";
                }
                this.hour = aData[28];
                this.minute = aData[29];
                this.second = aData[30];

                try {
                    this.passTime.set(this.year, this.month - 1, this.day, this.hour, this.minute, this.second);
                    this.passTime.set(14, 0);
                } catch (Exception var6) {
                    this.passTime.set(2000, 0, 1, 1, 0, 0);
                    this.passTime.set(14, 0);
                }

                if (this.year > 2018) {
                    return "";
                } else {
                    this.singleVehicleID = aData[33] + aData[34] * 256 + aData[35] * 256 * 256;
                    this.lineCode = Integer.parseInt(String.format("%02d", new Object[]{Integer.valueOf(aData[36] & 255)}));
                    this.vehicleType = aData[37];
                    this.vehicleSpeed = aData[38];
                    byte[] var8 = new byte[12];

                    int byteCRC;
                    for (byteCRC = 0; byteCRC < 12; ++byteCRC) {
                        var8[byteCRC] = (byte) aData[39 + byteCRC];
                    }

                    this.plate = (new String(var8, "gb2312")).trim();
                    this.color = String.valueOf(aData[51]);
                    this.axleCount = aData[52];
                    this.totalWeight = aData[53] + aData[54] * 256 + aData[55] * 256 * 256;

                    for (byteCRC = 0; byteCRC < 6; ++byteCRC) {
                        this.singleAxleWeight[byteCRC] = aData[56 + 2 * byteCRC] + aData[57 + 2 * byteCRC] * 256;
                    }

                    this.singleAxleWeight[6] = aData[68] + aData[69] * 256 + aData[70] * 256 * 256;
                    this.limiteWeight = aData[71] + aData[72] * 256 + aData[73] * 256 * 256;
                    this.crcCheck = String.format("%02x", new Object[]{Integer.valueOf(aData[aData.length - 4] & 255)}) + String.format("%02x", new Object[]{Integer.valueOf(aData[aData.length - 3] & 255)});
                    System.arraycopy(aData, 0, this.intTrafficData, 0, aData.length);
                    int[] var9 = new int[aData.length - 6];
                    System.arraycopy(aData, 2, var9, 0, aData.length - 6);
                    this.shouldCRC = GetSmallEndCRC(var9);

                    for (int i = 0; i < aData.length; ++i) {
                        this.byteTrafficData[i] = (byte) aData[i];
                    }

                    return "";
                }
            } catch (Exception var7) {
                this.logger.error(" data deal~~~" + var7.toString());
                throw new Exception(var7.toString());
            }
        } else {
            return "error";
        }
    }

    public String DWHDCaptureTrafficInfoStream(byte[] data) throws Exception {
        int[] aData = new int[data.length];

        int e;
        for (e = 0; e < data.length; ++e) {
            aData[e] = data[e] & 255;
        }

        if (aData != null && aData.length >= 6 && aData[0] == 170 && aData[1] == 170 && (aData[2] & 255) + (aData[3] & 255) * 256 + (aData[4] & 255) * 256 * 256 + (aData[5] & 255) * 256 * 256 * 256 == aData.length - 10) {
            try {
                this.intTrafficData = new int[aData.length];
                this.byteTrafficData = new byte[aData.length];
                this.treatyType = 2016;
                this.trafficDataLength = aData[2] + aData[3] * 256 + aData[4] * 256 * 256 + aData[5] * 256 * 256 * 256;
                this.realTrafficDataLength = aData.length;
                this.dataType = aData[6];
                this.stationCode = "";

                for (e = 0; e < 16; ++e) {
                    this.equipCode = this.equipCode + (char) aData[7 + e];
                }

                this.equipError = aData[23];
                this.year = aData[24] + aData[25] * 256;
                this.month = aData[26];
                this.day = aData[27];
                Calendar now = Calendar.getInstance();
                if (this.day != now.get(Calendar.DAY_OF_MONTH)) {
                    return "";
                }
                this.hour = aData[28];
                this.minute = aData[29];
                this.second = aData[30];

                try {
                    this.passTime.set(this.year, this.month - 1, this.day, this.hour, this.minute, this.second);
                    this.passTime.set(14, 0);
                } catch (Exception var7) {
                    this.passTime.set(2000, 0, 1, 1, 0, 0);
                    this.passTime.set(14, 0);
                }

                if (this.year > 2018) {
                    return "";
                } else {
                    this.singleVehicleID = aData[33] + aData[34] * 256 + aData[35] * 256 * 256;
                    this.lineCode = Integer.parseInt(String.format("%02d", new Object[]{Integer.valueOf(aData[36] & 255)}));
                    this.vehicleSpeed = aData[37];
                    byte[] var9 = new byte[12];

                    for (int vc = 0; vc < 12; ++vc) {
                        var9[vc] = (byte) aData[38 + vc];
                    }

                    this.plate = (new String(var9, "gb2312")).trim();
                    this.color = String.valueOf(aData[50]);
                    byte[] var10 = new byte[]{(byte) aData[51]};
                    this.vehicleColor = (new String(var10, "gb2312")).trim();
                    this.crcCheck = String.format("%02x", new Object[]{Integer.valueOf(aData[aData.length - 4] & 255)}) + String.format("%02x", new Object[]{Integer.valueOf(aData[aData.length - 3] & 255)});
                    System.arraycopy(aData, 0, this.intTrafficData, 0, aData.length);
                    int[] byteCRC = new int[aData.length - 6];
                    System.arraycopy(aData, 2, byteCRC, 0, aData.length - 6);
                    this.shouldCRC = GetSmallEndCRC(byteCRC);

                    for (int i = 0; i < aData.length; ++i) {
                        this.byteTrafficData[i] = (byte) aData[i];
                    }

                    return "";
                }
            } catch (Exception var8) {
                this.logger.error(" data deal~~~" + var8.toString());
                throw new Exception(var8.toString());
            }
        } else {
            return "error";
        }
    }

    public String DWHDCaptureTrafficFromFile(String path, String fileName) throws Exception {
        String[] str = fileName.split("_");
        if (str.length >= 5) {
            try {
                this.equipCode = str[2];
                String e = str[1];
                this.year = Integer.parseInt(e.substring(0, 4));
                this.month = Integer.parseInt(e.substring(4, 6));
                this.day = Integer.parseInt(e.substring(6, 8));
                Calendar now = Calendar.getInstance();
                if (this.day != now.get(Calendar.DAY_OF_MONTH)) {
                    return "";
                }
                this.hour = Integer.parseInt(e.substring(8, 10));
                this.minute = Integer.parseInt(e.substring(10, 12));
                this.second = Integer.parseInt(e.substring(12, 14));
                this.millisecond = Integer.parseInt(e.substring(14, 17));
                try {
                    this.passTime.set(this.year, this.month - 1, this.day, this.hour, this.minute, this.second);
                    this.passTime.set(14, this.millisecond);
                } catch (Exception var9) {
                    this.passTime.set(2000, 0, 1, 1, 0, 0);
                    this.passTime.set(14, 0);
                }

                if (this.year > 2018) {
                    return "";
                } else {
                    this.plate = str[3];
                    this.color = str[4];
                    if (this.plate.length() == 7 && this.color.substring(0, 1).equals("黄")) {
                        int nLen = 512000;
                        FileInputStream in = new FileInputStream(path + "\\" + fileName);
                        byte[] buffer = new byte[nLen];

                        int c;
                        while ((c = in.read(buffer)) != -1) {
                            if (c == nLen) {
                                this.logger.error(" 文件过大~~~" + c);
                                break;
                            }

                            this.image = new byte[c];
                            System.arraycopy(buffer, 0, this.image, 0, c);
                        }

                        in.close();
                    } else {
                        this.image = new byte[0];
                        logger.info("设置traffic image" + this.image.length);
                    }

                    return "";
                }
            } catch (Exception var10) {
                this.logger.error(" data deal~~~" + var10.toString());
                throw new Exception(var10.toString());
            }
        } else {
            return "";
        }
    }

    public String DWImageTrafficInfoStream(byte[] data) throws Exception {
        int[] aData = new int[data.length];

        int e;
        for (e = 0; e < data.length; ++e) {
            aData[e] = data[e] & 255;
        }

        if (aData != null && aData.length >= 6 && aData[0] == 170 && aData[1] == 170 && (aData[2] & 255) + (aData[3] & 255) * 256 + (aData[4] & 255) * 256 * 256 + (aData[5] & 255) * 256 * 256 * 256 == aData.length - 10) {
            try {
                this.intTrafficData = new int[aData.length];
                this.byteTrafficData = new byte[aData.length];
                this.treatyType = 2016;
                this.trafficDataLength = aData[2] + aData[3] * 256 + aData[4] * 256 * 256 + aData[5] * 256 * 256 * 256;
                this.realTrafficDataLength = aData.length;
                this.dataType = aData[6];
                this.equipCode = "";

                for (e = 0; e < 16; ++e) {
                    this.equipCode = this.equipCode + (char) aData[7 + e];
                }

                this.equipError = aData[23];
                this.year = aData[24] + aData[25] * 256;
                this.month = aData[26];
                this.day = aData[27];
                Calendar now = Calendar.getInstance();
                if (this.day != now.get(Calendar.DAY_OF_MONTH)) {
                    return "";
                }
                this.hour = aData[28];
                this.minute = aData[29];
                this.second = aData[30];
                if (this.year > 2018) {
                    return "";
                } else {
                    try {
                        this.passTime.set(this.year, this.month - 1, this.day, this.hour, this.minute, this.second);
                        this.passTime.set(14, 0);
                    } catch (Exception var5) {
                        this.passTime.set(2000, 0, 1, 1, 0, 0);
                        this.passTime.set(14, 0);
                    }

                    this.singleVehicleID = aData[33] + aData[34] * 256 + aData[35] * 256 * 256;
                    this.lineCode = aData[36];
                    this.imageType = aData[37];
                    this.image = new byte[aData.length - 50];
                    System.arraycopy(data, 42, this.image, 0, aData.length - 50);
                    this.crcCheck = String.format("%02x", new Object[]{Integer.valueOf(aData[aData.length - 4] & 255)}) + String.format("%02x", new Object[]{Integer.valueOf(aData[aData.length - 3] & 255)});
                    System.arraycopy(aData, 0, this.intTrafficData, 0, aData.length);
                    int[] var7 = new int[aData.length - 6];
                    System.arraycopy(aData, 2, var7, 0, aData.length - 6);
                    this.shouldCRC = GetSmallEndCRC(var7);

                    for (int i = 0; i < aData.length; ++i) {
                        this.byteTrafficData[i] = (byte) aData[i];
                    }

                    return "";
                }
            } catch (Exception var6) {
                var6.printStackTrace();
                this.logger.error(" data deal~~~" + var6.toString());
                throw new Exception(var6.toString());
            }
        } else {
            return "error";
        }
    }

    public static long getLong(byte[] bb, int index) {
        return ((long) bb[index + 7] & 255L) << 56 | ((long) bb[index + 6] & 255L) << 48 | ((long) bb[index + 5] & 255L) << 40 | ((long) bb[index + 4] & 255L) << 32 | ((long) bb[index + 3] & 255L) << 24 | ((long) bb[index + 2] & 255L) << 16 | ((long) bb[index + 1] & 255L) << 8 | ((long) bb[index + 0] & 255L) << 0;
    }

    public String getStrOfByte(byte[] aData) {
        String str = "";

        for (int i = 0; i < aData.length; ++i) {
            str = str + String.format("%02x", new Object[]{Integer.valueOf(aData[i] & 255)});
        }

        return str;
    }

    public byte[] getBytesOfStr(String aStr) {
        byte[] b = new byte[aStr.length() / 2];

        for (int i = 0; i < aStr.length() / 2; ++i) {
            b[i] = (byte) Integer.parseInt(aStr.substring(i * 2, i * 2 + 2), 16);
        }

        return b;
    }

    public byte[] getByteOfInt(int[] aData) {
        byte[] b = new byte[aData.length];

        for (int i = 0; i < aData.length; ++i) {
            b[i] = (byte) aData[i];
        }

        return b;
    }

    public String getCharOfByte(byte[] aData) {
        String str = "";

        for (int i = 0; i < aData.length; ++i) {
            str = str + (char) aData[i];
        }

        return str;
    }

    public static String getCRC(int[] aIntTrafficData) {
        short crc16 = 0;

        for (int i = 0; i < aIntTrafficData.length; ++i) {
            try {
                short ex = (short) (aIntTrafficData[i] & '\uffff');
                crc16 = (short) (crctab[crc16 >> 8 & 255 ^ ex & '\uffff'] ^ crc16 << 8);
            } catch (Exception var4) {
                return "0000";
            }
        }

        return String.format("%04x", new Object[]{Integer.valueOf(crc16 & '\uffff')});
    }

    public static String GetSmallEndCRC(int[] aIntTrafficData) {
        byte uchCRCHi = -1;
        byte uchCRCLo = -1;
        int l = aIntTrafficData.length;

        for (int i = 0; i < l; ++i) {
            byte uIndex = (byte) (uchCRCLo ^ (byte) (aIntTrafficData[i] & 255));
            uchCRCLo = (byte) (uchCRCHi ^ (byte) (auchCRCHi[uIndex & 255] & 255));
            uchCRCHi = (byte) (auchCRCLo[uIndex & 255] & 255);
        }

        String tempStr = String.format("%04x", new Object[]{Integer.valueOf(uchCRCHi << 8 & '\uffff' | uchCRCLo & 255)});
        return tempStr.substring(2, 4) + tempStr.substring(0, 2);
    }

    public static String GetSmallEndCRC(byte[] aIntTrafficData) {
        byte uchCRCHi = -1;
        byte uchCRCLo = -1;
        int l = aIntTrafficData.length;

        for (int i = 0; i < l; ++i) {
            byte uIndex = (byte) (uchCRCLo ^ (byte) (aIntTrafficData[i] & 255));
            uchCRCLo = (byte) (uchCRCHi ^ (byte) (auchCRCHi[uIndex & 255] & 255));
            uchCRCHi = (byte) (auchCRCLo[uIndex & 255] & 255);
        }

        String tempStr = String.format("%04x", new Object[]{Integer.valueOf(uchCRCHi << 8 & '\uffff' | uchCRCLo & 255)});
        return tempStr.substring(2, 4) + tempStr.substring(0, 2);
    }

    public int[] CreatHDCaptureStream(String aEquipCode, Calendar aCalendar, int aDataSequence, boolean aIsRandom) {
        byte dataLength = 62;
        int[] data = new int[dataLength];
        data[0] = 170;
        data[1] = 170;
        data[dataLength - 1] = 238;
        data[dataLength - 2] = 238;

        try {
            data[2] = (dataLength - 10) % 256;
            data[3] = (dataLength - 10) / 256;
            data[4] = (dataLength - 10) / 256 / 256;
            data[5] = (dataLength - 10) / 256 / 256 / 256;
            data[6] = 19;

            for (int ex = 0; ex < 16; ++ex) {
                if (aEquipCode.length() > ex) {
                    data[7 + ex] = aEquipCode.charAt(ex);
                }
            }

            data[23] = 0;
            data[24] = aCalendar.get(1) % 256;
            data[25] = aCalendar.get(1) / 256;
            data[26] = aCalendar.get(2) + 1;
            data[27] = aCalendar.get(5);
            data[28] = 1;
            data[29] = 2;
            data[30] = 3;
            data[33] = aDataSequence % 256;
            data[34] = aDataSequence / 256;
            data[35] = aDataSequence / 256 / 256;
            data[36] = 12;
            data[37] = 98;
            byte[] var10 = "津A12345".getBytes();

            for (int byteCRC = 0; byteCRC < 16; ++byteCRC) {
                if (var10.length > byteCRC) {
                    data[38 + byteCRC] = var10[byteCRC] & 255;
                }
            }

            data[50] = 1;
            data[51] = 65;
            int[] var11 = new int[data.length - 6];
            System.arraycopy(data, 2, var11, 0, data.length - 6);
            this.shouldCRC = GetSmallEndCRC(var11);
            data[data.length - 4] = Integer.parseInt(this.shouldCRC.substring(0, 2), 16);
            data[data.length - 3] = Integer.parseInt(this.shouldCRC.substring(2, 4), 16);
            return data;
        } catch (Exception var9) {
            return data;
        }
    }

    public int[] CreatFXCZFStream(String aEquipCode, Calendar aCalendar, int aDataSequence, boolean aIsRandom) {
        byte dataLength = 87;
        int[] data = new int[dataLength];
        data[0] = 170;
        data[1] = 170;
        data[dataLength - 1] = 238;
        data[dataLength - 2] = 238;

        try {
            data[2] = (dataLength - 10) % 256;
            data[3] = (dataLength - 10) / 256;
            data[4] = (dataLength - 10) / 256 / 256;
            data[5] = (dataLength - 10) / 256 / 256 / 256;
            data[6] = 17;

            for (int ex = 0; ex < 16; ++ex) {
                if (aEquipCode.length() > ex) {
                    data[7 + ex] = aEquipCode.charAt(ex);
                }
            }

            data[23] = 0;
            data[24] = aCalendar.get(1) % 256;
            data[25] = aCalendar.get(1) / 256;
            data[26] = aCalendar.get(2) + 1;
            data[27] = aCalendar.get(5);
            data[28] = 1;
            data[29] = 2;
            data[30] = 3;
            data[31] = 0;
            data[32] = 0;
            data[33] = aDataSequence % 256;
            data[34] = aDataSequence / 256;
            data[35] = aDataSequence / 256 / 256;
            data[36] = 12;
            data[37] = 6;
            data[38] = 78;
            byte[] var10 = "津A12345".getBytes();

            for (int byteCRC = 0; byteCRC < 16; ++byteCRC) {
                if (var10.length > byteCRC) {
                    data[39 + byteCRC] = var10[byteCRC] & 255;
                }
            }

            data[51] = 1;
            data[52] = 6;
            data[53] = 168;
            data[54] = 347;
            data[55] = 1;
            data[56] = 248;
            data[57] = 42;
            data[58] = 224;
            data[59] = 46;
            data[60] = 200;
            data[61] = 50;
            data[62] = 176;
            data[63] = 54;
            data[64] = 152;
            data[65] = 58;
            data[66] = 128;
            data[67] = 62;
            data[68] = 104;
            data[69] = 66;
            data[70] = 0;
            data[71] = 104;
            data[72] = 191;
            data[73] = 0;
            int[] var11 = new int[data.length - 6];
            System.arraycopy(data, 2, var11, 0, data.length - 6);
            this.shouldCRC = GetSmallEndCRC(var11);
            data[data.length - 4] = Integer.parseInt(this.shouldCRC.substring(0, 2), 16);
            data[data.length - 3] = Integer.parseInt(this.shouldCRC.substring(2, 4), 16);
            return data;
        } catch (Exception var9) {
            return data;
        }
    }

    public byte[] readFile(String aEquipCode, Calendar aCalendar, int aDataSequence) {
        byte[] bFile = new byte[5242880];
        FileInputStream in = null;

        try {
            in = new FileInputStream("d:/收费管理.jpg");
        } catch (FileNotFoundException var10) {
            var10.printStackTrace();
        }

        int leng = 0;

        byte[] data;
        try {
            leng = in.read(bFile);
            data = new byte[leng];
            System.arraycopy(bFile, 0, data, 0, leng);
            this.shouldCRC = GetSmallEndCRC(data);
        } catch (IOException var9) {
            var9.printStackTrace();
        }

        data = new byte[leng + 50];
        data[0] = -86;
        data[1] = -86;
        data[leng + 50 - 1] = -18;
        data[leng + 50 - 2] = -18;
        data[2] = (byte) ((leng + 40) % 256);
        data[3] = (byte) ((leng + 40) / 256);
        data[4] = (byte) ((leng + 40) / 256 / 256);
        data[5] = (byte) ((leng + 40) / 256 / 256 / 256);
        data[6] = 18;

        for (int byteCRC = 0; byteCRC < 16; ++byteCRC) {
            if (aEquipCode.length() > byteCRC) {
                data[7 + byteCRC] = (byte) aEquipCode.charAt(byteCRC);
            }
        }
        data[24] = (byte) (aCalendar.get(1) % 256);
        data[25] = (byte) (aCalendar.get(1) / 256);
        data[26] = (byte) (aCalendar.get(2) + 1);
        data[27] = (byte) (aCalendar.get(5));
        data[23] = 0;
//        data[24] = -30;
//        data[25] = 7;
//        data[26] = 7;
//        data[27] = 27 ;
        data[28] = 22;
        data[29] = 37;
        data[30] = 17;
        data[31] = -36;
        data[32] = 2;
        data[33] = 13;
        data[34] = 0;
        data[35] = 0;
        data[36] = 11;
        data[37] = 1;
        data[38] = 44;
        data[39] = 12;
        data[40] = 1;
        data[41] = 0;
        System.arraycopy(bFile, 0, data, 42, leng);
        byte[] var11 = new byte[data.length - 6];
        System.arraycopy(data, 2, var11, 0, data.length - 6);
        this.shouldCRC = GetSmallEndCRC(var11);
        data[data.length - 4] = (byte) Integer.parseInt(this.shouldCRC.substring(0, 2), 16);
        data[data.length - 3] = (byte) Integer.parseInt(this.shouldCRC.substring(2, 4), 16);
        return data;
    }
}
