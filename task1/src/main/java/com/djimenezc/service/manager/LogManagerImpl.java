package com.djimenezc.service.manager;

import com.djimenezc.service.executor.LogDaemon;
import com.djimenezc.service.parser.LogParserService;

import java.io.File;
import java.io.IOException;

/**
 * Manage the log writing and time to time update the service to have data sorted and sync
 * with the persistent file
 * Created by david on 11/07/2016.
 */
class LogManagerImpl implements LogManager {

    private final File file;
    private LogParserService logParserService;
    private LogDaemon thread1;
    private LogDaemon thread2;

    LogManagerImpl(File file) {
        this.file = file;
    }

    public void startLogDaemonGenerator() throws IOException, InterruptedException {

        thread1 = new LogDaemon(this.file, "Thread-1", 100);
        thread2 = new LogDaemon(this.file, "Thread-2", 400);

        thread1.start();
        thread2.start();
    }

    @Override
    public void stopThreads() throws InterruptedException {
        thread1.stop();
        thread2.stop();
        thread1.join();
        thread2.join();
    }

}
