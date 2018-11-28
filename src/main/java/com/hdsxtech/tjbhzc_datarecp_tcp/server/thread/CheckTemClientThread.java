package com.hdsxtech.tjbhzc_datarecp_tcp.server.thread;



import com.hdsxtech.tjbhzc_datarecp_tcp.server.ClientManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * 检查未登录的客户端
 *
 */
public class CheckTemClientThread extends AbsThread {
    private static final Logger logger = LoggerFactory.getLogger(CheckTemClientThread.class);
    private static CheckTemClientThread obj;
    private Timer timer = new Timer();

    public static CheckTemClientThread getInstance() {
        if (obj == null)
            obj = new CheckTemClientThread();
        return obj;
    }

    @Override
    public void runThread(long delay, long period) {
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                try {
                    ClientManager.checkTempClient();
                } catch (Exception e) {
                    logger.error("检查临时客户端异常：", e);
                }
            }
        }, delay * 1000, period * 1000);
    }

    @Override
    public void stop() {
        isRun = false;
    }
}
