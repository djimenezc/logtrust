package com.djimenezc.service.parser;

import com.djimenezc.service.entities.LogEntry;
import com.djimenezc.service.generator.LogFileGenerator;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

/**
 * Test to verify that the log parser is sorting the lines out
 * Created by david on 10/07/2016.
 */
public class LogParserTest {

    private static final int ONE_HOUR = 3600;
    private LogParserHelperImpl logParser;
    private LogFileGenerator logGenerator;

    @Before
    public void setUp() throws IOException {
        System.out.println("@Before - setUp");
        logParser = new LogParserHelperImpl();
        List<String> connectedHosts = new ArrayList<>();
        connectedHosts.add("connected1");
        connectedHosts.add("connected2");
        List<String> receivedHosts = new ArrayList<>();
        receivedHosts.add("received1");
        receivedHosts.add("received2");
        receivedHosts.add("received3");
        logGenerator = new LogFileGenerator(null, connectedHosts, receivedHosts);
    }

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @After
    public void tearDown() {
        System.out.println("@After - tearDown");
    }

    @Test
    public void testParseMediumFileSize() throws Exception {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resourceSource = classLoader.getResource("logsEntries/log1.txt");
        URL resourceExpected = classLoader.getResource("logsEntries/log1-sorted.txt");

        assert resourceSource != null;
        final File source = new File(resourceSource.getFile());
        assert resourceExpected != null;
        final File expected = new File(resourceExpected.getFile());
        final File output = folder.newFile("xyz.txt");

        logParser.parseFile(source, output);

        Assert.assertEquals(FileUtils.readLines(expected), FileUtils.readLines(output));
    }

    @Test
    public void testParseOneFileParam() throws Exception {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resourceSource = classLoader.getResource("logsEntries/log1.txt");
        URL resourceExpected = classLoader.getResource("logsEntries/log1-sorted.txt");

        assert resourceSource != null;
        final File source = new File(resourceSource.getFile());
        assert resourceExpected != null;
        final File expected = new File(resourceExpected.getFile());

        logParser.parseFile(source);

        Assert.assertEquals(FileUtils.readLines(expected), FileUtils.readLines(source));
    }

    @Test
    public void testReadLogEntries() throws Exception {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("logsEntries/200-entries-file.txt");
        File file = resource != null ? new File(resource.getFile()) : null;

        if (file != null) {

            Map<Long, LogEntry> logEntries = logParser.readLogEntries(file);

            long expectedSize = 200;
            long actualSize = logEntries.size();

            Assert.assertEquals(expectedSize, actualSize);
        } else {
            throw new Exception("File not found");
        }
    }

    private File getFileSortedEntries() {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("logsEntries/log-20160710-200-entries-sorted.txt");
        return resource != null ? new File(resource.getFile()) : null;
    }

    @Test
    public void testGetConnectedHostList() throws Exception {


        List<String> connectedHosts = new ArrayList<>();
        connectedHosts.add("connected1");
        connectedHosts.add("connected2");

        Map<Long, LogEntry> map = generateRecentLogEntries();
        addEntryOutOfRange(map);

        Assert.assertEquals(connectedHosts, logParser.getConnectedHostList(ONE_HOUR, "connected1", map));

    }

    @Test
    public void testGetReceivedHostList() throws Exception {

        List<String> receivedHosts = new ArrayList<>();
        receivedHosts.add("received1");
        receivedHosts.add("received2");
        receivedHosts.add("received3");

        Map<Long, LogEntry> map = generateRecentLogEntries();
        addEntryOutOfRange(map);

        Assert.assertEquals(receivedHosts, logParser.getReceivedHostList(ONE_HOUR, "received1", map));

    }

    private void addEntryOutOfRange(Map<Long, LogEntry> map) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        LogEntry logEntry = new LogEntry(cal.getTime(), "ass", "sdd");
        map.put(logEntry.getCreatedDate().getTime(), logEntry);
    }

    private Map<Long, LogEntry> generateRecentLogEntries() {

        Map<Long, LogEntry> map = new TreeMap<>();

        IntStream.range(0, 50)
            .forEach(value -> {
                LogEntry logEntry = logGenerator.getRandomEntry(1000);
                map.put(logEntry.getCreatedDate().getTime(), logEntry);
            });

        return map;
    }

    @Test
    public void testGetHostMostConnections() throws Exception {

        File file = getFileSortedEntries();

        if (file != null) {

            Map<Long, LogEntry> map = logParser.readLogEntries(file);

            map.forEach((k, v) -> v.setCreated(new Date()));
            String expected = "connected2";

            Assert.assertEquals(expected, logParser.getHostMostConnections(ONE_HOUR, map));

        } else {
            throw new Exception("File not found");
        }
    }

    @Test
    public void testGetHostMostConnectionsNoEntries() throws Exception {

        Map<Long, LogEntry> map = new HashMap<>();

        Assert.assertEquals(null, logParser.getHostMostConnections(ONE_HOUR, map));

    }
}
