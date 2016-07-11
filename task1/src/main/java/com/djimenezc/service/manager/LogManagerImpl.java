package com.djimenezc.service.manager;

import com.djimenezc.service.executor.LogGeneratorDaemon;
import com.djimenezc.service.executor.TailDaemon;
import com.djimenezc.service.parser.LogParserService;
import com.djimenezc.service.parser.LogParserServiceImpl;

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
    private LogGeneratorDaemon thread1;
    private LogGeneratorDaemon thread2;
    private TailDaemon tailThread;
    private LogParserService logParserService;

    LogManagerImpl(File file) {
        this.file = file;
    }

    @Override
    public void startLogDaemonGenerator() throws IOException, InterruptedException {

        List<String> connectedHosts = new ArrayList<>();
        connectedHosts.add("connected1");
        connectedHosts.add("connected3");
        List<String> receivedHosts = new ArrayList<>();
        receivedHosts.add("received1");
        receivedHosts.add("received2");

        List<String> connectedHosts2 = new ArrayList<>();
        connectedHosts2.add("connected2");
        List<String> receivedHosts2 = new ArrayList<>();
        receivedHosts2.add("received3");

        thread1 = new LogGeneratorDaemon(this.file, "Thread-1", 100, connectedHosts, receivedHosts);
        thread2 = new LogGeneratorDaemon(this.file, "Thread-2", 400, connectedHosts2, receivedHosts2);

        thread1.start();
        thread2.start();
    }

    @Override
    public void startTailDaemonGenerator() throws IOException, InterruptedException {

        this.logParserService = new LogParserServiceImpl(file);

        tailThread = new TailDaemon(this.logParserService, "Thread-tail", 1000);

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

    @Override
    public LogParserService getLogParserService() {
        return logParserService;
    }
}
