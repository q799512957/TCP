package com.hdsxtech.tjbhzc_datarecp_tcp.client.handler;

import com.hdsxtech.tjbhzc_datarecp_tcp.factory.MsgBean;

/**
 *
 * 消息处理接口
 *
 */
public interface IMsgHandle {
	/**
	 * 处理消息
	 * @param msg
	 */
	void DoHandle(MsgBean msg);

}
