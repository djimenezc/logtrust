package com.djimenezc.service.parser;

import com.djimenezc.service.entities.LogEntry;
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
import java.util.Map;

/**
 * Test to verify that the log parser is sorting the lines out
 * Created by david on 10/07/2016.
 */
public class LogParserTest {

    private LogParserImpl logParser;

    @Before
    public void setUp() throws IOException {
        System.out.println("@Before - setUp");
        logParser = new LogParserImpl();
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
    public void testReadLogEntries() throws Exception {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("200-entries-file.txt");
        File file = resource != null ? new File(resource.getFile()) : null;

        if (file != null) {

            Map<Long, LogEntry> logEntries = logParser.readLogEntries(file);

            long expectedSize = 200;
            long actualSize = logEntries.size();

            Assert.assertEquals(expectedSize, actualSize);
        }

    }
}
