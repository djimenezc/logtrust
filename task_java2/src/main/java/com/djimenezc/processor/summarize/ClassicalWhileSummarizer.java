package com.djimenezc.processor.summarize;

import java.math.BigDecimal;
import java.util.List;

/**
 * Solution that iterates through the array and sum the numbers
 * sequentially
 * Created by david on 09/07/2016.
 */
class ClassicalWhileSummarizer extends AbstractSummarizer<BigDecimal> {

  ClassicalWhileSummarizer() {
    super("classical");
  }

  @Override
  BigDecimal runSummarizer(List<BigDecimal> numbers) {

    BigDecimal sum = BigDecimal.ZERO;

    int i=0;

    while(i< numbers.size()) {
      sum = sum.add(numbers.get(i++));
    }

    return sum;
  }

}
