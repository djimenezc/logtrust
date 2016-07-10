package com.djimenezc.service.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to manage files and directories
 * Created by david on 10/07/2016.
 */
public class FileUtil {

    public static File createTempDirectory(String name)
        throws IOException {
        final File temp;

        temp = File.createTempFile(name, Long.toString(System.nanoTime()));

        if (!(temp.delete())) {
            throw new IOException("Could not delete temp file: " + temp.getAbsolutePath());
        }

        if (!(temp.mkdir())) {
            throw new IOException("Could not create temp directory: " + temp.getAbsolutePath());
        }

        return (temp);
    }

    public static List<String> readLines(File file) throws IOException {

        List<String> entryList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line = br.readLine();

            while (line != null) {
                entryList.add(line);
                line = br.readLine();
            }

        }

        return entryList;
    }
}
