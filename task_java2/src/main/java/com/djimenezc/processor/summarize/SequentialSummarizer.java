package com.djimenezc.processor.summarize;

import java.math.BigDecimal;
import java.util.List;

/**
 * Solution that iterates through the array and sum the numbers
 * sequentially
 * Created by david on 09/07/2016.
 */
class SequentialSummarizer extends AbstractSummarizer<BigDecimal> {

  SequentialSummarizer(String name) {
    super(name);
  }

  @Override
  BigDecimal runSummarizer(List<BigDecimal> numbers) {

    BigDecimal sum = new BigDecimal(0);

    for (BigDecimal number : numbers) {
      sum = sum.add(number);
    }

    return sum;
  }

//  BigDecimal sum = new BigDecimal(0);
//
//  numbers.forEach(sum::add);
//
//  return sum;
}
