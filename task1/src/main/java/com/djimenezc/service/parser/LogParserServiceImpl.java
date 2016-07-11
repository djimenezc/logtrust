package com.djimenezc.service.parser;

import com.djimenezc.service.entities.LogEntry;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Log entries parser service implementation
 * Created by david on 10/07/2016.
 */
class LogParserServiceImpl implements LogParserService {

    private Map<Long, LogEntry> entriesMap;

    LogParserServiceImpl(File file) {

        entriesMap = new HashMap<>();
    }

    @Override
    public void parseFile() throws IOException {

    }

    @Override
    public void parseFile(File file) throws IOException {

    }

    @Override
    public List<String> getConnectedHostList(int seconds, String host) {
        return null;
    }

    @Override
    public List<String> getReceivedHostList(int seconds, String host) {
        return null;
    }

    @Override
    public String getHostMostConnections(int seconds) {
        return null;
    }
}
