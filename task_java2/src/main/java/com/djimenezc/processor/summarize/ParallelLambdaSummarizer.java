package com.djimenezc.processor.summarize;

import java.math.BigDecimal;
import java.util.List;

/**
 * Solution that use a functional approach (using java streams and the reduce function with lambdas) to calculate the sum of numbers
 * Created by david on 09/07/2016.
 */
class ParallelLambdaSummarizer extends AbstractSummarizer<BigDecimal> {

  ParallelLambdaSummarizer(String name) {
    super(name);
  }

  @Override
  BigDecimal runSummarizer(List<BigDecimal> numbers) {

    return numbers.parallelStream().reduce(BigDecimal.ZERO, BigDecimal::add);
  }

}
