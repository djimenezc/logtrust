package com.djimenezc.service.executor;

import com.djimenezc.service.parser.LogParserService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Process to watch a file and sort the entries on the fly
 * Created by david on 11/07/2016.
 */
public class TailDaemon extends AbstractIntervalExecutor {

    private boolean keepReading;
    private final File file;
    private LogParserService logParserService;

    /**
     * Constructor
     *
     * @param file           File
     * @param name           process name
     * @param interval       each how many seconds the process reads the file
     * @throws IOException
     */
    public TailDaemon(File file, String name, int interval) throws IOException {

        this.setThreadName(name);
        this.setInterval(interval);
        this.keepReading = true;
        this.file = file;
    }

    @Override
    void runTask() throws InterruptedException, IOException {

        BufferedReader br = new BufferedReader(new FileReader(this.file));

        String line;

        while (keepReading) {

            line = br.readLine();
            if (line == null) {
                //wait until there is more of the file for us to read
                Thread.sleep(1000);
            }
            else {

                logParserService.addLogEntry(line);
            }
        }
    }

    @Override
    void stopTask() {
        this.keepReading = false;
    }
}
