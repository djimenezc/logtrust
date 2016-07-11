package com.djimenezc.service.manager;

import java.io.IOException;

/**
 * Handle the file where the log entries are stored and provide different methods to
 * get information for the current data stored
 * Created by david on 11/07/2016.
 */
interface LogManager {
    void startLogDaemonGenerator() throws IOException, InterruptedException;

    void stopThreads() throws InterruptedException;
}
