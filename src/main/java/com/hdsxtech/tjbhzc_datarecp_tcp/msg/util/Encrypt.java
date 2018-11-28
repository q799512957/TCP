package com.hdsxtech.tjbhzc_datarecp_tcp.msg.util;

import java.util.Random;

/**
 * 消息体加密
 *
 * @author cuipengfei
 */
public class Encrypt {
    /**
     * 加密解密
     *
     * @param key
     * @param buffer
     * @param size
     */
    public static byte[] encryptUtil(long key, byte[] buffer, long size,
                                     long M1, long IA1, long IC1) {
        int idx = 0;
        if (0 == key) {
            key = 1;
        }
        long mkey = M1;
        if (0 == mkey) {
            mkey = 1;
        }
        while (idx < size) {
            key = IA1 * (key % mkey) + IC1;
            buffer[idx++] ^= ((key >> 20) & 0xFF);
        }
        return buffer;
    }

    private static Random ran;

    /**
     * 32位伪随机数产生器
     *
     * @return
     */
    public static long getRandom32long() {
        if (ran == null) {
            ran = new Random();
        }
        return ran.nextLong() & 0xFFFFFFFFL;

    }

}
