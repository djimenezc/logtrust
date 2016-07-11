package com.djimenezc.service.executor;

import com.djimenezc.service.generator.LogFileGenerator;
import com.djimenezc.service.generator.LogGenerator;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Random log generator that can be run as a single thread
 * <p>
 * Created by david on 11/07/2016.
 */
public class LogGeneratorDaemon extends AbstractIntervalExecutor {

    private LogGenerator logFileGenerator;

    /**
     * Constructor
     *
     * @param file           File
     * @param name           process name
     * @param interval       each how many seconds the process writes in the file
     * @param connectedHosts list of possible values of the connected hosts
     * @param receivedHosts  list of possible values of the received hosts
     * @throws IOException
     */
    public LogGeneratorDaemon(File file, String name, int interval, List<String> connectedHosts, List<String> receivedHosts) throws IOException {

        this.setThreadName(name);
        this.setInterval(interval);

        logFileGenerator = new LogFileGenerator(file, connectedHosts, receivedHosts);
    }

    @Override
    void runTask() {
        System.out.println(logFileGenerator.writeEntry(10000));
    }

    @Override
    void stopTask() {
        logFileGenerator.closeStream();
    }
}
