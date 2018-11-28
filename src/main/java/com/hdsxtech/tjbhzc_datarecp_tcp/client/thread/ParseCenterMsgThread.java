package com.hdsxtech.tjbhzc_datarecp_tcp.client.thread;


import com.hdsxtech.tjbhzc_datarecp_tcp.client.handler.IMsgHandle;
import com.hdsxtech.tjbhzc_datarecp_tcp.client.handler.MessageHandleFactory;
import com.hdsxtech.tjbhzc_datarecp_tcp.factory.MsgBean;
import com.hdsxtech.tjbhzc_datarecp_tcp.util.ConstantUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.ByteBuffer;


/**
 * 处理消息线程
 */
public class ParseCenterMsgThread extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(ParseCenterMsgThread.class);
    private byte[] rpb;

    public ParseCenterMsgThread(byte[] rpb) {
        this.rpb = rpb;
    }

    @Override
    public void run() {
        try {
            int msgid = rpb[Integer.valueOf(ConstantUtils.HEADER_MSGID_ID)] & 0x00ff;
            MsgBean msg = head_bodyFromBytes(rpb, msgid);
            if (msg == null) {
                return;
            }
            // 交给对应handler处理
            IMsgHandle handler = MessageHandleFactory.getHandler(msg);
            if (handler != null) {
                handler.DoHandle(msg);
            }
        } catch (Exception e) {
            logger.error("接受消息队列处理数据错误" + e.toString());
        }
    }

    /**
     * 消息头解析
     *
     * @return
     */
    private MsgBean head_bodyFromBytes(byte[] b, int msgid) {
        ByteBuffer buffer1 = ByteBuffer.wrap(b);
        byte[] head_body = new byte[b.length - 1];
        buffer1.position(0);
        buffer1.get(head_body);

        // 先获取消息id
        MsgBean msg905 = new MsgBean(msgid, ConstantUtils.KEY_CENTER);
        msg905.frombytes(b, ConstantUtils.KEY_CENTER);
        return msg905;
    }
}
