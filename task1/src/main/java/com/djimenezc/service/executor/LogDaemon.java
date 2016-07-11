package com.djimenezc.service.executor;

import com.djimenezc.service.generator.LogFileGenerator;
import com.djimenezc.service.generator.LogGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Random log generator
 * <p>
 * Created by david on 11/07/2016.
 */
public class LogDaemon implements Runnable {

    private final int interval;
    private File file;
    private LogGenerator logFileGenerator;
    private Thread t;
    private String threadName;

    /**
     * Constructor
     *
     * @param file File
     * @param name process name
     * @param interval each how many seconds the process writes in the file
     * @throws IOException
     */
    public LogDaemon(File file, String name, int interval) throws IOException {

        this.file = file;
        this.threadName = name;
        this.interval = interval;

        List<String> connectedHosts = new ArrayList<>();
        connectedHosts.add("connected1");
        connectedHosts.add("connected2");
        connectedHosts.add("connected3");
        List<String> receivedHosts = new ArrayList<>();
        receivedHosts.add("received1");
        receivedHosts.add("received2");
        receivedHosts.add("received3");

        logFileGenerator = new LogFileGenerator(this.file, connectedHosts, receivedHosts);
    }

    public void run() {

        Timer timer = new Timer();
        System.out.println("Running thread " + this.threadName);

        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                System.out.println(logFileGenerator.writeEntry(interval));
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
            t.interrupt();
            logFileGenerator.closeStream();
        }
    }

    public void join() throws InterruptedException {
        t.join();
    }

}
