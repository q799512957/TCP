package com.hdsxtech.tjbhzc_datarecp_tcp.factory;


import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * 数据类型转换
 *
 * @author wangxiri
 */
public class DataTypeUtil {

    /**
     * 无符号byte转int
     *
     * @param b
     * @return
     */
    public static int Byte2Uint8(byte b) {
        int n = b & 0xff;
        return n;
    }

    /**
     * bytes转short，有符号
     *
     * @param b
     * @return
     */
    public static short Bytes2Short(byte[] b) {
        ByteBuffer buffer = ByteBuffer.wrap(b);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        return buffer.getShort();
    }

    /**
     * bytes转无符号short
     *
     * @param b
     * @return
     */
    public static int Bytes2ushort(byte[] b) {
        short s = Bytes2Short(b);
        int i = s & 0xffff;
        return i;
    }

    /**
     * bytes转int，有符号
     *
     * @param b
     * @return
     */
    public static int Bytes2int32(byte[] b) {
        ByteBuffer buffer = ByteBuffer.wrap(b);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        return buffer.getInt();
    }

    /**
     * bytes转uint32
     *
     * @param b
     * @return
     */
    public static long byte2unsignInt32(byte[] b) {
        ByteBuffer buffer = ByteBuffer.wrap(b);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        int i = buffer.getInt();
        long n = i & 0xffffffffl;
        return n;
    }

    /**
     * bytes转BCD编码
     */
    public static String bytes2BCD(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (byte v : b) {
            sb.append(byte2BCD(v));
        }
        return sb.toString();
    }

    /**
     * bytes转BCD编码
     *
     * @param b
     * @param len
     * @return
     */
    public static String bytes2BCD(byte[] b, int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(byte2BCD(b[i]));
        }
        return sb.toString();
    }

    /**
     * byte转BCD
     *
     * @param b
     * @return
     */
    public static String byte2BCD(byte b) {
        String t = Integer.toHexString(b & 0xff);
        if (t.length() == 1) {
            t = "0" + t;
        }
        return t.toUpperCase();
    }

    /**
     * byte[] 转String
     *
     * @param b
     * @return
     */
    public static String bytes2GBKString(byte[] b, int len) {
        return bytes2GBKString(Arrays.copyOfRange(b, 0, len));
    }

    /**
     * byte[] 转String
     *
     * @param b
     * @return
     */
    public static String bytes2GBKString(byte[] b) {

        String s = null;
        try {
            s = new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s.trim();
    }

    /**
     * 字符转BCD码
     *
     * @param str
     * @param len
     * @return
     */

    public static byte[] str2BCD(String str, int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append("00");
        }
        sb.append(str);
        String str1 = sb.substring(sb.length() - len * 2);

        byte[] bcd = str2BCD(str1);
        return bcd;
    }

    /**
     * 字符转BCD码
     *
     * @param str
     * @return
     */
    public static byte[] str2BCD(String str) {
        if (str == null)
            throw new IllegalArgumentException("输入转换参数为null!");
        int strLen = str.length();
        if (strLen % 2 != 0) {
            throw new IllegalArgumentException("参数不合法,长度必须为2的倍数!");
        }
        int len = strLen / 2;
        byte[] bcd = new byte[len];
        int offset = 0;
        for (int i = 0; i < len; i++) {
            String sub = str.substring(offset, offset + 2);
            bcd[i] = (byte) (Integer.parseInt(sub, 16) & 0xff);
            offset += 2;
        }

        return bcd;
    }

    /**
     * BCD码转String
     *
     * @param bytes
     * @return
     */
    public static String BCD2Str(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            String s = Integer.toHexString(b);
            if (s.length() > 2)
                s = s.substring(s.length() - 2);
            s = s.length() == 1 ? "0" + s : s;
            sb.append(s);
        }
        return sb.toString().toUpperCase();
    }
    /**
     * bytes码转doubke
     *
     * @return
     */
    public static double bytes2Double(byte[] arr) {
//      long value = 0;
//      for (int i = 0; i < 8; i++) {
//          value |= ((long) (arr[i] & 0xff)) << (8 * i);
//      }
//      return Double.longBitsToDouble(value);
//    	byte[] b = new byte[]{arr[0],arr[1],arr[2],arr[3],arr[4],arr[5],arr[6],arr[7]};
      if (arr == null) {
          throw new IllegalArgumentException("byte数组必须不为空,并且是8位!");
      }
      long l = byte8ToLong(arr);
      return Double.longBitsToDouble(l);
  }
    
    public static long byte8ToLong(byte[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("byte数组必须不为空,并且是8位!");
        }
        return (long) (((long) (arr[0] & 0xff) << 56) | ((long) (arr[1] & 0xff) << 48) | ((long) (arr[2] & 0xff) << 40)
                | ((long) (arr[3] & 0xff) << 32) | ((long) (arr[4] & 0xff) << 24)
                | ((long) (arr[5] & 0xff) << 16) | ((long) (arr[6] & 0xff) << 8) | ((long) (arr[7] & 0xff)));
    }


    public static byte[] double2Bytes(double d) {
        long value = Double.doubleToRawLongBits(d);
        byte[] byteRet = new byte[8];
        for (int i = 0; i < 8; i++) {
            byteRet[i] = (byte) ((value >> 8 * i) & 0xff);
        }
        return byteRet;
    }

    public static byte[] intToBytes(int value)
    {
        byte[] src = new byte[4];
        src[0] = (byte) ((value>>24) & 0xFF);
        src[1] = (byte) ((value>>16)& 0xFF);
        src[2] = (byte) ((value>>8)&0xFF);
        src[3] = (byte) (value & 0xFF);
        return src;
    }

    public static byte[] LongToBytes(long values) {
        byte[] buffer = new byte[8];
        for (int i = 0; i < 8; i++) {
            int offset = 64 - (i + 1) * 8;
            buffer[i] = (byte) ((values >> offset) & 0xff);
        }
        return buffer;
    }

    public static byte[] floatToByte(float f) {
        int intbits = Float.floatToIntBits(f);//将float里面的二进制串解释为int整数
        return intToBytes(intbits);
    }

}
