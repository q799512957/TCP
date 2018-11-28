package com.hdsxtech.tjbhzc_datarecp_tcp.server;


import com.hdsxtech.tjbhzc_datarecp_tcp.bean.Client;
import com.hdsxtech.tjbhzc_datarecp_tcp.bean.ClientMsg;
import com.hdsxtech.tjbhzc_datarecp_tcp.bean.TcpUser;
import com.hdsxtech.tjbhzc_datarecp_tcp.factory.MsgBean;
import com.hdsxtech.tjbhzc_datarecp_tcp.util.ConstantUtils;
import com.hdsxtech.tjbhzc_datarecp_tcp.util.PropertiesUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 *
 * 客户端管理工具
 *
 */
public class ClientManager {
    private static final Logger logger = LoggerFactory.getLogger(ClientManager.class);

    /**
     * 客户端连接
     */
    private static ConcurrentHashMap<String, Client> clientMap = new ConcurrentHashMap<String, Client>();
    /**
     * 流水号和客户端对应关系
     */
    public static Vector<ClientMsg> clientMsg_sn = new Vector<>();

    /**
     * 客户端临时连接--未登录前
     */
    private static ConcurrentHashMap<String, Client> tempClientMap = new ConcurrentHashMap<String, Client>();

    /**
     * 添加一个临时客户端
     *
     * @param ctx
     */
    public static void addTemClient(ChannelHandlerContext ctx) {
        Client client = new Client();
        client.setChannel(ctx);
        client.setLastUpTime(new Date());
        client.setLogin(false);
        String clientId = getClientId(ctx);

        logger.info("添加临时客户端：" + clientId);

        ChannelHandlerContext channel = null;
        for (String key : tempClientMap.keySet()) {
            if (getIP_Port(clientId)[0].equals(getIP_Port(key)[0])) {
                logger.info("新Channel接入移除旧临时客户端：");
                channel = tempClientMap.get(key).getChannel();
                removeTemClient(channel);
                if (channel != null && channel.channel().isOpen()) {
                    channel.close();
                }
                channel = null;
            }
        }

        for (String key : clientMap.keySet()) {
            if (getIP_Port(clientId)[0].equals(getIP_Port(key)[0])) {
                logger.info("新Channel接入移除客户端：");
                channel = clientMap.get(key).getChannel();
                removeClient(clientMap.get(key).getChannel());
                if (channel != null && channel.channel().isOpen()) {
                    channel.close();
                }
                channel = null;
            }
        }
        tempClientMap.put(clientId, client);
    }

    /**
     * 移除一个临时客户端
     *
     * @param channel
     */
    public static void removeTemClient(ChannelHandlerContext channel) {
        String clientId = getClientId(channel);
        Client client = tempClientMap.remove(clientId);
        if (client != null) {
            logger.info("------------移除临时客户端：" + clientId + "-------------------");
        }
    }

    /**
     * 添加一个客户端
     *
     * @param channel
     */
    public static void addClient(ChannelHandlerContext channel, TcpUser user, String zdid) {
        String clientId = getClientId(channel);
        if (clientId == null) {
            return;
        }
        Client client = clientMap.get(clientId);
        if (client != null) {// 客户端已经登录过了
            logger.info("客户端重复登录");
        } else {
            client = tempClientMap.get(clientId);
            if (client != null) {
                removeTemClient(channel);
            } else {
                logger.error("channel在临时客户端及客户端库中都不存在");
                return;
            }
        }
        client.setChannel(channel);
        client.setLastUpTime(new Date());
        client.setLogin(true);
        client.setZdid(zdid);

        logger.info("------------添加客户端：" + clientId + "-------------------");
        clientMap.put(clientId, client);
    }

    /**
     * 移除一个客户端
     *
     * @param channel
     */
    public static void removeClient(ChannelHandlerContext channel) {
        String clientId = getClientId(channel);
        Client client = clientMap.remove(clientId);
        if (client != null) {
            logger.info("------------移除客户端：" + clientId + "-------------------");
        }
    }

