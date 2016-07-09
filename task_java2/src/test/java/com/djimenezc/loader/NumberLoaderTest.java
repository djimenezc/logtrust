/**
 * Copyright (C) 2016 David Jimenez.
 */
package com.djimenezc.loader;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class unit tests.
 *
 * @version 1.0.0
 */
public class NumberLoaderTest {

  private FileNumberLoaderImpl loader;

  @Before
  public void setUp() {
    loader = new FileNumberLoaderImpl();
    System.out.println("@Before - setUp");
  }

  @After
  public void tearDown() {
    System.out.println("@After - tearDown");
  }

  @Test
  public void testReadNumbers() throws Exception {

    List<BigDecimal> current = loader.readNumbers("real-numbers-load-test.txt");

    List<Double> expected = new ArrayList<>();
    expected.add(1480156196.31);
    expected.add(1452846823.32);

    Assert.assertEquals(expected, current);
  }
}
