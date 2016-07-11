package com.djimenezc.service.parser;

import com.djimenezc.service.entities.MultipleLogEntry;
import com.djimenezc.service.entities.SingleLogEntry;
import com.djimenezc.service.util.FileUtil;
import com.djimenezc.service.util.TimeUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
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
    public Map<Long, MultipleLogEntry> parseFile(File file) throws IOException {

        return this.parseFile(file, file);
    }

    @Override
    public Map<Long, MultipleLogEntry> parseFile(File source, File destination) throws IOException {

        Map<Long, MultipleLogEntry> map = this.readLogEntries(source);

        this.writeEntriesInFile(destination, map);

        return map;
    }

    private void calculateRangeDate(int seconds) {

        Calendar cal = Calendar.getInstance();
        this.now = new Date();
        long nowLong = (now).getTime();
        cal.setTimeInMillis(nowLong - TimeUtil.secondsToMs(seconds));
        this.endDate = cal.getTime();
    }

    private List<SingleLogEntry> getListSingleLogEntries(Map<Long, MultipleLogEntry> entries) {

        //Keep the insertion order
        List<SingleLogEntry> singleLogEntries = new LinkedList<>();

        for (MultipleLogEntry multipleLogEntry : entries.values()) {
            singleLogEntries.addAll(multipleLogEntry.getLogEntries());
        }

        return singleLogEntries;
    }

    @Override
    public List<String> getConnectedHostList(int seconds, String host, Map<Long, MultipleLogEntry> entries) {

        this.calculateRangeDate(seconds);

        List<SingleLogEntry> singleLogEntries = getListSingleLogEntries(entries);

        return singleLogEntries.stream()
            .filter(logEntry -> TimeUtil.isWithinRange(logEntry.getCreatedDate(), this.endDate, this.now) && logEntry.getDestinationHost().equals(host)
            )
            .map(SingleLogEntry::getSourceHost)
            .distinct()
            .sorted()
            .collect(Collectors.toList());
    }

    @Override
    public List<String> getReceivedHostList(int seconds, String host, Map<Long, MultipleLogEntry> entries) {

        this.calculateRangeDate(seconds);

        List<SingleLogEntry> singleLogEntries = getListSingleLogEntries(entries);

        return singleLogEntries.stream()
            .filter(logEntry -> TimeUtil.isWithinRange(logEntry.getCreatedDate(), this.endDate, this.now) && logEntry.getSourceHost().equals(host)
            )
            .map(SingleLogEntry::getDestinationHost)
            .distinct()
            .sorted()
            .collect(Collectors.toList());
    }

    @Override
    public String getHostMostConnections(int seconds, Map<Long, MultipleLogEntry> entries) {

        this.calculateRangeDate(seconds);

        List<SingleLogEntry> singleLogEntries = getListSingleLogEntries(entries);

        Optional<Map.Entry<String, Long>> result = singleLogEntries.stream()
            .filter(logEntry -> TimeUtil.isWithinRange(logEntry.getCreatedDate(), this.endDate, this.now)
            )
            .collect(Collectors.groupingBy(SingleLogEntry::getSourceHost, counting()))
            .entrySet()
            .stream()
            .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1);

        if (result.isPresent())
            return result.get().getKey();
        return null;
    }

    private void writeEntriesInFile(File destination, Map<Long, MultipleLogEntry> map) throws IOException {

        PrintWriter printWriter = new PrintWriter(new FileWriter(destination));

        for (MultipleLogEntry multipleLogEntry : map.values()) {

            for (SingleLogEntry entry : multipleLogEntry.getLogEntries()) {

                printWriter.write(entry.getEntryString() + LINE_SEPARATOR);
            }
        }

        printWriter.close();
    }

    @Override
    public SingleLogEntry getLogEntry(String line) {

        String[] fields = line.split(FIELD_SEPARATOR);

        Date date = Date.from(Instant.ofEpochSecond(Long.parseLong(fields[0])));

        return new SingleLogEntry(date, fields[1], fields[2]);
    }

    @Override
    public Map<Long, MultipleLogEntry> readLogEntries(File file) throws IOException {

        TreeMap<Long, MultipleLogEntry> logMap = new TreeMap<>();

        List<String> lines = FileUtil.readLines(file);

        for (String line : lines) {

            SingleLogEntry logEntry = this.getLogEntry(line);
            Long key = logEntry.getCreatedDate().getTime();
            MultipleLogEntry multipleLogEntry = getMultipleLogEntry(logMap, logEntry, key);

            logMap.put(key, multipleLogEntry);
        }

        return logMap;
    }

    @Override
    public MultipleLogEntry getMultipleLogEntry(TreeMap<Long, MultipleLogEntry> logMap, SingleLogEntry logEntry, Long key) {

        MultipleLogEntry multipleLogEntry;

        if (logMap.get(key) == null) {
            multipleLogEntry = new MultipleLogEntry(logEntry);
        } else {
            multipleLogEntry = logMap.get(key);
            multipleLogEntry.addLogEntry(logEntry);
        }

        return multipleLogEntry;
    }

}
