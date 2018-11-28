package com.hdsxtech.tjbhzc_datarecp_tcp.server.thread;

import com.hdsxtech.tjbhzc_datarecp_tcp.server.ClientManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * 检查长时间未活跃的客户端
 *
 */
public class CheckClientThread extends AbsThread {
    private static final Logger logger = LoggerFactory.getLogger(CheckClientThread.class);
    private static CheckClientThread obj;
    private boolean isStart = true;
    private Timer timer = new Timer();

    public static CheckClientThread getInstance() {
        if (obj == null)
            obj = new CheckClientThread();
        return obj;
    }

    @Override
    public void runThread(long delay, long period) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    ClientManager.checkClient();
                } catch (Exception e) {
                    logger.error("检查登录客户端异常:", e);
                }
            }
        }, delay * 1000, period * 1000);
    }

    @Override
    public void stop() {
        isRun = false;
        isStart = false;
    }
}
