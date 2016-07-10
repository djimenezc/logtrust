package com.djimenezc.service.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Class that generate log entries in a file
 * Created by david on 10/07/2016.
 */
public class LogFileGenerator implements LogGenerator {

    private final File file;
    private PrintWriter printWriter;

    LogFileGenerator(File file) throws IOException {
        this.file = file;
        this.openStream();
    }

    @Override
    public void openStream() throws IOException {

        this.printWriter = new PrintWriter(new FileWriter(this.file));
    }

    @Override
    public void addLogEntry(String entry) {

        printWriter.write(entry + System.lineSeparator());
    }

    @Override
    public void closeStream() {
        printWriter.close();
    }
}
