package com.hdsxtech.tjbhzc_datarecp_tcp.server;

import com.hdsxtech.tjbhzc_datarecp_tcp.bean.ReciPackBean;
import com.hdsxtech.tjbhzc_datarecp_tcp.util.Constants;
import com.hdsxtech.tjbhzc_datarecp_tcp.util.MsgQueue;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Arrays;

/**
 * tcp handler
 */
public class TcpServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(TcpServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
       /* ByteBuf in = (ByteBuf) msg;
        try {
            System.out.println("start ----------------------------------------------------------------------------");
            while (in.isReadable()) { // (1)
                System.out.print(" ");
                System.out.print(in.readByte());
                System.out.flush();
            }
            System.out.println();
            System.out.println("end ----------------------------------------------------------------------------");
        } finally {
            ReferenceCountUtil.release(msg); // (2)
        }
        // 或者直接打印
        System.out.println("Yes, A new client in = " + ctx.name());*/
       try {
            if (msg instanceof byte[]) {
                final byte[] msgbytes = (byte[]) msg;
                //System.out.println(Arrays.toString(msgbytes));
                ReciPackBean rpb = new ReciPackBean();
                rpb.setChannel(ctx);
                rpb.setMsgbytes(msgbytes);
                try {
                    logger.debug("收到客户端消息");
                    Constants.bytesToHexString(msgbytes);
                    MsgQueue.getRecqueue().put(rpb);
                } catch (Exception e) {
                    logger.error("主handler---接收消息队列存储消息失败", e);
                }
            } else {
                logger.error("主handler---消息解码有误，请检查！！");
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("-------------临时客户端建立连接--------------");
        ClientManager.addTemClient(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
        InetAddress inetAdd = address.getAddress();
        logger.info("客户端断开连接：" + ctx.name() + ",IP:" + inetAdd.getHostAddress()
                + ",port:" + address.getPort());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        logger.error("主handle---rexceptionCaught异常", cause);
        ctx.close();
        ctx = null;
    }
}
