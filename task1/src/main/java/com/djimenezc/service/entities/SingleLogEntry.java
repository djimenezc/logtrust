package com.djimenezc.service.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * POJO class that represent a log entry
 * Created by david on 10/07/2016.
 */
public class SingleLogEntry extends AbstractLogEntry implements Serializable {

    private String sourceHost;
    private String destinationHost;

    public SingleLogEntry(Date created, String sourceHost, String destinationHost) {
        this.setCreatedDate(created);
        this.sourceHost = sourceHost;
        this.destinationHost = destinationHost;
    }

    public String getSourceHost() {
        return sourceHost;
    }

    public String getDestinationHost() {
        return destinationHost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SingleLogEntry logEntry = (SingleLogEntry) o;

        return this.getCreatedDate() != null ? this.getCreatedDate().equals(logEntry.getCreatedDate()) : logEntry.getCreatedDate() == null && (sourceHost != null ? sourceHost.equals(logEntry.sourceHost) : logEntry.sourceHost == null && (destinationHost != null ? destinationHost.equals(logEntry.destinationHost) : logEntry.destinationHost == null));

    }

    @Override
    public int hashCode() {
        int result = this.getCreatedDate() != null ? this.getCreatedDate().hashCode() : 0;
        result = 31 * result + (sourceHost != null ? sourceHost.hashCode() : 0);
        result = 31 * result + (destinationHost != null ? destinationHost.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
            "created=" + this.getCreatedDate() +
            ", sourceHost='" + sourceHost + '\'' +
            ", destinationHost='" + destinationHost + '\'' +
            '}';
    }

    public String getEntryString() {
        return getUnixDate() + " " + getSourceHost() + " " + getDestinationHost();
    }

    private String getUnixDate() {
        return String.valueOf(this.getCreatedDate().getTime() / 1000);
    }
}
