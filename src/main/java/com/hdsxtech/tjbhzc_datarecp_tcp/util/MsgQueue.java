package com.hdsxtech.tjbhzc_datarecp_tcp.util;

import com.hdsxtech.tjbhzc_datarecp_tcp.bean.ReciPackBean;
import com.hdsxtech.tjbhzc_datarecp_tcp.factory.AbsMsg;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 消息队列
 * 
 */
public class MsgQueue {

	/**
	 * 接收数据队列
	 */
	private static LinkedBlockingQueue<ReciPackBean> rec_queue = new LinkedBlockingQueue<ReciPackBean>();

	public static LinkedBlockingQueue<ReciPackBean> getRecqueue() {
		return rec_queue;
	}

	/**
	 * 接收数据队列
	 */
	private static LinkedBlockingQueue<AbsMsg> base_queue = new LinkedBlockingQueue<AbsMsg>();

	public static LinkedBlockingQueue<AbsMsg> getBasequeue() {
		return base_queue;
	}
}
