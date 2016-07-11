package com.djimenezc.service.generator;

import com.djimenezc.service.entities.SingleLogEntry;

import java.io.IOException;

/**
 * Interface
 * Created by david on 10/07/2016.
 */
public interface LogGenerator {

    void openStream() throws IOException;

    void flushChanges();

    String writeEntry(String entry, int secondsInterval);

    String writeEntry(int secondsInterval);

    void addLogEntry(String entry);

    void closeStream();

    String getRandomEntryString(int seconds);

    SingleLogEntry getRandomEntry(int seconds);
}
