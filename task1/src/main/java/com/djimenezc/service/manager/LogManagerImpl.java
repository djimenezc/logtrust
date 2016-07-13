package com.djimenezc.service.manager;

import com.djimenezc.service.executor.LogGeneratorDaemon;
import com.djimenezc.service.executor.TailDaemon;
import com.djimenezc.service.parser.LogParserService;
import com.djimenezc.service.parser.LogParserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manage the log writing and time to time update the service to have data sorted and sync
 * with the persistent file
 * Created by david on 11/07/2016.
 */
@Service
class LogManagerImpl implements LogManager {

    private static final int TAIL_WATCHING_TIME = 1000;
    private static final int THREAD_ONE_GENERATING_TIME = 100;
    private static final int THREAD_TWO_GENERATING_TIME = 400;
    private final File file;
    private LogGeneratorDaemon thread1;
    private LogGeneratorDaemon thread2;
    private TailDaemon tailThread;
    private LogParserService logParserService;

    private final Logger log = LoggerFactory.getLogger(LogManagerImpl.class);

    LogManagerImpl(File file) {
        this.file = file;
    }

    @SuppressWarnings("unused")
    LogManagerImpl() throws IOException, InterruptedException {

        log.info("Starting log manager");
        this.file = File.createTempFile("log", ".txt");
        log.info("File created in " + this.file.getAbsolutePath());

        this.startLogDaemonGenerator();
        this.startTailDaemonGenerator();
    }

    @Override
    public void startLogDaemonGenerator() throws IOException, InterruptedException {

        log.info("startLogDaemonGenerator");
        List<String> connectedHosts = new ArrayList<>();
        connectedHosts.add("connected1");
        connectedHosts.add("connected2");
        connectedHosts.add("connected5");
        List<String> receivedHosts = new ArrayList<>();
        receivedHosts.add("received1");
        receivedHosts.add("received2");
        receivedHosts.add("received3");
        receivedHosts.add("received4");
        receivedHosts.add("received5");

        List<String> connectedHosts2 = new ArrayList<>();
        connectedHosts2.add("connected2");
        List<String> receivedHosts2 = new ArrayList<>();
        receivedHosts2.add("received3");

        thread1 = new LogGeneratorDaemon(this.file, "Thread-1", THREAD_ONE_GENERATING_TIME, connectedHosts, receivedHosts);
        thread2 = new LogGeneratorDaemon(this.file, "Thread-2", THREAD_TWO_GENERATING_TIME, connectedHosts2, receivedHosts2);

        thread1.start();
        thread2.start();
    }

    @Override
    public void startTailDaemonGenerator() throws IOException, InterruptedException {

        log.info("startTailDaemonGenerator");

        this.logParserService = new LogParserServiceImpl(file);

        tailThread = new TailDaemon(this.logParserService, "Thread-tail", TAIL_WATCHING_TIME);

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
