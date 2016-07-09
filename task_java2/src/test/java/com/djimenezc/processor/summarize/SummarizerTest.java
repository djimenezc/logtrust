/**
 * Copyright (C) 2016 David Jimenez.
 */
package com.djimenezc.processor.summarize;

import com.djimenezc.loader.FileNumberLoaderImpl;
import com.djimenezc.loader.NumberLoader;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Main class unit tests.
 *
 * @version 1.0.0
 */
public class SummarizerTest {

  private Summarizer<Double> summarizer;
  private List<Double> numberList;

  private static final Double SUM_EXPECTED = 1.1306810477132999E11;

  @Before
  public void setUp() throws Exception {
    System.out.println("@Before - setUp");
    NumberLoader loader = new FileNumberLoaderImpl();
    numberList = loader.readNumbers("real-numbers.txt");
  }

  @After
  public void tearDown() {
    System.out.println("@After - tearDown");
  }

  @Test
  public void testReadNumbersSequential() throws Exception {

    summarizer = new SequentialSummarizer("Sequential");
    Double actual = summarizer.sumNumbers(numberList);

    Assert.assertEquals(SUM_EXPECTED, actual);
  }

  @Test
  public void testReadNumbersRecursive() throws Exception {

    summarizer = new RecursiveSummarizer("Recursive");
    Double actual = summarizer.sumNumbers(numberList);

    Assert.assertEquals(SUM_EXPECTED, actual);
  }
}
