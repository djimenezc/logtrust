package com.djimenezc.service.executor;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Wrapper for a thread executor that runs a task in intervals
 * Created by david on 11/07/2016.
 */
abstract class AbstractIntervalExecutor implements Runnable {

    private Thread t;
    private String threadName;
    private int interval;

    abstract void runTask() throws InterruptedException, IOException;

    abstract void stopTask();

    public void run() {

        Timer timer = new Timer();
        System.out.println("Running thread " + this.threadName);

        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                try {
                    runTask();
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }

        }, 0, interval);
    }

    public void start() {

        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }

    public void stop() {

        if (t != null) {
            System.out.println("Stopping thread " + this.threadName);
            this.stopTask();
            t.interrupt();
        }
    }

    public void join() throws InterruptedException {
        t.join();
    }

    void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getThreadName() {
        return threadName;
    }
}