    /**
     * 移除未登陆客户端
     */
    public static void checkTempClient() {
        Iterator<Map.Entry<String, Client>> it = tempClientMap.entrySet().iterator();
        Client client = null;
        Date nowDate = new Date();
        ChannelHandlerContext channel = null;
        while (it.hasNext()) {
            Map.Entry<String, Client> entry = it.next();
            client = entry.getValue();
            if (nowDate.getTime() - client.getLastUpTime().getTime() >= Integer
                    .parseInt(PropertiesUtil.getProperties().getProperty(
                            "tcp.temheartbeatdelay")) * 1000
                    && !client.isLogin()) {
                it.remove();
                channel = client.getChannel();
                if (channel.channel().isOpen()) {
                    logger.info("移除未登录的客户端(超时连接)，连接name："
                            + getClientId(channel));
                    channel.close();
                }
                channel = null;
            }
        }
    }

    /**
     * 移除登陆客户端
     */
    public static void checkClient() {
        Iterator<Map.Entry<String, Client>> it = clientMap.entrySet().iterator();
        Client client = null;
        Date nowDate = new Date();
        ChannelHandlerContext channel = null;
        while (it.hasNext()) {
            Map.Entry<String, Client> entry = it.next();
            client = entry.getValue();
            if (nowDate.getTime() - client.getLastUpTime().getTime() >= Integer
                    .parseInt(PropertiesUtil.getProperties().getProperty(
                            "tcp.heartbeatdelay")) * 1000
                    && client.isLogin()) {
                // it.remove();
                removeClient(client.getChannel());
                channel = client.getChannel();
                if (channel.channel().isOpen()) {
                    logger.info("移除登录的客户端(超过空闲时间)，连接name："
                            + getClientId(channel));
                    channel.close();
                }
                channel = null;
            }
        }
    }

    /**
     * 发送消息
     *
     * @param m
     * @param ctx
     */
    public static void sendData(final MsgBean m, final ChannelHandlerContext ctx) {
        try {
            if (ctx != null && ctx.channel().isOpen()) {
                ChannelFuture cf = ctx.write(m.tobytes());
                System.out.println("发送消息："+m.toString());
                ctx.flush();
                System.out.println(ClientManager.getClient(ctx));
//                System.out.println(ClientManager.getClient(ctx).getMsgidMap());
                cf.addListener(new ChannelFutureListener() {

                    @Override
                    public void operationComplete(ChannelFuture future) {
                    	try{
                    		if (ClientManager.getClient(ctx) != null) {
                            ClientManager.getClient(ctx).getMsgidMap().remove(m.getHeader().getByID(ConstantUtils.HEADER_MSGID_ID));
                    			logger.info("respone success complete!!ok!!" + m);
                    		}
                    	}catch(Exception e){
                    		e.printStackTrace();
                    		}
                    }
                });
            } else {
                logger.info("发送消息---Channel断开或者为空：channel" + ctx);
            }
        } catch (Exception e) {
            logger.error("发送消息失败：转码", e);
            e.printStackTrace();
        }
    }

