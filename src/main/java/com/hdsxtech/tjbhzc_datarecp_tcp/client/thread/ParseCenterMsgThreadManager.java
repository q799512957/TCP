package com.hdsxtech.tjbhzc_datarecp_tcp.client.thread;

import com.hdsxtech.tjbhzc_datarecp_tcp.client.CenterMsgQueue;
import com.hdsxtech.tjbhzc_datarecp_tcp.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.stream.FileImageInputStream;
import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池（处理消息）管理
 */
public class ParseCenterMsgThreadManager extends AbsThread {
	private static final Logger logger = LoggerFactory.getLogger(ParseCenterMsgThreadManager.class);
    private static ParseCenterMsgThreadManager obj;
	private boolean isStart=true;

	public static ParseCenterMsgThreadManager getInstance() {
		if (obj == null)
			obj = new ParseCenterMsgThreadManager();
		return obj;
	}

	private ThreadPoolExecutor threadPool;

	public ParseCenterMsgThreadManager() {
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
		new Thread(new ParseThreadManage()).start();
		logger.info("服务器中心消息处理线程启动完成");
	}

	class ParseThreadManage implements Runnable {

		public void run() {
			while (isStart) {
				byte[] rpb = null;
				try {
					rpb = CenterMsgQueue.getRecqueue().take();
					threadPool.execute(new ParseCenterMsgThread(rpb));
				} catch (Exception e) {
					logger.error("消息解析管理线程运行异常", e);
				}
			}
		}
	}

	@Override
	public void stop() {
		isRun = false;
		isStart=false;
	}


	public static void main(String[] args) {
		File f = new File("E:/abc.png");
		FileImageInputStream in = null;
		try{
			in = new FileImageInputStream(f);
			byte[] imgs = new byte[(int)in.length()];
			in.read(imgs);
			System.out.println(imgs);
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

}
