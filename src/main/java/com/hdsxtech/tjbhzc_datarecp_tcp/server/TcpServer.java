package com.hdsxtech.tjbhzc_datarecp_tcp.server;

import com.hdsxtech.tjbhzc_datarecp_tcp.util.PropertiesUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;


/**
 *
 * tcp服务端
 *
 */
public class TcpServer extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(TcpServer.class);
    private int hostport; // 服务器端口
    private LogLevel loglevel;
    private static TcpServer tcpServer;

    public static TcpServer getInstance() {
        if (tcpServer == null) {
            tcpServer = new TcpServer();
        }
        return tcpServer;
    }

    @Override
    public void run() {
        // 初始化服务器
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Properties p = PropertiesUtil.getProperties();
        try {
            this.hostport = Integer.parseInt(p.getProperty("tcp.port"));
            String loglevelname = p.getProperty("tcp.loglevel").toUpperCase();
            loglevel = LogLevel.valueOf(loglevelname);
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new TcpCodec(),
                                  //  new LengthFieldBasedFrameDecoder(5*1024*1024, 2, 4),
                                    new TcpServerHandler());
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128) // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
            ChannelFuture f;
            f = b.bind(hostport).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // workerGroup.shutdownGracefully();
            // bossGroup.shutdownGracefully();
        }
        logger.info("服务端监听初始化完成");
    }
}
