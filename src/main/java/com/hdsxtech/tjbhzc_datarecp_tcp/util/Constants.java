package com.hdsxtech.tjbhzc_datarecp_tcp.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Constants {
    private static final Logger logger = LoggerFactory.getLogger(Constants.class);
    private static int msg_sn = 0;
    private static long msg_sn_rsp = 0l;

    public static char [] ALPHABET = new char[]{'1','2','3','4','5','6','7','8','9',
            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

    public static List<String> EQUIPT_IDS= null;

    public static void bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() == 1) {
                hv = '0' + hv;
            }
            stringBuilder.append("" + hv);
        }
    }

    public static String byteToBit(byte b) {
        return ""
                + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)
                + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)
                + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)
                + (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);
    }

    public static int getMsg_sn() {
        if (msg_sn >= Integer.MAX_VALUE) {
            msg_sn = 0;
        } else {
            msg_sn++;
        }
        return msg_sn;
    }


    public static String getCheckNo() {

        String distCode = PropertiesUtil.getProperties().getProperty("distCode");

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);// 获取年份
        int month=cal.get(Calendar.MONTH);// 获取月份
        int day=cal.get(Calendar.DATE);// 获取日
        int hour=cal.get(Calendar.HOUR);// 小时
        int minute=cal.get(Calendar.MINUTE);// 分
        int second=cal.get(Calendar.SECOND);// 秒
        int millisecond = cal.get(Calendar.MILLISECOND); // 毫秒

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String randomcode = "";
        for( int i =0 ; i < 6 ; i ++ ) {
           randomcode += ALPHABET[(int)Math.floor(35 * Math.random())];
        }
        return distCode +  sdf.format(new Date())+millisecond + "000" +randomcode;
    }


    public static void main(String[] args) {
       // System.out.println(getCheckNo());
        Calendar calendar = Calendar.getInstance();
      //  calendar.clear();
       // calendar.set(2018,0,1);
        long millis = calendar.getTimeInMillis();
        System.out.println(System.currentTimeMillis());
    }

}
