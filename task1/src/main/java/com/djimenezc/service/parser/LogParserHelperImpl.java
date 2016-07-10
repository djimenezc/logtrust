package com.djimenezc.service.parser;

import com.djimenezc.service.entities.LogEntry;
import com.djimenezc.service.util.FileUtil;
import com.djimenezc.service.util.TimeUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;

/**
 * Implement log parser that sort out the lines of file
 * <p>
 * Created by david on 10/07/2016.
 */
class LogParserHelperImpl implements LogParserHelper {

    private static final java.lang.String FIELD_SEPARATOR = " ";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private Date now;
    private Date endDate;

    @Override
    public void parseFile(File file) throws IOException {

        this.parseFile(file, file);
    }

    @Override
    public void parseFile(File source, File destination) throws IOException {

        Map<Long, LogEntry> map = this.readLogEntries(source);

        this.writeEntriesInFile(destination, map);
    }

    private void calculateRangeDate(int seconds) {

        Calendar cal = Calendar.getInstance();
        this.now = new Date();
        long nowLong = (now).getTime();
        cal.setTimeInMillis(nowLong - TimeUtil.secondsToMs(seconds));
        this.endDate = cal.getTime();
    }

    @Override
    public List<String> getConnectedHostList(int seconds, String host, Map<Long, LogEntry> entries) {

        this.calculateRangeDate(seconds);

        return entries.values().stream()
            .filter(logEntry -> TimeUtil.isWithinRange(logEntry.getCreatedDate(), this.endDate, this.now) && logEntry.getDestinationHost().equals(host)
            )
            .map(LogEntry::getSourceHost)
            .distinct()
            .sorted()
            .collect(Collectors.toList());
    }

    @Override
    public List<String> getReceivedHostList(int seconds, String host, Map<Long, LogEntry> entries) {

        this.calculateRangeDate(seconds);

        return entries.values().stream()
            .filter(logEntry -> TimeUtil.isWithinRange(logEntry.getCreatedDate(), this.endDate, this.now) && logEntry.getSourceHost().equals(host)
            )
            .map(LogEntry::getDestinationHost)
            .distinct()
            .sorted()
            .collect(Collectors.toList());
    }

    @Override
    public String getHostMostConnections(int seconds, Map<Long, LogEntry> entries) {

        this.calculateRangeDate(seconds);

        Optional<Map.Entry<String, Long>> result = entries.values().stream()
            .filter(logEntry -> TimeUtil.isWithinRange(logEntry.getCreatedDate(), this.endDate, this.now)
            )
            .collect(Collectors.groupingBy(LogEntry::getSourceHost, counting()))
            .entrySet()
            .stream()
            .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1);

        if (result.isPresent())
            return result.get().getKey();
        return null;
    }

    private void writeEntriesInFile(File destination, Map<Long, LogEntry> map) throws IOException {

        PrintWriter printWriter = new PrintWriter(new FileWriter(destination));

        for (LogEntry entry : map.values()) {

            printWriter.write(entry.getEntryString() + LINE_SEPARATOR);
        }

        printWriter.close();
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
