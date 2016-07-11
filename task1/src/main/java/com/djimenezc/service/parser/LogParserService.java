package com.djimenezc.service.parser;

import com.djimenezc.service.entities.MultipleLogEntry;
import com.djimenezc.service.entities.SingleLogEntry;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Log entries parser service. It is used to manage log entries and the order in a file
 * <p>
 * Created by david on 10/07/2016.
 */
public interface LogParserService {

    /**
     * Load a file of log entries in the service
     *
     * @param file file to sort entries
     * @throws IOException
     */
    void loadFile(File file) throws IOException;

    /**
     * Sort out the log entries of <code>file<code>
     *
     * @param file file to sort entries
     * @return map list of log entries sorted by date
     * @throws IOException
     */
    Map<Long, MultipleLogEntry> parseFile(File file) throws IOException;

    /**
     * List of hostname connected to <code>host<code> during the last <code>seconds<code>
     *
     * @param host    connected host
     * @return list of hosts
     */
    List<String> getConnectedHostList(String host);

    /**
     * List of hostname received connections from <code>host<code> during the last <code>seconds<code>
     *
     * @param host    received host
     * @return list of hosts
     */
    List<String> getReceivedHostList(String host);

    /**
     * Return the hostname of the machine that has generated most connections
     * in the last seconds
     *
     * @return String hostname
     */
    String getHostMostConnections();

    /**
     * Return the number of single log entries loaded
     * @return long
     */
    long getNumberEntries();

    SingleLogEntry addLogEntry(String line);
}
