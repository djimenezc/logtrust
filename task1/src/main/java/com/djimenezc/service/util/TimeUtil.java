package com.djimenezc.service.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Class to manage timestamps and dates
 * Created by david on 10/07/2016.
 */
public class TimeUtil {

    /**
     * Return a random date since 2012 to nowadays
     * @return random date
     */
    public static Date getRandomTimestamp() {

        Random r = new Random();
        long unixTime = (long) (1293861599 + r.nextDouble() * 60 * 60 * 24 * 365);

        return new Date(unixTime);
    }

    /**
     * Return a date between now and <code>seconds</code> ago
     *
     * @param seconds interval of seconds to return the date
     * @return date
     */
    public static Date getRandomTimestamp(int seconds) {

        Date max = new Date();
        Calendar cal = Calendar.getInstance();

        cal.setTimeInMillis(max.getTime() - secondsToMs(seconds));
        Date min = cal.getTime();

        Random r = new Random();

        long unixTime = r.nextInt((int) (max.getTime() - min.getTime())) + min.getTime();

        return new Date(unixTime);
    }

    private static long secondsToMs(int seconds) {
        return seconds * 1000;
    }

    static boolean isWithinRange(Date testDate, Date startDate, Date endDate) {
        return !(testDate.before(startDate) || testDate.after(endDate));
    }
}
