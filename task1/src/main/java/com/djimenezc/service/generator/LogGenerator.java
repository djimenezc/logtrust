package com.djimenezc.service.generator;

import java.io.IOException;

/**
 * Interface
 * Created by david on 10/07/2016.
 */
interface LogGenerator {

    void openStream() throws IOException;

    void generateRandomEntry(String entry, int secondsInterval);

    void generateRandomEntry(int secondsInterval);

    void addLogEntry(String entry);

    void closeStream();
}
