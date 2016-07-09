package com.djimenezc.processor.summarize;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Solution that iterates through the array and sum the numbers
 * splitting the input array in several subArray and using the executor service
 * calculate the sum of the partial sum of each subArray
 * <p>
 */
class ParallelWithExecutorsSummarizer extends AbstractSummarizer<BigDecimal> {

  ParallelWithExecutorsSummarizer() {
    super("ParallelWithExecutors");
  }

  private static final int SUB_ARRAY_SIZE = 50;

  @Override
  BigDecimal runSummarizer(List<BigDecimal> numbers) {

    List<List<BigDecimal>> arrayOfSubArrays = splitArray(numbers);
    ConcurrentHashMap<String, BigDecimal> sumMap = new
      ConcurrentHashMap<>(arrayOfSubArrays.size());

    ExecutorService executor = Executors.newFixedThreadPool(
      Runtime.getRuntime().availableProcessors() + 1);

    int i = 1;
    for (List<BigDecimal> subArray : arrayOfSubArrays) {
      String arrayName = "Array " + i;
      executor.execute(
        new ArraySumCalculator(sumMap, subArray, arrayName));
      i++;
    }
    executor.shutdown();

    try {
      executor.awaitTermination(60, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    return calculateTotalSum(sumMap);
  }

  private BigDecimal calculateTotalSum(ConcurrentHashMap<String, BigDecimal> sumMap) {

    return sumMap.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private List<List<BigDecimal>> splitArray(List<BigDecimal> numbers) {

    List<List<BigDecimal>> array = new ArrayList<>();
    int end;

    for (int i = 0; i < numbers.size(); i += SUB_ARRAY_SIZE) {
      end = calculateEndValue(i, numbers);
      array.add(numbers.subList(i, end));
    }

    return array;
  }

  private int calculateEndValue(int start, List<BigDecimal> numbers) {

    return numbers.size() < SUB_ARRAY_SIZE ? numbers.size() : start + SUB_ARRAY_SIZE;
  }

  private BigDecimal sum(List<BigDecimal> numbers) {

    return numbers.stream()
      .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private class ArraySumCalculator implements Runnable {

    ConcurrentHashMap<String, BigDecimal> sumMap;
    List<BigDecimal> numbers;
    String arrayName;

    ArraySumCalculator(ConcurrentHashMap<String, BigDecimal> sumMap,
                       List<BigDecimal> numbers,
                       String arrayName) {
      this.sumMap = sumMap;
      this.numbers = numbers;
      this.arrayName = arrayName;
    }

    @Override
    public void run() {
      sumMap.put(arrayName, sum(numbers));
    }
  }
}
