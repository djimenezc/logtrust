package com.djimenezc.service.parser;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Log entries parser service implementation
 * Created by david on 10/07/2016.
 */
public class LogParserServiceImpl implements LogParserService {

    public LogParserServiceImpl(File file) {
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
