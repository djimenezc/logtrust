package com.djimenezc.service.util;

import java.util.Date;
import java.util.Random;

/**
 * Class to help managing timestamps and dates
 * Created by david on 10/07/2016.
 */
public class TimeUtil {

    public static Date getRandomTimestamp() {

        Random r = new Random();
        long unixtime = (long) (1293861599 + r.nextDouble() * 60 * 60 * 24 * 365);

        return new Date(unixtime);
    }
}
