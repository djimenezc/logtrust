package com.djimenezc.processor.summarize;

import java.util.List;

/**
 * Solution that iterates through the array and sum the numbers
 * sequentially
 * Created by david on 09/07/2016.
 */
class SequentialSummarizer extends AbstractSummarizer<Double> {

  SequentialSummarizer(String name) {
    super(name);
  }

  @Override
  Double runSummarizer(List<Double> numbers) {

    Double sum = 0D;

    for (Double number : numbers) {
      sum += number;
    }

    return sum;
  }

}
