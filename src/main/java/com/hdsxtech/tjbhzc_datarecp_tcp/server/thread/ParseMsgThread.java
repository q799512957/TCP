package com.hdsxtech.tjbhzc_datarecp_tcp.server.thread;

import com.hdsxtech.tjbhzc_datarecp_tcp.bean.ClientMsg;
import com.hdsxtech.tjbhzc_datarecp_tcp.bean.ReciPackBean;
import com.hdsxtech.tjbhzc_datarecp_tcp.factory.DataTypeUtil;
import com.hdsxtech.tjbhzc_datarecp_tcp.factory.MsgBean;
import com.hdsxtech.tjbhzc_datarecp_tcp.msg.IHandler;
import com.hdsxtech.tjbhzc_datarecp_tcp.server.ClientManager;
import com.hdsxtech.tjbhzc_datarecp_tcp.server.handler.HandlerFactory;
import com.hdsxtech.tjbhzc_datarecp_tcp.util.ConstantUtils;
import com.hdsxtech.tjbhzc_datarecp_tcp.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 *
 * 处理消息线程
 *
 */
public class ParseMsgThread extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(ParseMsgThread.class);
    private ReciPackBean rpb;

    public ParseMsgThread(ReciPackBean rpb) {
        this.rpb = rpb;
    }

    @Override
    public void run() {
        try {
            byte[] msgbytes = rpb.getMsgbytes();
           // System.out.println("msgbytes"+ Arrays.toString(msgbytes));
//            if (!MsgBean.checkMac(msgbytes)) {
//                logger.info("消息校验码错误");
//            }
//            System.out.println(ConstantUtils.byte2hex(rpb.getMsgbytes()));
            //int msgid = (msgbytes[Integer.valueOf(ConstantUtils.HEADER_MSGID_ID)] + (msgbytes[Integer.valueOf(ConstantUtils.HEADER_MSGID_ID) + 1] * 256)) & 0xffff;
//            MsgBean msg905 = head_bodyFromBytes(msgbytes);
//            if (msg905 == null) {
//                return;
//            }

            // 登录消息处理
           /* if (msgid == MessageID.MESSAGE_SIGNIN_ID) {
                logger.debug("客户端－－》登录");
            } else {// 其它消息处理
                String str = "";
                Client client = ClientManager.getClient(rpb.getChannel());
                if (client == null || !client.isLogin()) {
                    logger.info("客户端没有登录，请先登录后再发送消息");
                    ClientManager.removeTemClient(rpb.getChannel());
                    ChannelHandlerContext channel = null;
                    channel = rpb.getChannel();
                    if (channel.channel().isOpen()) {
                        logger.info("客户端没有登录，发送业务数据，主动断开连接，连接name："
                                + ClientManager.getClientId(channel));
                        channel.close();
                    }
                    channel = null;
                    return;
                }
                ClientManager.setClientLastTime(rpb.getChannel(), client);
            }*/
            // 交给对应handler处理
            IHandler handler = HandlerFactory.getHandler(msgbytes);
            if (handler != null) {
                handler.doHandle(msgbytes, rpb.getChannel());
            }
        } catch (Exception e) {
            logger.error("接受消息队列处理数据错误", e);
        }
    }

    private byte[] updateMsg_sn(byte[] msgbytes, long vall) {
        byte[] b = new byte[4];
        b[3] = (byte) (vall >> 0);
        b[2] = (byte) (vall >> 8);
        b[1] = (byte) (vall >> 16);
        b[0] = (byte) (vall >> 24);
        System.arraycopy(b, 0, msgbytes, 25, 4);
        return msgbytes;
    }

    /**
     * 获取客户端消息流水号
     *
     * @return
     */
    private long getMsg_sn(byte[] b) {
        byte[] msg_sn = new byte[4];
        System.arraycopy(b, 25, msg_sn, 0, 4);
        return DataTypeUtil.byte2unsignInt32(msg_sn);
    }

    /**
     *
     * 缓存客户端消息
     *
     * @return
     */
    private byte[] CacheMsg_sn(byte[] b) {
        long msg_sn = getMsg_sn(b);
        ClientMsg cm = new ClientMsg();
        cm.setChannel(rpb.getChannel());
        cm.setMsg_sn_o(msg_sn);
        long msg_sn_n = Constants.getMsg_sn();
        cm.setMsg_sn(msg_sn_n);
        cm.setSendTime(System.currentTimeMillis());
        byte[] msg = updateMsg_sn(b, msg_sn_n);
        ClientManager.clientMsg_sn.addElement(cm);
        //更新校验码
        byte[] mac = new byte[1];
        for (int i = 0; i < msg.length - 1; i++) {
            mac[0] ^= msg[i];
        }
        System.arraycopy(mac, 0, msg, msg.length - 1, 1);
        return msg;
    }

    /**
     *
     * 消息头解析
     *
     * @return
     */
    private MsgBean head_bodyFromBytes(byte[] b) {
        ByteBuffer buffer1 = ByteBuffer.wrap(b);
        byte[] head_body = new byte[b.length];
        buffer1.position(0);
        buffer1.get(head_body);

        //先获取消息id
        MsgBean msg905 = new MsgBean(b[Integer.valueOf(ConstantUtils.HEADER_MSGID_ID)]+b[Integer.valueOf(ConstantUtils.HEADER_MSGID_ID)+1]*256 & 0x00ff, ConstantUtils.KEY_CENTER);
        msg905.frombytes(head_body, ConstantUtils.KEY_CENTER);
        return msg905;
    }

    /**
     *
     * 解码转义
     *
     * @param b
     * @return
     */
    private byte[] decode(byte[] b) {
        ByteBuffer buffer = ByteBuffer.allocate(10 * 1024 * 1024);
        ByteBuffer buffer1 = ByteBuffer.wrap(b);
        buffer.position(0);
        while (buffer1.remaining() > 0) {
            byte d = buffer1.get();
            if (d == 0x5a) {
                byte e = buffer1.get();
                if (e == 0x02)
                    buffer.put((byte) 0x5a);
                else if (e == 0x01)
                    buffer.put((byte) 0x5b);
            } else if (d == 0x5e) {
                byte e = buffer1.get();
                if (e == 0x02)
                    buffer.put((byte) 0x5e);
                else if (e == 0x01)
                    buffer.put((byte) 0x5d);
            } else {
                buffer.put(d);
            }
        }
        byte[] result = new byte[buffer.position()];
        buffer.position(0);
        buffer.get(result);
        return result;
    }
}
