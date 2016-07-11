package com.djimenezc.service.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that stores multiples log entries that happened in the same moment
 * Created by david on 11/07/2016.
 */
public class MultipleLogEntry extends AbstractLogEntry{

    private List<SingleLogEntry> logEntries;

    public MultipleLogEntry(SingleLogEntry logEntry) {
        this.setCreatedDate(logEntry.getCreatedDate());
        logEntries = new ArrayList<>();
        logEntries.add(logEntry);
    }

    public List<SingleLogEntry> getLogEntries() {
        return logEntries;
    }

    public void setLogEntries(List<SingleLogEntry> logEntries) {
        this.logEntries = logEntries;
    }

    public List<SingleLogEntry> addLogEntry(SingleLogEntry logEntry) {
        this.logEntries.add(logEntry);

        return this.logEntries;
    }

}
