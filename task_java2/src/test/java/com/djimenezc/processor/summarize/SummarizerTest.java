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

import java.math.BigDecimal;
import java.util.List;

/**
 * Main class unit tests.
 *
 * @version 1.0.0
 */
public class SummarizerTest {

  private static final BigDecimal SUM_EXPECTED_REAL = BigDecimal.valueOf(113068104771.33);
  private static final BigDecimal SUM_EXPECTED_REAL_BIG_FILE = BigDecimal.valueOf(4522724190853.20).setScale(2);

  private static final BigDecimal SUM_EXPECTED_FEW = BigDecimal.valueOf(100000.00D).setScale(2);
  private static final BigDecimal SUM_EXPECTED_INT = new BigDecimal(460).setScale(1);

  private Summarizer<BigDecimal> summarizer;
  private List realNumberList;
  private List bigNumberList;
  private List intNumberList;
  private List fewNumberList;


  @Before
  public void setUp() throws Exception {
    System.out.println("@Before - setUp");
    NumberLoader loader = new FileNumberLoaderImpl();
    realNumberList = loader.readNumbers("real-numbers.txt");
    intNumberList = loader.readNumbers("int-numbers.txt");
    fewNumberList = loader.readNumbers("few-numbers.txt");
    bigNumberList = loader.readNumbers("real-big-numbers.txt");
  }

  @After
  public void tearDown() {
    summarizer = null;
    System.out.println("@After - tearDown");
  }

  private void checkResult(Summarizer<BigDecimal> summarizer) {

    BigDecimal actualReal = summarizer.sumNumbers(realNumberList);
    BigDecimal actualInt = summarizer.sumNumbers(intNumberList);
    BigDecimal actualFew = summarizer.sumNumbers(fewNumberList);
    BigDecimal actualBig = summarizer.sumNumbers(bigNumberList);

    Assert.assertEquals(SUM_EXPECTED_INT, actualInt);
    Assert.assertEquals(SUM_EXPECTED_FEW, actualFew.setScale(2));
    Assert.assertEquals(SUM_EXPECTED_REAL, actualReal);
    Assert.assertEquals(SUM_EXPECTED_REAL_BIG_FILE, actualBig.setScale(2));
  }

  @Test
  public void testReadNumbersSequential() throws Exception {

    summarizer = new SequentialSummarizer();
    checkResult(summarizer);
  }

  @Test
  public void testReadNumbersRecursive() throws Exception {

    summarizer = new RecursiveSummarizer();
    checkResult(summarizer);
  }

  @Test
  public void testReadNumbersFunctional() throws Exception {

    summarizer = new FunctionalSummarizer();
    checkResult(summarizer);
  }

  @Test
  public void testReadNumbersParallelLambda() throws Exception {

    summarizer = new ParallelLambdaSummarizer();
    checkResult(summarizer);
  }

  @Test
  public void testReadNumbersLambda() throws Exception {

    summarizer = new LambdaSummarizer();
    checkResult(summarizer);
  }

  @Test
  public void testReadNumbersParallelThreads() throws Exception {

    summarizer = new ParallelThreadsSummarizer();
    checkResult(summarizer);
  }

  @Test
  public void testReadNumbersCallableThreads() throws Exception {

    summarizer = new CallableSummarizer();
    BigDecimal actualBig = summarizer.sumNumbers(bigNumberList);

    Assert.assertEquals(SUM_EXPECTED_REAL_BIG_FILE, actualBig.setScale(2));
  }

  @Test
  public void testReadNumbersParallelExecutors() throws Exception {

    summarizer = new ParallelWithExecutorsSummarizer();
    BigDecimal actualReal = summarizer.sumNumbers(realNumberList);
    BigDecimal actualBig = summarizer.sumNumbers(bigNumberList);

    Assert.assertEquals(SUM_EXPECTED_REAL, actualReal);
    Assert.assertEquals(SUM_EXPECTED_REAL_BIG_FILE, actualBig.setScale(2));
  }
}
