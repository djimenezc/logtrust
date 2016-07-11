package com.djimenezc.service.parser;

import com.djimenezc.service.entities.MultipleLogEntry;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Test to verify that the log parser is sorting the lines out
 * Created by david on 10/07/2016.
 */
public class LogParserHelperTest extends  AbstractLogParserTest{

    private LogParserHelperImpl logParser;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        System.out.println("@Before - setUp");
        this.setupLogGenerator();
        logParser = new LogParserHelperImpl();
    }

    @After
    public void tearDown() {
        System.out.println("@After - tearDown");
    }

    @Test
    public void testParseMediumFileSize() throws Exception {

        final File source = getFile("logsEntries/log1.txt");
        final File expected = getFile("logsEntries/log1-sorted.txt");
        final File output = folder.newFile("xyz.txt");

        logParser.parseFile(source, output);

        Assert.assertEquals(FileUtils.readLines(expected), FileUtils.readLines(output));
    }

    @Test
    public void testParseOneFileParam() throws Exception {

        final File source = getFile("logsEntries/log1.txt");
        final File expected = getFile("logsEntries/log1-sorted.txt");

        logParser.parseFile(source);

        Assert.assertEquals(FileUtils.readLines(expected), FileUtils.readLines(source));
    }

    @Test
    public void testReadLogEntries() throws Exception {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("logsEntries/200-entries-file.txt");
        File file = resource != null ? new File(resource.getFile()) : null;

        if (file != null) {

            Map<Long, MultipleLogEntry> logEntries = logParser.readLogEntries(file);

            long expectedSize = 200;
            long actualSize = logEntries.size();

            Assert.assertEquals(expectedSize, actualSize);
        } else {
            throw new Exception("File not found");
        }
    }

    @Test
    public void testGetConnectedHostList() throws Exception {

        List<String> connectedHosts = new ArrayList<>();
        connectedHosts.add("connected1");
        connectedHosts.add("connected2");

        Map<Long, MultipleLogEntry> map = generateRecentLogEntries();
        addEntryOutOfRange(map);

        Assert.assertEquals(connectedHosts, logParser.getConnectedHostList(ONE_HOUR, "received1", map));

    }

    @Test
    public void testGetReceivedHostList() throws Exception {

        List<String> receivedHosts = new ArrayList<>();
        receivedHosts.add("received1");
        receivedHosts.add("received2");
        receivedHosts.add("received3");

        Map<Long, MultipleLogEntry> map = generateRecentLogEntries();
        addEntryOutOfRange(map);

        Assert.assertEquals(receivedHosts, logParser.getReceivedHostList(ONE_HOUR, "connected1", map));

    }

    @Test
    public void testGetHostMostConnectionsOutOfTimeRange() throws Exception {

        File file = getFileSortedEntries();

        if (file != null) {

            Map<Long, MultipleLogEntry> map = logParser.readLogEntries(file);

            map.forEach((k, v) -> v.setCreatedDate(new Date()));

            Assert.assertNull(logParser.getHostMostConnections(ONE_HOUR, map));

        } else {
            throw new Exception("File not found");
        }
    }

    @Test
    public void testGetHostMostConnections() throws Exception {

        File file = getFileSortedEntries();

        if (file != null) {

            Map<Long, MultipleLogEntry> map = generateRecentLogEntries();

            map.forEach((k, v) -> v.setCreatedDate(new Date()));

            Assert.assertNotNull(logParser.getHostMostConnections(ONE_HOUR, map));

        } else {
            throw new Exception("File not found");
        }
    }

    @Test
    public void testGetHostMostConnectionsNoEntries() throws Exception {

        Map<Long, MultipleLogEntry> map = new HashMap<>();

        Assert.assertEquals(null, logParser.getHostMostConnections(ONE_HOUR, map));
    }

}
