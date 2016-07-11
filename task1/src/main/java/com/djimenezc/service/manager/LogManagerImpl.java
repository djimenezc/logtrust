package com.djimenezc.service.manager;

import com.djimenezc.service.executor.LogGeneratorDaemon;
import com.djimenezc.service.executor.TailDaemon;
import com.djimenezc.service.parser.LogParserService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manage the log writing and time to time update the service to have data sorted and sync
 * with the persistent file
 * Created by david on 11/07/2016.
 */
class LogManagerImpl implements LogManager {

    private final File file;
    private LogParserService logParserService;
    private LogGeneratorDaemon thread1;
    private LogGeneratorDaemon thread2;
    private TailDaemon tailThread;

    LogManagerImpl(File file) {
        this.file = file;
    }

    public void startLogDaemonGenerator() throws IOException, InterruptedException {

        List<String> connectedHosts = new ArrayList<>();
        connectedHosts.add("connected1");
        connectedHosts.add("connected3");
        List<String> receivedHosts = new ArrayList<>();
        receivedHosts.add("received1");
        receivedHosts.add("received2");


        thread1 = new LogGeneratorDaemon(this.file, "Thread-1", 100, connectedHosts, receivedHosts);
        connectedHosts.clear();
        connectedHosts.add("connected2");
        receivedHosts.clear();
        receivedHosts.add("received3");
        thread2 = new LogGeneratorDaemon(this.file, "Thread-2", 400, connectedHosts, receivedHosts);

        thread1.start();
        thread2.start();
    }

    public void startTailDaemonGenerator() throws IOException, InterruptedException {

        tailThread = new TailDaemon(this.file, "Thread-tail", 100);

        tailThread.start();
    }

    @Override
    public void stopThreads() throws InterruptedException {

        if (thread1 != null) {
            thread1.stop();
            thread1.join();
        }

        if (thread2 != null) {
            thread2.stop();
            thread2.join();
        }

        if (tailThread != null) {
            tailThread.stop();
            tailThread.join();
        }
    }

}
