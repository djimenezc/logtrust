package com.djimenezc.processor.summarize;

import java.math.BigDecimal;
import java.util.List;

/**
 * Solution that use a functional approach (using java streams) to calculate the sum of numbers
 * Created by david on 09/07/2016.
 */
class FunctionalSummarizer extends AbstractSummarizer<BigDecimal> {

  FunctionalSummarizer(String name) {
    super(name);
  }

  @Override
  BigDecimal runSummarizer(List<BigDecimal> numbers) {

    return BigDecimal.valueOf(numbers.stream().mapToDouble(BigDecimal::doubleValue).sum());
  }

}
