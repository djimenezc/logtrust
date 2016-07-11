package com.djimenezc.service.manager;

import com.djimenezc.service.parser.LogParserService;

import java.io.IOException;

/**
 * Handle the file where the log entries are stored and provide different methods to
 * get information for the current data stored
 * Created by david on 11/07/2016.
 */
interface LogManager {
    void startLogDaemonGenerator() throws IOException, InterruptedException;

    void startTailDaemonGenerator() throws IOException, InterruptedException;

    void stopThreads() throws InterruptedException;

    LogParserService getLogParserService();
}
