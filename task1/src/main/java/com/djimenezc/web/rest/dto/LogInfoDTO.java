package com.djimenezc.web.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogInfoDTO {

    private final List<String> received;
    private final List<String> connected;
    private final String hostMostConnections;
    private Date date;

    public LogInfoDTO(List<String> connected, List<String> received, String hostMostConnections) {
        this.date = new Date();
        this.connected = connected;
        this.received = received;
        this.hostMostConnections = hostMostConnections;
    }

    public List<String> getReceived() {
        return received;
    }

    public List<String> getConnected() {
        return connected;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @JsonCreator
    public LogInfoDTO() {
        this.date = new Date();
        this.connected = new ArrayList<>();
        this.received = new ArrayList<>();
        this.hostMostConnections = "";
    }

    public String getHostMostConnections() {
        return hostMostConnections;
    }

    @Override
    public String toString() {
        return "LogInfoDTO{" +
            "received=" + received +
            ", connected=" + connected +
            ", hostMostConnections='" + hostMostConnections + '\'' +
            ", date=" + date +
            '}';
    }
}
