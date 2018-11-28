package com.hdsxtech.tjbhzc_datarecp_tcp.msg;

import com.hdsxtech.tjbhzc_datarecp_tcp.factory.MsgBean;
import io.netty.channel.ChannelHandlerContext;

public interface IHandler {

    void doHandle(byte[] m, ChannelHandlerContext ctx);

}
