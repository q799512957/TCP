package com.hdsxtech.tjbhzc_datarecp_tcp.bean;


import io.netty.channel.ChannelHandlerContext;

public class ClientMsg {
    private long msg_sn_o;// 原流水号
    private long msg_sn;// 监控网关生成流水号
    private ChannelHandlerContext channel;// 流水号对应的客户端
    private long sendTime;//消息发送时间

    public ChannelHandlerContext getChannel() {
        return channel;
    }

    public void setChannel(ChannelHandlerContext channel) {
        this.channel = channel;
    }

    public long getMsg_sn_o() {
        return msg_sn_o;
    }

    public void setMsg_sn_o(long msg_sn_o) {
        this.msg_sn_o = msg_sn_o;
    }

    public long getMsg_sn() {
        return msg_sn;
    }

    public void setMsg_sn(long msg_sn) {
        this.msg_sn = msg_sn;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }


}
