package com.djimenezc.service.generator;

import java.io.IOException;

/**
 * Interface
 * Created by david on 10/07/2016.
 */
public interface LogGenerator {

    void openStream() throws IOException;

    void addLogEntry(String entry);

    void closeStream() ;
}
