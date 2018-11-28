package com.hdsxtech.tjbhzc_datarecp_tcp.client;



import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * 消息队列
 * 
 */
public class CenterMsgQueue {

	/**
	 * 接收数据队列
	 */
	private static LinkedBlockingQueue<byte[]> rec_queue = new LinkedBlockingQueue<byte[]>();
	public static LinkedBlockingQueue<byte[]> getRecqueue() {
		return rec_queue;
	}
	
}
