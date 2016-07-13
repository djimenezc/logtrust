package com.djimenezc.processor.summarize;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.DoubleAdder;

/**
 * Solution that iterates through the array and sum the numbers
 * sequentially
 * Created by david on 09/07/2016.
 */
class ForEachSummarizer extends AbstractSummarizer<BigDecimal> {

  ForEachSummarizer() {
    super("foreach");
  }

  @Override
  BigDecimal runSummarizer(List<BigDecimal> numbers) {

    DoubleAdder longAdder = new DoubleAdder();

    numbers.stream().map(BigDecimal::doubleValue).forEach(longAdder::add);

    return BigDecimal.valueOf(longAdder.doubleValue());
  }

}
