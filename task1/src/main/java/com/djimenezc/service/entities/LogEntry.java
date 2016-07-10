package com.djimenezc.service.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * POJO class that represent a log entry
 * Created by david on 10/07/2016.
 */
public class LogEntry implements Serializable {

    private Date created;
    private String sourceHost;
    private String destinationHost;

    public LogEntry(Date created, String sourceHost, String destinationHost) {
        this.created = created;
        this.sourceHost = sourceHost;
        this.destinationHost = destinationHost;
    }

    public Date getCreatedDate() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    private String getSourceHost() {
        return sourceHost;
    }

    private String getDestinationHost() {
        return destinationHost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogEntry logEntry = (LogEntry) o;

        return created != null ? created.equals(logEntry.created) : logEntry.created == null && (sourceHost != null ? sourceHost.equals(logEntry.sourceHost) : logEntry.sourceHost == null && (destinationHost != null ? destinationHost.equals(logEntry.destinationHost) : logEntry.destinationHost == null));

    }

    @Override
    public int hashCode() {
        int result = created != null ? created.hashCode() : 0;
        result = 31 * result + (sourceHost != null ? sourceHost.hashCode() : 0);
        result = 31 * result + (destinationHost != null ? destinationHost.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
            "created=" + created +
            ", sourceHost='" + sourceHost + '\'' +
            ", destinationHost='" + destinationHost + '\'' +
            '}';
    }

    public String getEntryString() {
        return getUnixDate() + " " + getSourceHost() + " " + getDestinationHost();
    }

    private String getUnixDate() {
        return String.valueOf(getCreatedDate().getTime() / 1000);
    }
}
