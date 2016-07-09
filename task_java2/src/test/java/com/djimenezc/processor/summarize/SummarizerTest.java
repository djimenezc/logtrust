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

  private static final BigDecimal SUM_EXPECTED_FEW = new BigDecimal(100000.00D).setScale(2);
  private static final BigDecimal SUM_EXPECTED_INT = new BigDecimal(460).setScale(1);

  private Summarizer<BigDecimal> summarizer;
  private List numberList;
  private List intNumberList;
  private List fewNumberList;

  @Before
  public void setUp() throws Exception {
    System.out.println("@Before - setUp");
    NumberLoader loader = new FileNumberLoaderImpl();
    numberList = loader.readNumbers("real-numbers.txt");
    intNumberList = loader.readNumbers("int-numbers.txt");
    fewNumberList = loader.readNumbers("few-numbers.txt");
  }

  @After
  public void tearDown() {
    System.out.println("@After - tearDown");
  }

  @Test
  public void testReadNumbersSequential() throws Exception {

    summarizer = new SequentialSummarizer("Sequential");
    BigDecimal actualReal = summarizer.sumNumbers(numberList);
    BigDecimal actualInt = summarizer.sumNumbers(intNumberList);
    BigDecimal actualFew = summarizer.sumNumbers(fewNumberList);

    Assert.assertEquals(SUM_EXPECTED_INT, actualInt);
    Assert.assertEquals(SUM_EXPECTED_FEW, actualFew);
    Assert.assertEquals(SUM_EXPECTED_REAL, actualReal);
  }

  @Test
  public void testReadNumbersRecursive() throws Exception {

    summarizer = new RecursiveSummarizer("Recursive");
    BigDecimal actualReal = summarizer.sumNumbers(numberList);
    BigDecimal actualInt = summarizer.sumNumbers(intNumberList);
    BigDecimal actualFew = summarizer.sumNumbers(fewNumberList);

    Assert.assertEquals(SUM_EXPECTED_INT, actualInt);
    Assert.assertEquals(SUM_EXPECTED_FEW, actualFew);
    Assert.assertEquals(SUM_EXPECTED_REAL, actualReal);
  }

  @Test
  public void testReadNumbersFunctional() throws Exception {

    summarizer = new FunctionalSummarizer("Functional");
    BigDecimal actualReal = summarizer.sumNumbers(numberList);
    BigDecimal actualInt = summarizer.sumNumbers(intNumberList);
    BigDecimal actualFew = summarizer.sumNumbers(fewNumberList).setScale(2);

    Assert.assertEquals(SUM_EXPECTED_INT, actualInt);
    Assert.assertEquals(SUM_EXPECTED_FEW, actualFew);
    Assert.assertEquals(SUM_EXPECTED_REAL, actualReal);
  }

  @Test
  public void testReadNumbersParallelLambda() throws Exception {

    summarizer = new ParallelLambdaSummarizer("ParallelLambda");
    BigDecimal actualReal = summarizer.sumNumbers(numberList);
    BigDecimal actualInt = summarizer.sumNumbers(intNumberList);
    BigDecimal actualFew = summarizer.sumNumbers(fewNumberList);

    Assert.assertEquals(SUM_EXPECTED_INT, actualInt);
    Assert.assertEquals(SUM_EXPECTED_FEW, actualFew);
    Assert.assertEquals(SUM_EXPECTED_REAL, actualReal);
  }

  @Test
  public void testReadNumbersLambda() throws Exception {

    summarizer = new LambdaSummarizer("Lambda");
    BigDecimal actualReal = summarizer.sumNumbers(numberList);
    BigDecimal actualInt = summarizer.sumNumbers(intNumberList);
    BigDecimal actualFew = summarizer.sumNumbers(fewNumberList);

    Assert.assertEquals(SUM_EXPECTED_INT, actualInt);
    Assert.assertEquals(SUM_EXPECTED_FEW, actualFew);
    Assert.assertEquals(SUM_EXPECTED_REAL, actualReal);
  }
}
