package com.djimenezc.service.generator;

import com.djimenezc.service.entities.SingleLogEntry;
import com.djimenezc.service.util.TimeUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;

/**
 * Class that generate log entries in a file in different intervals of time
 * Created by david on 10/07/2016.
 */
public class LogFileGenerator implements LogGenerator {

    private List<String> connectedHosts;
    private List<String> receivedHosts;
    private File file;
    private PrintWriter printWriter;

    /**
     * Constructor that received a <code>file<code> where the entries are stored
     *
     * @param file file of entries
     * @throws IOException
     */
    LogFileGenerator(File file) throws IOException {
        this.file = file;
        this.openStream();
    }

    /**
     * Constructor that received a <code>file<code> where the entries are stored
     *
     * @param file           file of entries
     * @param connectedHosts list of connected hosts
     * @param receivedHosts  list of received hosts
     * @throws IOException
     */
    public LogFileGenerator(File file, List<String> connectedHosts, List<String> receivedHosts) throws IOException {

        this(file);

        this.connectedHosts = connectedHosts;
        this.receivedHosts = receivedHosts;
    }

    @Override
    public void openStream() throws IOException {

        if (this.file != null) {

            this.printWriter = new PrintWriter(new FileWriter(this.file));
        }
    }

    @Override
    public void flushChanges() {
        printWriter.flush();
    }

    @Override
    public String writeEntry(String entry, int secondsInterval) {

        addLogEntry(entry);

        return entry;
    }

    @Override
    public String writeEntry(int secondsInterval) {
        return writeEntry(this.getRandomEntryString(secondsInterval), secondsInterval);
    }

    @Override
    public void addLogEntry(String entry) {

        printWriter.write(entry + System.lineSeparator());
    }

    @Override
    public void closeStream() {
        if (this.file != null) {

            printWriter.close();
        }
    }

    public String getRandomEntryString(int seconds) {

        return getRandomEntry(seconds).getEntryString();
    }

    public SingleLogEntry getRandomEntry(int seconds) {

        return new SingleLogEntry(TimeUtil.getRandomTimestamp(seconds), getRandomConnectedHost(), getRandomReceivedHost());
    }

    private int getRandomIndex(List<String> hosts) {

        Random r = new Random();
        int low = 0;
        int high = hosts.size();

        return r.nextInt(high - low) + low;
    }

    private String getRandomConnectedHost() {

        return this.connectedHosts.get(getRandomIndex(this.connectedHosts));
    }

    private String getRandomReceivedHost() {

        return this.receivedHosts.get(getRandomIndex(this.receivedHosts));
    }
}
