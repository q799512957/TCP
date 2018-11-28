package com.hdsxtech.tjbhzc_datarecp_tcp.server.thread;

/**
 *
 * 线程抽象类
 *
 */
public abstract class AbsThread {
    public boolean isRun = false;

    public void run(long delay, long period) {
        if (isRun)
            return;
        isRun = true;
        runThread(delay, period);
    }

    protected abstract void runThread(long delay, long period);

    public abstract void stop();

}
