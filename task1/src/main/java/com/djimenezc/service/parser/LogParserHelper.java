package com.djimenezc.service.parser;

import com.djimenezc.service.entities.MultipleLogEntry;
import com.djimenezc.service.entities.SingleLogEntry;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Log entries parser
 * <p>
 * Created by david on 10/07/2016.
 */
interface LogParserHelper {

    /**
     * Sort out the log entries of <code>file<code>
     *
     * @param file file to sort entries
     * @throws IOException
     */
    Map<Long, MultipleLogEntry> parseFile(File file) throws IOException;

    /**
     * Read log entries from the <code>source<code> file and stores the entries
     * sorted by timestamp in <code>destination<code>
     *
     * @param source      file to sort entries
     * @param destination file to write the result list of entries
     * @throws IOException
     */
    Map<Long, MultipleLogEntry> parseFile(File source, File destination) throws IOException;

    /**
     * List of hostname connected to <code>host<code> during the last <code>seconds<code>
     *
     * @param seconds interval of time to search
     * @param host    connected host
     * @param entries entries to examine
     * @return list of hosts
     */
    List<String> getConnectedHostList(int seconds, String host, Map<Long, MultipleLogEntry> entries);

    /**
     * List of hostname received connections from <code>host<code> during the last <code>seconds<code>
     *
     * @param host    received host
     * @param entries entries to examine
     * @return list of hosts
     */
    List<String> getReceivedHostList(int seconds, String host, Map<Long, MultipleLogEntry> entries);

    /**
     * Return the hostname of the machine that has generated most connections
     * in the last seconds
     *
     * @param seconds interval of time to search
     * @param entries entries to examine
     * @return String hostname
     */
    String getHostMostConnections(int seconds, Map<Long, MultipleLogEntry> entries);

    SingleLogEntry getLogEntry(String line);

    Map<Long, MultipleLogEntry> readLogEntries(File file) throws IOException;
}
