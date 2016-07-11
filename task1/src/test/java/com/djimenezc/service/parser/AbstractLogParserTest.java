package com.djimenezc.service.parser;

import com.djimenezc.service.entities.LogEntry;
import com.djimenezc.service.generator.LogFileGenerator;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

/**
 * Class to keep the common code used to test the class in this package
 * Created by david on 11/07/2016.
 */
class AbstractLogParserTest {

    private LogFileGenerator logGenerator;
    private ClassLoader classLoader;
    static final int ONE_HOUR = 3600;

    void setupLogGenerator() throws IOException {

        classLoader = getClass().getClassLoader();

        List<String> connectedHosts = new ArrayList<>();
        connectedHosts.add("connected1");
        connectedHosts.add("connected2");
        List<String> receivedHosts = new ArrayList<>();
        receivedHosts.add("received1");
        receivedHosts.add("received2");
        receivedHosts.add("received3");

        logGenerator = new LogFileGenerator(null, connectedHosts, receivedHosts);
    }

    /**
     * Return the file specific by <code>path</code>
     *
     * @param path url to the file
     * @return file
     */
    File getFile(String path) {
        URL resourceSource = classLoader.getResource(path);

        assert resourceSource != null;
        return new File(resourceSource.getFile());
    }

    /**
     *
     * @return file with entries sorted
     */
     File getFileSortedEntries() {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("logsEntries/log-20160710-200-entries-sorted.txt");
        return resource != null ? new File(resource.getFile()) : null;
    }

    /**
     *
     * @return a map of log entries
     */
    Map<Long, LogEntry> generateRecentLogEntries() {

        Map<Long, LogEntry> map = new TreeMap<>();

        IntStream.range(0, 50)
            .forEach(value -> {
                LogEntry logEntry = logGenerator.getRandomEntry(1000);
                map.put(logEntry.getCreatedDate().getTime(), logEntry);
            });

        return map;
    }

    /**
     *
     * @param map of log entries
     */
    void addEntryOutOfRange(Map<Long, LogEntry> map) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        LogEntry logEntry = new LogEntry(cal.getTime(), "ass", "sdd");
        map.put(logEntry.getCreatedDate().getTime(), logEntry);
    }
}
