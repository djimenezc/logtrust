/**
 * Copyright (C) 2016 David Jimenez.
 */
package com.djimenezc.service.manager;

import com.djimenezc.service.parser.LogParserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;

/**
 * Main class unit tests.
 *
 * @version 1.0.0
 */
public class LogManagerTest {

    private LogManager manager;
    private ClassLoader classLoader;
    private File file;

    @Before
    public void setUp() throws IOException {

        System.out.println("@Before - setUp");
        classLoader = getClass().getClassLoader();
        this.file = getFile("logsEntries/log-20160710-200-entries.txt");
        manager = new LogManagerImpl(this.file);

        deleteFileContent();
    }

    /**
     * Return the file specific by <code>path</code>
     *
     * @param path url to the file
     * @return file
     */
    private File getFile(String path) {
        URL resourceSource = classLoader.getResource(path);

        assert resourceSource != null;
        return new File(resourceSource.getFile());
    }

    private void deleteFileContent() throws FileNotFoundException {

        PrintWriter writer = new PrintWriter(this.file);
        writer.print("");
        writer.close();
    }

    private long getFileNumberLines() throws IOException {

        LineNumberReader lnr = new LineNumberReader(new FileReader(file));
        //noinspection ResultOfMethodCallIgnored
        lnr.skip(Long.MAX_VALUE);
        lnr.close();

        return lnr.getLineNumber() + 1;
    }

    @After
    public void tearDown() {
        System.out.println("@After - tearDown");
    }

    @Test(timeout = 10000)
    public void startLogDaemonGenerator() throws Exception {

        System.out.println("File path: " + this.file.getAbsolutePath());
        Assert.assertEquals(1L, getFileNumberLines());

        manager.startLogDaemonGenerator();

        Thread.sleep((long) 4000);

        manager.stopThreads();

        System.out.println("Lines added in the file " + getFileNumberLines());

        Assert.assertTrue(getFileNumberLines() > 1L);
    }

    @Test(timeout = 10000)
//    @Test
    public void defaultStart() throws Exception {

        LogManager manager1 = new LogManagerImpl();

        Thread.sleep((long) 6000);

        verifyLogMetrics(manager1.getLogParserService());

        manager1.stopThreads();
    }

    @Test(timeout = 10000)
//    @Test
    public void startLogAnalysis() throws Exception {

        Assert.assertEquals(1L, getFileNumberLines());

        manager.startLogDaemonGenerator();
        manager.startTailDaemonGenerator();

        Thread.sleep((long) 5000);

        LogParserService logParserService = manager.getLogParserService();

        manager.stopThreads();

        verifyLogMetrics(logParserService);

        System.out.println("Lines added in the file " + getFileNumberLines());

        Assert.assertTrue(getFileNumberLines() > 1L);
    }

    private void verifyLogMetrics(LogParserService logParserService) {

        Assert.assertNotEquals(0, logParserService.getEntriesMap().size());

        Assert.assertNotNull(logParserService.getHostMostConnections());

        List<String> connectedHostList = logParserService.getConnectedHostList("received1");
        Assert.assertNotEquals(0, connectedHostList.size());

        List<String> receivedHostList = logParserService.getReceivedHostList("connected1");
        Assert.assertNotEquals(0, receivedHostList.size());
    }

}
