package com.djimenezc.processor.summarize;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Solution that use the future API to calculate partial sum and merge
 * all the results in the end.
 * Created by david on 12/07/2016.
 */
class CallableSummarizer extends AbstractSummarizer<BigDecimal> {

  private ChopperUtil<BigDecimal> chopperUtil;

  CallableSummarizer() {
    super("callable");
    chopperUtil = new ChopperUtil<>();
  }

  @Override
  BigDecimal runSummarizer(List<BigDecimal> numbers) {

    BigDecimal sum = BigDecimal.ZERO;

    List<Callable<BigDecimal>> callables = new ArrayList<>(numbers.size());

    int numberPart = (int) Math.ceil(numbers.size() / 100);
    List<List<BigDecimal>> parts = chopperUtil.chopped(numbers, numberPart);

    //Create callables for each array
    for (List<BigDecimal> integers : parts) {
      Callable<BigDecimal> callable = new ArraySumCallable(integers);
      callables.add(callable);
    }

    ExecutorService executorService = Executors.newFixedThreadPool(
      Runtime.getRuntime().availableProcessors() + 1);

    try {

      List<Future<BigDecimal>> futures =
        executorService.invokeAll(callables);

      executorService.shutdown();

      //Iterate through the futures and get the sum
      for (Future<BigDecimal> future : futures) {
        BigDecimal partialSum = future.get();

        sum = sum.add(partialSum);
      }

    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    } catch (ExecutionException e) {
      System.out.println("Exception while calculating sum");
    }

    return sum;
  }

  private class ChopperUtil<T> {

    <T> List<List<T>> chopped(List<T> list, final int L) {
      List<List<T>> parts = new ArrayList<>();
      final int N = list.size();

      for (int i = 0; i < N; i += L) {
        parts.add(new ArrayList<T>(
          list.subList(i, Math.min(N, i + L)))
        );
      }

      return parts;
    }
  }

  private class ArraySumCallable implements Callable<BigDecimal> {

    List<BigDecimal> numbers;

    ArraySumCallable(List<BigDecimal> numbers) {

      this.numbers = numbers;
    }

    @Override
    public BigDecimal call() throws Exception {

      BigDecimal sum = BigDecimal.ZERO;

      for (BigDecimal value : numbers) {
        sum = sum.add(value);
      }

      return sum;

    }
  }
}
