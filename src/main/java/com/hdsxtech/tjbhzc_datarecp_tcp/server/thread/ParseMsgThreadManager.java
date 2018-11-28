package com.hdsxtech.tjbhzc_datarecp_tcp.server.thread;

import com.hdsxtech.tjbhzc_datarecp_tcp.bean.ReciPackBean;
import com.hdsxtech.tjbhzc_datarecp_tcp.util.MsgQueue;
import com.hdsxtech.tjbhzc_datarecp_tcp.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * 线程池（处理消息）管理
 * 
 */
public class ParseMsgThreadManager extends AbsThread {
	private static final Logger logger = LoggerFactory.getLogger(ParseMsgThreadManager.class);
	private static ParseMsgThreadManager obj;
	private ThreadPoolExecutor threadPool;
	private boolean isStart = true;

	public static ParseMsgThreadManager getInstance() {
		if (obj == null)
			obj = new ParseMsgThreadManager();
		return obj;
	}

	public ParseMsgThreadManager() {
		int corePoolSize = Integer.parseInt(PropertiesUtil.getProperties()
				.getProperty("ParseCorePoolSize"));
		int maximunPoolSize = Integer.parseInt(PropertiesUtil.getProperties()
				.getProperty("ParseMaximumPoolSize"));
		int keepAliveTime = Integer.parseInt(PropertiesUtil.getProperties()
				.getProperty("ParseKeepAliveTime"));
		threadPool = new ThreadPoolExecutor(corePoolSize, maximunPoolSize,
				keepAliveTime, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(corePoolSize),
				new ThreadPoolExecutor.CallerRunsPolicy());
	}

	@Override
	public void runThread(long delay, long period) {
		isRun = true;
		isStart = true;
		new Thread(new ParseThreadManage()).start();
		logger.info("服务器消息处理线程启动完成");
	}

	class ParseThreadManage implements Runnable {

		public void run() {
			while (isStart) {
				ReciPackBean rpb = null;
				try {
					if(MsgQueue.getRecqueue().size() > 0){
						rpb = MsgQueue.getRecqueue().take();
						threadPool.execute(new ParseMsgThread(rpb));
					}else{
						Thread.sleep(5000);
					}
					
				} catch (Exception e) {
					logger.error("消息解析管理线程运行异常", e);
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void stop() {
		isRun = false;
		isStart=false;
	}
}
