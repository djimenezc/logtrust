package com.djimenezc.processor.summarize;

import java.util.List;

/**
 * Solution that iterates through the array and sum the numbers
 * sequentially
 * Created by david on 09/07/2016.
 */
class RecursiveSummarizer extends AbstractSummarizer<Double> {

  RecursiveSummarizer(String name) {
    super(name);
  }

  @Override
  Double runSummarizer(List<Double> numbers) {

    return numbers.isEmpty() ? 0D : numbers.get(0) + runSummarizer(numbers.subList(1, numbers.size()));
  }

}
