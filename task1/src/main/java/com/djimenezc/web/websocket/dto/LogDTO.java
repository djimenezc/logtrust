package com.djimenezc.web.websocket.dto;

import com.djimenezc.service.entities.SingleLogEntry;

import java.util.Date;

/**
 * DTO for storing a log's activity.
 */
public class LogDTO extends SingleLogEntry{

    public LogDTO(Date created, String sourceHost, String destinationHost) {
        super(created, sourceHost, destinationHost);
    }
}
