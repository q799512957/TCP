package com.hdsxtech.tjbhzc_datarecp_tcp.client;

import com.hdsxtech.tjbhzc_datarecp_tcp.client.thread.CheckImgMsgThread;
import com.hdsxtech.tjbhzc_datarecp_tcp.client.thread.CheckWeightMsgThread;
import com.hdsxtech.tjbhzc_datarecp_tcp.util.Constants;
import com.hdsxtech.tjbhzc_datarecp_tcp.util.PropertiesUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;

public class CenterClientHandler extends ChannelInboundHandlerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(CenterClientHandler.class);
	private int reconnectdealy = Integer.parseInt(PropertiesUtil.getProperties().getProperty("reconnectdealy"));
	private int heartbeatdelay = Integer.parseInt(PropertiesUtil.getProperties().getProperty("tcp.heartbeatdelay"));// 心跳间隔
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		try {
			if (msg instanceof byte[]) {
				final byte[] msgbytes = (byte[]) msg;
				try {
					logger.debug("连接后台服务消息");
					Constants.bytesToHexString(msgbytes);
					CenterMsgQueue.getRecqueue().put(msgbytes);
				} catch (InterruptedException e) {
					logger.error("连接后台服务handler---接收消息队列存储消息失败", e);
					e.printStackTrace();
				}
			} else {
				logger.error("连接后台服务handler---消息解码有误，请检查！！");
			}

		} catch (Exception e) {
			logger.error("连接后台服务handler---channelRead异常",e);
			e.printStackTrace();
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info("与中心建立连接");
		CenterClient.isStoped=false;
		CenterClient.getInstance().setChtx(ctx);
	//	CheckImgMsgThread.getInstance().run(reconnectdealy, heartbeatdelay);
		CheckWeightMsgThread.getInstance().run(reconnectdealy,heartbeatdelay);
		// 打开连接时发送登录消息
		//CenterClient.getInstance().login();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
		InetAddress inetAdd = address.getAddress();
		logger.info("客户端断开连接：" + ctx.name() + ",IP:" + inetAdd.getHostAddress()
				+ ",port:" + address.getPort());
		CenterClient.getInstance().stopClient();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		logger.error("与中心连接主handle---rexceptionCaught异常", cause);
		CenterClient.getInstance().stopClient();
	}
}
