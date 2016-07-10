package com.djimenezc.service.parser;

import com.djimenezc.service.entities.LogEntry;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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

    @After
    public void tearDown() {
        System.out.println("@After - tearDown");
    }

    @Test
    public void testParseMediumFileSize() throws Exception {


    }

    @Test
    public void testReadLogEntries() throws Exception {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("200-entries-file.txt");
        File file = resource != null ? new File(resource.getFile()) : null;

        if(file != null) {

            Map<Long, LogEntry> logEntries = logParser.readLogEntries(file);

            long expectedSize = 200;
            long actualSize = logEntries.size();

            Assert.assertEquals(expectedSize, actualSize);
        }

    }
}
