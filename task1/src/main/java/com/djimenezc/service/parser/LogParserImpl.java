package com.djimenezc.service.parser;

import com.djimenezc.service.entities.LogEntry;
import com.djimenezc.service.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Implement log parser that sort out the lines of file
 * <p>
 * Created by david on 10/07/2016.
 */
class LogParserImpl implements LogParser {

    private static final java.lang.String FIELD_SEPARATOR = " ";

    @Override
    public void parseFile(File file) {

    }

    private LogEntry getLogEntry(String line) {

        String[] fields = line.split(FIELD_SEPARATOR);

        Date date = Date.from(Instant.ofEpochSecond(Long.parseLong(fields[0])));

        return new LogEntry(date, fields[1], fields[2]);
    }

    Map<Long, LogEntry> readLogEntries(File file) throws IOException {

        TreeMap<Long, LogEntry> logMap = new TreeMap<>();

        List<String> lines = FileUtil.readLines(file);

        for (String line : lines) {

            LogEntry logEntry = this.getLogEntry(line);

            logMap.put(logEntry.getCreatedDate().getTime(), logEntry);
        }

        return logMap;
    }

}
