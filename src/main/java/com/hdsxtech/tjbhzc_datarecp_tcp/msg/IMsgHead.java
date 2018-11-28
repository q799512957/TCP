package com.hdsxtech.tjbhzc_datarecp_tcp.msg;

/**
 * 消息头接口
 *
 */
public interface IMsgHead {
    /**
     * 消息转换为字节数组
     *
     * @return
     */
    byte[] tobytes();

    /**
     * 消息长度
     *
     * @return
     */
    int getHeadLen();

    /**
     * 字节数组转换为消息
     *
     * @param b
     * @return
     */
    boolean frombytes(byte[] b);
}
