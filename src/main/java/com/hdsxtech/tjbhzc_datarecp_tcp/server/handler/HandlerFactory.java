package com.hdsxtech.tjbhzc_datarecp_tcp.server.handler;

import com.hdsxtech.tjbhzc_datarecp_tcp.factory.MessageID;
import com.hdsxtech.tjbhzc_datarecp_tcp.factory.MsgBean;
import com.hdsxtech.tjbhzc_datarecp_tcp.msg.IHandler;
import com.hdsxtech.tjbhzc_datarecp_tcp.util.ConstantUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * 客户端消息handler工厂
 * 
 */
public class HandlerFactory {
	private static final Logger logger = LoggerFactory.getLogger(HandlerFactory.class);

	public static IHandler getHandler(byte[] m) {
		try {
		int msgid=m[6] & 0xFF;

			if (logger.isDebugEnabled()) {
				logger.debug("handler工厂产生消息:" + Integer.toHexString(msgid));
			}
			IHandler h = null;
			switch (msgid) {
                case MessageID.MESSAGE_CHECKWEIGHT_ID: 			// 车辆称重信息
                	h = new Handler11();
                	break;
                case MessageID.MESSAGE_CHECKIMG_ID: 			// 车辆称重图片信息
                	h = new Handler12();
                	break;
                default:
                    break;
			}
			return h;
		} catch (Exception e) {
			logger.error("根据消息id生成handler异常",e);
			e.printStackTrace();
		}
		return null;
	}
}
