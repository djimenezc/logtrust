package com.djimenezc.service.parser;

import java.io.File;
import java.io.IOException;

/**
 *
 * Created by david on 10/07/2016.
 */
interface LogParser {

    void parseFile(File file) throws IOException;

    void parseFile(File source, File destination) throws IOException;
}
