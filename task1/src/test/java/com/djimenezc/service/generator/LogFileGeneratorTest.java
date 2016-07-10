/**
 * Copyright (C) 2016 David Jimenez.
 */
package com.djimenezc.service.generator;

import com.djimenezc.service.entities.LogEntry;
import com.djimenezc.service.util.FileUtil;
import com.djimenezc.service.util.TimeUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class unit tests.
 *
 * @version 1.0.0
 */
public class LogFileGeneratorTest {

    private LogFileGenerator generator;

    @Before
    public void setUp() throws IOException {
        System.out.println("@Before - setUp");
    }

    @After
    public void tearDown() {
        System.out.println("@After - tearDown");
    }

    @Test
    public void testWriteRandomLogEntries() throws Exception {

        File logFile = File.createTempFile("writeRandomLogEntries.log", ".tmp");

        generator = new LogFileGenerator(logFile);

        for(int i =0 ; i < 100; i++) {

            LogEntry logEntry1 = new LogEntry(TimeUtil.getRandomTimestamp(), "source3", "destination2");
            LogEntry logEntry2 = new LogEntry(TimeUtil.getRandomTimestamp(), "source1", "destination2");

            generator.addLogEntry(logEntry1.getEntryString());
            generator.addLogEntry(logEntry2.getEntryString());
        }

        generator.closeStream();

        int expectedNumberEntries = 200;
        int actual = FileUtil.readLines(logFile).size();

        Assert.assertEquals(expectedNumberEntries, actual);
    }

    @Test
    public void testGenerateRandomEntry() throws Exception {

        File logFile = File.createTempFile("GenerateRandomEntry.log", ".tmp");

        List<String> connectedHosts = new ArrayList<>();
        connectedHosts.add("connected1");
        connectedHosts.add("connected2");
        connectedHosts.add("connected3");
        List<String> receivedHosts = new ArrayList<>();
        receivedHosts.add("received1");
        receivedHosts.add("received2");
        receivedHosts.add("received3");

        generator = new LogFileGenerator(logFile, connectedHosts, receivedHosts);

        for(int i=0; i < 200; i++) {
            generator.generateRandomEntry(60);
        }
    }
}
