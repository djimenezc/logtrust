package com.djimenezc.processor.summarize;

import java.util.List;

/**
 * Common functionality to the summarizer, the time that the
 * implementation takes to perform the sum is display on the console
 * Created by david on 09/07/2016.
 */
abstract class AbstractSummarizer<E> implements Summarizer<E> {

  private final String name;

  AbstractSummarizer(String name) {
    this.name = name;
  }

  @Override
  public E sumNumbers(List<E> numbers) {

    long startTime = System.currentTimeMillis();

    E sum = runSummarizer(numbers);

    long stopTime = System.currentTimeMillis();
    long elapsedTime = stopTime - startTime;
    System.out.println(this.name + " takes " + elapsedTime + " ms");

    return sum;
  }

  abstract E runSummarizer(List<E> numbers);
}
