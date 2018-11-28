package com.hdsxtech.tjbhzc_datarecp_tcp.factory;



public class MessageID {

    /**
     * 心跳
     */
    public static final int MESSAGE_HEARTBEAT_ID         = 0x01;

    /**
     * 心跳应答
     */
    public static final int MESSAGE_HEARTBEAT_ANSWER_ID         = 0x02;

    /**  称重数据ID **/
    public static final int MESSAGE_CHECKWEIGHT_ID         = 0x11;

    /** 称重数据应答的ID **/
    public static final int MESSAGE_CHECKWEIGHT_RESPONSE_ID         = 0x10;

    /**
     * 称重图片数据
     */
    public static final int MESSAGE_CHECKIMG_ID         = 0x12;

    /**
     * 称重图片数据 回传
     */
    public static final int MESSAGE_CHECKIMG_RESPONSE_ID  = 0x13;

}
