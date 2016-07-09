package com.djimenezc.processor.summarize;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Solution that iterates through the array in sections,
 * the array is split up in parts and partial sum are calculated for threads
 * <p>
 * <p>
 * Created by david on 09/07/2016.
 */
class ParallelThreadsSummarizer extends AbstractSummarizer<BigDecimal> {

  ParallelThreadsSummarizer() {
    super("ParallelThreads");
  }

  private static final int NUMBER_THREADS = 3;
  private List numbers;
  private List<BigDecimal> partialResult = new ArrayList<>();

  private class runnable implements Runnable {

    private Thread t;
    private int begin;
    private int end;
    private String threadName;

    runnable(int b, int e, String T_name) {
      begin = b;
      end = e;
      threadName = T_name;
    }

    public void run() {
      BigDecimal sum = BigDecimal.ZERO;

      for (int i = begin; i <= end; i++) {
        sum = sum.add((BigDecimal) numbers.get(i));
      }
      partialResult.add(sum);
    }

    void start() {

      if (t == null) {
        t = new Thread(this, threadName);
//        System.out.println("Starting thread " + threadName + " start: " + this.begin + " " + this.end);
        t.start();
      }
    }

    void join() throws InterruptedException {
      t.join();
    }
  }

  @Override
  BigDecimal runSummarizer(List<BigDecimal> numbers) {

    this.numbers = numbers;
    int start = 0;
    int threadsRange = numbers.size() / NUMBER_THREADS;
    int end = numbers.size() < NUMBER_THREADS ? numbers.size() - 1 : threadsRange - 1;
    int numberOfThreads = numbers.size() < NUMBER_THREADS ? 1 : NUMBER_THREADS;

    try {
      startProcesses(numbers, start, threadsRange, end, numberOfThreads);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    BigDecimal totalSum = sumPartialValues();

    return totalSum;
  }

  private void startProcesses(List<BigDecimal> numbers, int start, int threadsRange, int end, int numberOfThreads) throws InterruptedException {

    runnable partialSumProcess = null;

    for (int threadNumber = 0; threadNumber < numberOfThreads; threadNumber++) {
      partialSumProcess = new runnable(start, end, "Thread-" + threadNumber);
      partialSumProcess.start();
      start = end + 1;
      end = calculateEndValue(numbers, threadsRange, end, numberOfThreads, threadNumber);
      partialSumProcess.join();
    }

  }

  private int calculateEndValue(List<BigDecimal> numbers, int threadsRange, int end, int numberOfThreads, int threadNumber) {

    end = threadsRange + end > numbers.size() ? numbers.size() : threadsRange + end;

    if (threadNumber + 2 == numberOfThreads) {
      end = numbers.size() - 1;
    }

    return end;
  }

  private BigDecimal sumPartialValues() {

    BigDecimal totalSum = BigDecimal.ZERO;

    for (BigDecimal partialResult : this.partialResult) {
//      System.out.println("a." + partialResult);
      totalSum = totalSum.add(partialResult);
    }

    this.partialResult.clear();

    return totalSum;
  }

}