    /**
     * 发送消息
     *
     * @param m
     * @param ctx
     */
    public static void sendData(final byte[] m, final ChannelHandlerContext ctx) {
        try {
            if (ctx != null && ctx.channel().isOpen()) {
                ChannelFuture cf = ctx.write(m);
                //System.out.println("发送消息："+Arrays.toString(m));
                ctx.flush();
                System.out.println(ClientManager.getClient(ctx));
//                System.out.println(ClientManager.getClient(ctx).getMsgidMap());
                cf.addListener(new ChannelFutureListener() {

                    @Override
                    public void operationComplete(ChannelFuture future) {
                        try{
                            if (ClientManager.getClient(ctx) != null) {
                                ClientManager.getClient(ctx).getMsgidMap().remove(m[6] & 0xFF);
                                logger.info("respone success complete!!ok!!" + m);
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                logger.info("发送消息---Channel断开或者为空：channel" + ctx);
            }
        } catch (Exception e) {
            logger.error("发送消息失败：转码", e);
            e.printStackTrace();
        }
    }

    /**
     * 发送消息(透传)
     *
     * @param m
     * @param ctx
     */
    public static void sendDataBytes(final byte[] m, ChannelHandlerContext ctx) {
        if (ctx != null && ctx.channel().isOpen()) {
            ChannelFuture cf = ctx.write(m);
            ctx.flush();

            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    logger.debug("respone success complete!!ok!!" + m);
                }
            });
        } else {
            logger.info("发送消息---Channel断开或者为空：channel" + ctx);
        }
    }

    /**
     * 发送消息
     *
     * @param m
     * @param ctx
     */
    public static void sendDataClient(final MsgBean m, ChannelHandlerContext ctx, final Client cl) {
        if (ctx != null && ctx.channel().isOpen()) {
            ChannelFuture cf = ctx.write(m.tobytes());
            ctx.flush();
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    cl.getMsgidMap().remove(m.getHeader().getByID(ConstantUtils.HEADER_MSGID_ID));
                    logger.debug("respone success complete!!ok!!" + m);
                }
            });
        } else {
            logger.info("发送消息---Channel断开或者为空：channel" + ctx);
        }
    }

    /**
     * 发送应答消息
     *
     * @param answer
     * @param ctx
     */
    public static void sendAnswerData(MsgBean answer, ChannelHandlerContext ctx) {
        sendData(answer, ctx);
    }

//    /**
//     * 保存日志
//     *
//     * @param xzqhdm
//     * @param type
//     * @param ip
//     * @param port
//     */
//    public static void saveLog(String xzqhdm, byte type, String ip, int port) {
//        TcpClientLog tcpClientLog = new TcpClientLog();
//        tcpClientLog.setConnectype(type);
//        tcpClientLog.setXzqhdm(xzqhdm);
//        tcpClientLog.setTime(new Date());
//        tcpClientLog.setIp(ip);
//        tcpClientLog.setPort(port);
////		MsgQueue.getTcpLogqueue().add(tcpClientLog);
//    }

    /**
     * 查询登陆的客户端
     *
     * @param ctx
     * @return
     */
    public static Client getClient(ChannelHandlerContext ctx) {
        String clientId = getClientId(ctx);
        if (tempClientMap.containsKey(clientId)) {
            return tempClientMap.get(clientId);
        }
        logger.info("没有找到该客户端:" + clientId);
        return null;
    }
    
    /**
     * 查询登陆的客户端
     *
     * @param
     * @return
     */
    public static ConcurrentHashMap<String, Client> getClient() {
    	return clientMap;
    }

    /**
     * 更新最后一次收到消息时间
     *
     * @param ctx
     * @param client
     */
    public static void setClientLastTime(ChannelHandlerContext ctx, Client client) {
        client.setLastUpTime(new Date());
        clientMap.put(getClientId(ctx), client);
    }

    /**
     * 获取clientId
     *
     * @param ctx
     * @return
     */
    public static String getClientId(ChannelHandlerContext ctx) {
        try {
            InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
            InetAddress inetAdd = address.getAddress();
            return inetAdd.getHostAddress() + ":" + address.getPort() + "," + ctx.name();
        } catch (Exception e) {
            logger.error("取客户端id异常：", e);
            return null;
        }
    }

    /**
     * 获取ip和端口号：0：ip，1：端口
     *
     * @param clientId
     * @return
     */
    public static String[] getIP_Port(String clientId) {
        String[] ip_port = null;
        if (clientId != null && !"".equals(clientId)) {
            String[] clientString = clientId.split(",");
            if (clientString != null && clientString.length == 2) {
                ip_port = clientString[0].split(":");
            }
        }
        return ip_port;
    }

    public static ConcurrentHashMap<String, Client> getClientMap() {
        return clientMap;
    }

    public static void setClientMap(ConcurrentHashMap<String, Client> clientMap) {
        ClientManager.clientMap = clientMap;
    }

    /**
     * 根据chtx获取对应client
     */
    public static Client getClientByChtx(ChannelHandlerContext channel) {
        Enumeration<Client> en = ClientManager.getClientMap().elements();
        while (en.hasMoreElements()) {
            Client cl = en.nextElement();

            if (cl.getChannel() == channel) {
                return cl;
            }
        }
        return null;
    }
}
