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
    private LogParserService logParserService;

    /**
     * Constructor
     *
     * @param logParserService LogParserService
     * @param name             process name
     * @param interval         each how many seconds the process reads the file
     * @throws IOException
     */
    public TailDaemon(LogParserService logParserService, String name, int interval) throws IOException {

        this.setThreadName(name);
        this.setInterval(interval);
        this.keepReading = true;
        this.logParserService = logParserService;
    }

    @Override
    void runTask() throws InterruptedException, IOException {

        File file = this.logParserService.getFile();
        BufferedReader br = new BufferedReader(new FileReader(file));

//        System.out.println("Analysing file" + file.getAbsolutePath());
        String line;

        while (keepReading) {

            line = br.readLine();
            if (line == null) {
                //wait until there is more of the file for us to read
                Thread.sleep(1000);
            } else {
//                System.out.println("Adding new entry in memory " + logParserService.addLogEntry(line));
            }
        }
    }

    @Override
    void stopTask() {
        this.keepReading = false;
    }
}
