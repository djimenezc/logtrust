package com.djimenezc.service.util;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Test to verify that the log parser is sorting the lines out
 * Created by david on 10/07/2016.
 */
public class TimeUtilTest {


    @Before
    public void setUp() throws IOException {
        System.out.println("@Before - setUp");
    }

    @After
    public void tearDown() {
        System.out.println("@After - tearDown");
    }

    @Test
    public void testGetRandomTimestampInterval() throws Exception {

        int seconds = 60;

        for (int i = 0; i < 100; i++) {
            Date date = TimeUtil.getRandomTimestamp(seconds);
            Calendar cal = Calendar.getInstance();
            long now = (new Date()).getTime();
            cal.setTimeInMillis(now - 60000 - 1000L);
            Date endDate = cal.getTime();
            cal.setTimeInMillis(now + 1000L);
            Date startDate = cal.getTime();

            Assert.assertTrue(date + " is not in range from " + startDate + " to " + endDate, TimeUtil.isWithinRange(date, endDate, startDate));
        }
    }

}
