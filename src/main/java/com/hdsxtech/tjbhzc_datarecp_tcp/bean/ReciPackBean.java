package com.hdsxtech.tjbhzc_datarecp_tcp.bean;


import io.netty.channel.ChannelHandlerContext;


/**
 *
 * 封装hanler类
 *
 */
public class ReciPackBean {
    private byte[] msgbytes;
    private ChannelHandlerContext channel;

    public ChannelHandlerContext getChannel() {
        return channel;
    }

    public void setChannel(ChannelHandlerContext channel) {
        this.channel = channel;
    }

    public byte[] getMsgbytes() {
        return msgbytes;
    }

    public void setMsgbytes(byte[] msgbytes) {
        this.msgbytes = msgbytes;
    }

}
