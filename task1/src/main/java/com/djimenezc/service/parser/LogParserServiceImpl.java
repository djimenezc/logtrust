package com.djimenezc.service.parser;

import com.djimenezc.service.entities.MultipleLogEntry;
import com.djimenezc.service.entities.SingleLogEntry;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Log entries parser service implementation
 * Created by david on 10/07/2016.
 */
class LogParserServiceImpl implements LogParserService {

    private static final int INTERVAL_HOST_MOST_CONNECTIONS = 3600;

    private File file;

    private Map<Long, MultipleLogEntry> entriesMap;
    private LogParserHelper parserHelper;

    Map<Long, MultipleLogEntry> getEntriesMap() {
        return entriesMap;
    }

    LogParserServiceImpl(File file) throws IOException {

        parserHelper = new LogParserHelperImpl();
        this.loadFile(file);
    }

    @Override
    public void loadFile(File file) throws IOException {

        this.file = file;
        entriesMap = parserHelper.parseFile(this.file);
    }

    @Override
    public Map<Long, MultipleLogEntry> parseFile(File file) throws IOException {

        return parserHelper.readLogEntries(file);
    }

    @Override
    public List<String> getConnectedHostList(String host) {
        return parserHelper.getConnectedHostList(INTERVAL_HOST_MOST_CONNECTIONS, host, this.entriesMap);
    }

    @Override
    public List<String> getReceivedHostList(String host) {
        return parserHelper.getReceivedHostList(INTERVAL_HOST_MOST_CONNECTIONS, host, this.entriesMap);
    }

    @Override
    public String getHostMostConnections() {
        return parserHelper.getHostMostConnections(INTERVAL_HOST_MOST_CONNECTIONS, this.entriesMap);
    }

    @Override
    public long getNumberEntries() {

        long size = 0;

        for (MultipleLogEntry multipleLogEntry : this.entriesMap.values()) {
            size += multipleLogEntry.getLogEntries().size();
        }

        return size;
    }

    public File getFile() {
        return file;
    }

    @Override
    public SingleLogEntry addLogEntry(String line) {

        SingleLogEntry logEntry = parserHelper.getLogEntry(line);

        Long time = logEntry.getCreatedDate().getTime();

        parserHelper.getMultipleLogEntry((TreeMap<Long, MultipleLogEntry>) this.getEntriesMap(), logEntry, time);

        return logEntry;
    }

}
