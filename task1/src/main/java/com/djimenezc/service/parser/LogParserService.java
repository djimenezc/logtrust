package com.djimenezc.service.parser;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Log entries parser service.
 *
 * Created by david on 10/07/2016.
 */
interface LogParserService {

    /**
     * Sort out the log entries
     *
     * @throws IOException
     */
    void parseFile() throws IOException;

    /**
     * Sort out the log entries of <code>file<code>
     *
     * @param file file to sort entries
     * @throws IOException
     */
    void parseFile(File file) throws IOException;

    /**
     * List of hostname connected to <code>host<code> during the last <code>seconds<code>
     *
     * @param seconds interval of time to search
     * @param host    connected host
     * @return list of hosts
     */
    List<String> getConnectedHostList(int seconds, String host);

    /**
     * List of hostname received connections from <code>host<code> during the last <code>seconds<code>
     *
     * @param seconds interval of time to search
     * @param host    received host
     * @return list of hosts
     */
    List<String> getReceivedHostList(int seconds, String host);

    /**
     * Return the hostname of the machine that has generated most connections
     * in the last seconds
     *
     * @param seconds interval of time to search
     * @return String hostname
     */
    String getHostMostConnections(int seconds);
}
