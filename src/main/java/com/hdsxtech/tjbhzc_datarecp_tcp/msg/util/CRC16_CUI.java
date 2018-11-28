package com.hdsxtech.tjbhzc_datarecp_tcp.msg.util;


/**
 * CRC16校验码工具
 *
 */
public class CRC16_CUI {

    /**
     * 获取字节数组的CRC16校验码
     *
     * @param bytes
     * @return
     */
    public static byte[] getBigCRC16(byte[] bytes) {
        int crc = 0xFFFF; // initial value
        int polynomial = 0xA001; // 1010 0000 0000 0001
        for (int j = 0; j < bytes.length; j++) {
            crc ^= bytes[j];
            for (int i = 0; i < 8; i++) {
                boolean c15 = ((crc & 0x1) == 1);
                crc >>= 1;
                if (c15)
                    crc ^= polynomial;
            }
        }
        byte[] b = new byte[2];
        b[0] = (byte) (crc & 0xFF);
        b[1] = (byte) (crc >> 8);
        return b;

    }

    /**
     * 获取字节数组的CRCCRC16-CCITT校验码方法
     *
     * @param bytes
     * @return
     */
    public static byte[] getCRCCRC16_CCITT(byte[] bytes) {

        int crc = 0xFFFF; // initial value
        int polynomial = 0x1021; // 0001 0000 0010 0001 (0, 5, 12)
        for (byte b : bytes) {
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b >> (7 - i) & 1) == 1);
                boolean c15 = ((crc >> 15 & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit)
                    crc ^= polynomial;
            }
        }
        crc &= 0xffff;
        return Converter.unSigned16IntToBigBytes(crc);
    }
}
