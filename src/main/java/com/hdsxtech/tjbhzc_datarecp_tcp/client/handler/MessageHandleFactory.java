package com.hdsxtech.tjbhzc_datarecp_tcp.client.handler;

import com.hdsxtech.tjbhzc_datarecp_tcp.factory.MessageID;
import com.hdsxtech.tjbhzc_datarecp_tcp.factory.MsgBean;
import com.hdsxtech.tjbhzc_datarecp_tcp.util.ConstantUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * 根据消息生成消息处理类
 *
 * @author jkYishon
 * @author zhanglm 修改
 *
 */
public class MessageHandleFactory {
    private static final Logger logger = LoggerFactory.getLogger(MessageHandleFactory.class);

    public static IMsgHandle getHandler(MsgBean msg) {
        try {
            int msgid = (int) msg.getHeader().getByID(ConstantUtils.HEADER_MSGID_ID).getValue();
            IMsgHandle h = null;
            switch (msgid) {
                case MessageID.MESSAGE_CHECKWEIGHT_RESPONSE_ID: // 消息的响应的消息
                    h = new Handler11();
                    break;
                case MessageID.MESSAGE_CHECKIMG_RESPONSE_ID:
                    h = new Handler12();
                default:
                    break;
            }
            return h;
        } catch (Exception e) {
            logger.error("根据消息ID生成handler异常" + e.toString());
            e.printStackTrace();
        }
        return null;
    }
}
