package com.djimenezc.service.parser;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

/**
 * Test to verify that the log parser is sorting the lines out
 * Created by david on 10/07/2016.
 */
public class LogParserServiceTest extends AbstractLogParserTest {

    private LogParserServiceImpl logParserService;

    @Before
    public void setUp() throws IOException {

        System.out.println("@Before - setUp");

        this.setupLogGenerator();

        File file = getFile("logsEntries/log-20160710-100000-entries");
        logParserService = new LogParserServiceImpl(file);
    }

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @After
    public void tearDown() {
        System.out.println("@After - tearDown");
    }

    @Test
    public void testConstructor() throws Exception {

        long actualEntries = logParserService.getNumberEntries();
        long expectedEntries = 99840;

        Assert.assertEquals(expectedEntries, actualEntries);
    }

    @Test
    public void testLoadFile() throws Exception {

        File file = getFile("logsEntries/200-entries-file.txt");

        logParserService.loadFile(file);

        long expectedEntries = 200;
        long actualEntries = logParserService.getEntriesMap().size();

        Assert.assertEquals(expectedEntries, actualEntries);
    }

}
