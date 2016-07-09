package com.djimenezc.processor.summarize;

import java.math.BigDecimal;
import java.util.List;

/**
 * Solution that use recursion to calculate the sum of the list numbers
 * Created by david on 09/07/2016.
 */
class RecursiveSummarizer extends AbstractSummarizer<BigDecimal> {

  RecursiveSummarizer(String name) {
    super(name);
  }

  @Override
  BigDecimal runSummarizer(List<BigDecimal> numbers) {

    return numbers.isEmpty() ? BigDecimal.ZERO : runSummarizer(numbers.subList(1, numbers.size())).add(numbers.get(0));
  }

}
