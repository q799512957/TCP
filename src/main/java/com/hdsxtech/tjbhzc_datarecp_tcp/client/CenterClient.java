package com.hdsxtech.tjbhzc_datarecp_tcp.client;

import com.hdsxtech.tjbhzc_datarecp_tcp.factory.MsgBean;
import com.hdsxtech.tjbhzc_datarecp_tcp.util.Constants;
import com.hdsxtech.tjbhzc_datarecp_tcp.util.PropertiesUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 链接中心客户端
 *
 * @author sid
 */
public class CenterClient extends Thread {
    private volatile static CenterClient obj;
    private static final Logger logger = LoggerFactory.getLogger(CenterClient.class);

    public static ConcurrentHashMap<String, Integer> devmap = new ConcurrentHashMap();
    public static boolean isLogined = false; // 是否登陆成功
    public static boolean isStoped = false;  // 是否已经停止
    public ChannelHandlerContext chtx;
    public ChannelFuture cf;
    private volatile static EventLoopGroup group = null;
    private volatile static Bootstrap b = null;

    private int reconnectdealy = Integer.parseInt(PropertiesUtil
            .getProperties().getProperty("reconnectdealy"));
    private int heartbeatdelay = Integer.parseInt(PropertiesUtil
            .getProperties().getProperty("tcp.heartbeatdelay"));// 心跳间隔

    public volatile static int connstate = 0;

    public static CenterClient getInstance() {
            if (obj == null) {
            synchronized (CenterClient.class) {
                if (obj == null) {
                    obj = new CenterClient();
                }
            }
            obj = new CenterClient();
        }
        return obj;
    }

    private CenterClient() {}

    public void reconnect() {
        logger.info("监控连接后台服务：断线重连线程，当前状态：" + (connstate == 1 ? "连接中" : "已断线"));
        if (connstate != 1) {
            try {
                if (!this.isStoped) {
                    this.stopClient();
                }
                init();
//                ReCenterConnectedThread.getInstance().run(this.reconnectdealy * 1000,
//                        this.reconnectdealy * 1000);
            } catch (Exception e) {
                logger.error("CenterClient断线重连初始化失败：", e);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        try {
            init();
//            ReCenterConnectedThread.getInstance().run(this.reconnectdealy * 1000,
//                    this.reconnectdealy * 1000);
        } catch (Exception e) {
            logger.error("CenterClient初始化失败：", e);
            e.printStackTrace();
        }
    }

    private void init() {
        try {
            Properties p = PropertiesUtil.getProperties();
            if (b == null || group == null) {
                group = new NioEventLoopGroup();
                b = new Bootstrap();
                final LogLevel loglevel = LogLevel.valueOf(p.getProperty("loglevel").toUpperCase());
                b.group(group).channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch)
                                    throws Exception {
                                ch.pipeline().addLast(
                                        new CenterCodec(),
                                        new LoggingHandler(loglevel),
                                        new CenterClientHandler());
                            }
                        });

                b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);
            }
            cf = b.connect(p.getProperty("center.hostname"),
                    Integer.parseInt(p.getProperty("center.hostport"))).sync();

            cf.addListeners();

        } catch (InterruptedException e) {
            logger.error("CenterClient初始化失败：", e); //$NON-NLS-1$
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("CenterClient初始化失败：", e); //$NON-NLS-1$
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * 发送消息
     *
     * @param m
     * @des
     */
    public void SendMsg(final MsgBean m) {
        try {
          //  if (isLogined) {
            if (true) {
                if (chtx != null && chtx.channel().isOpen()) {
                    Constants.bytesToHexString(m.tobytes());
                    ChannelFuture cf = chtx.write(m.tobytes());
                    chtx.flush();
                    cf.addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture future) throws Exception {
                            logger.debug("success complete!!ok!!" + m);
                        }
                    });
                }
            }
        } catch (Exception e) {
            logger.error("发送消息异常", e);
        }
    }

    /**
     * 只发消息不缓存，用于心跳类消息
     *
     * @param m
     */
    public void sendWithoutCache(MsgBean m) {
        if (isLogined) {
            if (chtx != null && chtx.channel().isOpen()) {
                ChannelFuture cf = chtx.write(m.tobytes());
                chtx.flush();
                if (logger.isDebugEnabled()) {
                    logger.debug("发送不缓存消息 - :" + m);
                }
            }
        }
    }



    public void stopClient() {
        try {
            logger.info("关闭连接后台服务");

//            CenterHeartBeatThread.getInstance().stop();
//            CarLocationMsgThread.getInstance().stop();
            CenterClient.connstate = 0;
            this.isLogined = false;
            this.isStoped = true;
            chtx.close();
            if (group != null) {
                group.shutdownGracefully();
                b = null;
                group = null;
            }
            b = null;
            group = null;
            cf = null;
        } catch (Exception e) {
            logger.info("关闭连接后台服务失败", e);
            e.printStackTrace();
        }
    }

    /**
     * send:(发送消息).
     *
     * @param m
     * @author baobao
     */
    public void send(MsgBean m) {
        if (chtx != null && chtx.channel().isOpen()) {
            ChannelFuture cf;
            try {
                 //Constants.bytesToHexString(m.tobytes());
       //        System.out.println("发送消息："+ConstantUtils.byte2hex(m.tobytes()));
                 cf = chtx.writeAndFlush(m.tobytes());
         //        cf = chtx.writeAndFlush("abcd".getBytes());
                //logger.info("客户端－－－》中心网关消息" + m);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ChannelHandlerContext getChtx() {
        return chtx;
    }

    public void setChtx(ChannelHandlerContext chtx) {
        this.chtx = chtx;
    }

    public ChannelFuture getCf() {
        return cf;
    }

    public void setCf(ChannelFuture cf) {
        this.cf = cf;
    }

    public boolean isLogined() {
        return isLogined;
    }
}
