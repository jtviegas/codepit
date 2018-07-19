package org.aprestos.labs.concurrency.pragmatic;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.aprestos.labs.concurrency.pragmatic.executions.CompletableFuturesCommonForkJoinExecution;
import org.aprestos.labs.concurrency.pragmatic.executions.CompletableFuturesExecution;
import org.aprestos.labs.concurrency.pragmatic.executions.DedicatedForkPoolExecution;
import org.aprestos.labs.concurrency.pragmatic.executions.Execution;
import org.aprestos.labs.concurrency.pragmatic.executions.ThreadPoolExecution;
import org.aprestos.labs.concurrency.pragmatic.tasks.CountPrimes;
import org.aprestos.labs.concurrency.pragmatic.tasks.GetPageLength;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(Parameterized.class)
public class Tests {

  private static final Logger logger = LoggerFactory.getLogger(Tests.class);

  private static final List<String> stats = new ArrayList<String>();

  @AfterClass
  public static void saveStats() throws IOException {
    FileUtils.write(new File("output.csv"),
        stats.stream().collect(Collectors.joining(System.getProperty("line.separator"))), Charset.defaultCharset());
  }

  @Parameters
  public static Collection<Pair<String, Triple<List<Pair<TaskType, Object[]>>, Double, ExecutionType>>> data() {
    return Arrays.asList(
        Pair.of("500 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 500 })), 1.0,
                ExecutionType.CompletableFuturesCommonForkJoinExecution)),
        Pair.of("500 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 500 })), 0.75,
                ExecutionType.CompletableFuturesCommonForkJoinExecution)),
        Pair.of("500 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 500 })), 0.5,
                ExecutionType.CompletableFuturesCommonForkJoinExecution)),
        Pair.of("500 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 500 })), 0.25,
                ExecutionType.CompletableFuturesCommonForkJoinExecution)),
        Pair.of("500 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 500 })), 0.0,
                ExecutionType.CompletableFuturesCommonForkJoinExecution)),
        Pair.of("5000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 5000 })), 1.0,
                ExecutionType.CompletableFuturesCommonForkJoinExecution)),
        Pair.of("5000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 5000 })), 0.75,
                ExecutionType.CompletableFuturesCommonForkJoinExecution)),
        Pair.of("5000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 5000 })), 0.5,
                ExecutionType.CompletableFuturesCommonForkJoinExecution)),
        Pair.of("5000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 5000 })), 0.25,
                ExecutionType.CompletableFuturesCommonForkJoinExecution)),
        Pair.of("5000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 5000 })), 0.0,
                ExecutionType.CompletableFuturesCommonForkJoinExecution)),
        Pair.of("10000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 10000 })), 1.0,
                ExecutionType.CompletableFuturesCommonForkJoinExecution)),
        Pair.of("10000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 10000 })), 0.75,
                ExecutionType.CompletableFuturesCommonForkJoinExecution)),
        Pair.of("10000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 10000 })), 0.5,
                ExecutionType.CompletableFuturesCommonForkJoinExecution)),
        Pair.of("10000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 10000 })), 0.25,
                ExecutionType.CompletableFuturesCommonForkJoinExecution)),
        Pair.of("10000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 10000 })), 0.0,
                ExecutionType.CompletableFuturesCommonForkJoinExecution)),

        Pair.of("500 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 500 })), 1.0,
                ExecutionType.CompletableFuturesExecution)),
        Pair.of("500 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 500 })), 0.75,
                ExecutionType.CompletableFuturesExecution)),
        Pair.of("500 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 500 })), 0.5,
                ExecutionType.CompletableFuturesExecution)),
        Pair.of("500 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 500 })), 0.25,
                ExecutionType.CompletableFuturesExecution)),
        Pair.of("500 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 500 })), 0.0,
                ExecutionType.CompletableFuturesExecution)),
        Pair.of("5000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 5000 })), 1.0,
                ExecutionType.CompletableFuturesExecution)),
        Pair.of("5000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 5000 })), 0.75,
                ExecutionType.CompletableFuturesExecution)),
        Pair.of("5000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 5000 })), 0.5,
                ExecutionType.CompletableFuturesExecution)),
        Pair.of("5000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 5000 })), 0.25,
                ExecutionType.CompletableFuturesExecution)),
        Pair.of("5000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 5000 })), 0.0,
                ExecutionType.CompletableFuturesExecution)),
        Pair.of("10000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 10000 })), 1.0,
                ExecutionType.CompletableFuturesExecution)),
        Pair.of("10000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 10000 })), 0.75,
                ExecutionType.CompletableFuturesExecution)),
        Pair.of("10000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 10000 })), 0.5,
                ExecutionType.CompletableFuturesExecution)),
        Pair.of("10000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 10000 })), 0.25,
                ExecutionType.CompletableFuturesExecution)),
        Pair.of("10000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 10000 })), 0.0,
                ExecutionType.CompletableFuturesExecution)),

        Pair.of("500 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 500 })), 1.0,
                ExecutionType.DedicatedForkPoolExecution)),
        Pair.of("500 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 500 })), 0.75,
                ExecutionType.DedicatedForkPoolExecution)),
        Pair.of("500 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 500 })), 0.5,
                ExecutionType.DedicatedForkPoolExecution)),
        Pair.of("500 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 500 })), 0.25,
                ExecutionType.DedicatedForkPoolExecution)),
        Pair.of("500 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 500 })), 0.0,
                ExecutionType.DedicatedForkPoolExecution)),
        Pair.of("5000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 5000 })), 1.0,
                ExecutionType.DedicatedForkPoolExecution)),
        Pair.of("5000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 5000 })), 0.75,
                ExecutionType.DedicatedForkPoolExecution)),
        Pair.of("5000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 5000 })), 0.5,
                ExecutionType.DedicatedForkPoolExecution)),
        Pair.of("5000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 5000 })), 0.25,
                ExecutionType.DedicatedForkPoolExecution)),
        Pair.of("5000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 5000 })), 0.0,
                ExecutionType.DedicatedForkPoolExecution)),
        Pair.of("10000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 10000 })), 1.0,
                ExecutionType.DedicatedForkPoolExecution)),
        Pair.of("10000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 10000 })), 0.75,
                ExecutionType.DedicatedForkPoolExecution)),
        Pair.of("10000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 10000 })), 0.5,
                ExecutionType.DedicatedForkPoolExecution)),
        Pair.of("10000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 10000 })), 0.25,
                ExecutionType.DedicatedForkPoolExecution)),
        Pair.of("10000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 10000 })), 0.0,
                ExecutionType.DedicatedForkPoolExecution)),

        Pair.of("500 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 500 })), 1.0,
                ExecutionType.ThreadPoolExecution)),
        Pair.of("500 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 500 })), 0.75,
                ExecutionType.ThreadPoolExecution)),
        Pair.of("500 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 500 })), 0.5,
                ExecutionType.ThreadPoolExecution)),
        Pair.of("500 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 500 })), 0.25,
                ExecutionType.ThreadPoolExecution)),
        Pair.of("500 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 500 })), 0.0,
                ExecutionType.ThreadPoolExecution)),
        Pair.of("5000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 5000 })), 1.0,
                ExecutionType.ThreadPoolExecution)),
        Pair.of("5000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 5000 })), 0.75,
                ExecutionType.ThreadPoolExecution)),
        Pair.of("5000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 5000 })), 0.5,
                ExecutionType.ThreadPoolExecution)),
        Pair.of("5000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 5000 })), 0.25,
                ExecutionType.ThreadPoolExecution)),
        Pair.of("5000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 5000 })), 0.0,
                ExecutionType.ThreadPoolExecution)),
        Pair.of("10000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 10000 })), 1.0,
                ExecutionType.ThreadPoolExecution)),
        Pair.of("10000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 10000 })), 0.75,
                ExecutionType.ThreadPoolExecution)),
        Pair.of("10000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 10000 })), 0.5,
                ExecutionType.ThreadPoolExecution)),
        Pair.of("10000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 10000 })), 0.25,
                ExecutionType.ThreadPoolExecution)),
        Pair.of("10000 io,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.IO, new Object[] { 10000 })), 0.0,
                ExecutionType.ThreadPoolExecution)),

        Pair.of("50000 processing in 10 partitions,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.PROCESSING, new Object[] { 50000, 10 })), 1.0,
                ExecutionType.CompletableFuturesCommonForkJoinExecution)),
        Pair.of("50000 processing in 10 partitions,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.PROCESSING, new Object[] { 50000, 10 })), 0.75,
                ExecutionType.CompletableFuturesCommonForkJoinExecution)),
        Pair.of("50000 processing in 10 partitions,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.PROCESSING, new Object[] { 50000, 10 })), 0.5,
                ExecutionType.CompletableFuturesCommonForkJoinExecution)),
        Pair.of("50000 processing in 10 partitions,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.PROCESSING, new Object[] { 50000, 10 })), 0.25,
                ExecutionType.CompletableFuturesCommonForkJoinExecution)),
        Pair.of("50000 processing in 10 partitions,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.PROCESSING, new Object[] { 50000, 10 })), 0.0,
                ExecutionType.CompletableFuturesCommonForkJoinExecution)),

        Pair.of("100000 processing in 10 partitions,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.PROCESSING, new Object[] { 100000, 10 })), 1.0,
                ExecutionType.CompletableFuturesCommonForkJoinExecution)),
        Pair.of("100000 processing in 10 partitions,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.PROCESSING, new Object[] { 100000, 10 })), 0.75,
                ExecutionType.CompletableFuturesCommonForkJoinExecution)),
        Pair.of("100000 processing in 10 partitions,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.PROCESSING, new Object[] { 100000, 10 })), 0.5,
                ExecutionType.CompletableFuturesCommonForkJoinExecution)),
        Pair.of("100000 processing in 10 partitions,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.PROCESSING, new Object[] { 100000, 10 })), 0.25,
                ExecutionType.CompletableFuturesCommonForkJoinExecution)),
        Pair.of("100000 processing in 10 partitions,%f,%d,%d",
            Triple.of(Arrays.asList(Pair.of(TaskType.PROCESSING, new Object[] { 100000, 10 })), 0.0,
                ExecutionType.CompletableFuturesCommonForkJoinExecution)),

        Pair.of("50000 processing in 10 partitions and 500 io,%f,%d,%d",
            Triple.of(
                Arrays.asList(Pair.of(TaskType.IO, new Object[] { 500 }),
                    Pair.of(TaskType.PROCESSING, new Object[] { 50000, 10 })),
                1.0, ExecutionType.CompletableFuturesCommonForkJoinExecution)),
        Pair.of("50000 processing in 10 partitions and 500 io,%f,%d,%d",
            Triple.of(
                Arrays.asList(Pair.of(TaskType.IO, new Object[] { 500 }),
                    Pair.of(TaskType.PROCESSING, new Object[] { 50000, 10 })),
                0.75, ExecutionType.CompletableFuturesCommonForkJoinExecution)),
        Pair.of("50000 processing in 10 partitions and 500 io,%f,%d,%d",
            Triple.of(
                Arrays.asList(Pair.of(TaskType.IO, new Object[] { 500 }),
                    Pair.of(TaskType.PROCESSING, new Object[] { 50000, 10 })),
                0.5, ExecutionType.CompletableFuturesCommonForkJoinExecution)),
        Pair.of("50000 processing in 10 partitions and 500 io,%f,%d,%d",
            Triple.of(
                Arrays.asList(Pair.of(TaskType.IO, new Object[] { 500 }),
                    Pair.of(TaskType.PROCESSING, new Object[] { 50000, 10 })),
                0.25, ExecutionType.CompletableFuturesCommonForkJoinExecution)),
        Pair.of("50000 processing in 10 partitions and 500 io,%f,%d,%d",
            Triple.of(
                Arrays.asList(Pair.of(TaskType.IO, new Object[] { 500 }),
                    Pair.of(TaskType.PROCESSING, new Object[] { 50000, 10 })),
                0.0, ExecutionType.CompletableFuturesCommonForkJoinExecution)),

        Pair.of("100000 processing in 10 partitions and 5000 io,%f,%d,%d",
            Triple.of(
                Arrays.asList(Pair.of(TaskType.IO, new Object[] { 5000 }),
                    Pair.of(TaskType.PROCESSING, new Object[] { 100000, 10 })),
                1.0, ExecutionType.CompletableFuturesCommonForkJoinExecution)),
        Pair.of("100000 processing in 10 partitions and 5000 io,%f,%d,%d",
            Triple.of(
                Arrays.asList(Pair.of(TaskType.IO, new Object[] { 5000 }),
                    Pair.of(TaskType.PROCESSING, new Object[] { 100000, 10 })),
                0.75, ExecutionType.CompletableFuturesCommonForkJoinExecution)),
        Pair.of("100000 processing in 10 partitions and 5000 io,%f,%d,%d",
            Triple.of(
                Arrays.asList(Pair.of(TaskType.IO, new Object[] { 5000 }),
                    Pair.of(TaskType.PROCESSING, new Object[] { 100000, 10 })),
                0.5, ExecutionType.CompletableFuturesCommonForkJoinExecution)),
        Pair.of("100000 processing in 10 partitions and 5000 io,%f,%d,%d",
            Triple.of(
                Arrays.asList(Pair.of(TaskType.IO, new Object[] { 5000 }),
                    Pair.of(TaskType.PROCESSING, new Object[] { 100000, 10 })),
                0.25, ExecutionType.CompletableFuturesCommonForkJoinExecution)),
        Pair.of("100000 processing in 10 partitions and 5000 io,%f,%d,%d",
            Triple.of(
                Arrays.asList(Pair.of(TaskType.IO, new Object[] { 5000 }),
                    Pair.of(TaskType.PROCESSING, new Object[] { 100000, 10 })),
                0.0, ExecutionType.CompletableFuturesCommonForkJoinExecution))

    );
  }

  @Parameter
  public Pair<String, Triple<List<Pair<TaskType, Object[]>>, Double, ExecutionType>> param;

  @Test
  public void test() throws Exception {

    final Map<String, Object> context = new HashMap<String, Object>();
    context.put("startTs", System.nanoTime() / 1000000);
    doTest(param.getRight().getLeft(), param.getRight().getMiddle(), param.getRight().getRight(), context).execute();

    stats.add(String.format(param.getLeft(), param.getRight().getMiddle(), param.getRight().getRight().ordinal(),
        (long) context.get("duration")));

    Assert.assertTrue(true);
  }

  private Execution doTest(List<Pair<TaskType, Object[]>> tasks, double ioCoefficient, ExecutionType exType,
      final Map<String, Object> context) {
    Execution execution = null;

    List<Callable<Void>> callables = new ArrayList<Callable<Void>>();
    for (Pair<TaskType, Object[]> task : tasks) {
      if (task.getLeft().equals(TaskType.IO))
        callables.addAll(GetPageLength.createTasks((int) task.getRight()[0], context));
      else
        callables.addAll(CountPrimes.createTasks((int) task.getRight()[0], (int) task.getRight()[1], context));
    }

    final Callable<Void> cb = new Callable<Void>() {
      @Override
      public Void call() throws Exception {
        callback(context);
        return null;
      }
    };

    switch (exType) {
    case CompletableFuturesCommonForkJoinExecution:
      execution = new CompletableFuturesCommonForkJoinExecution(ioCoefficient, callables, cb);
      break;
    case CompletableFuturesExecution:
      execution = new CompletableFuturesExecution(ioCoefficient, callables, cb);
      break;
    case DedicatedForkPoolExecution:
      execution = new DedicatedForkPoolExecution(ioCoefficient, callables, cb);
      break;
    default:
      execution = new ThreadPoolExecution(ioCoefficient, callables, cb);

    }

    return execution;
  }

  private enum TaskType {
    IO,
    PROCESSING
  }

  private enum ExecutionType {
    CompletableFuturesCommonForkJoinExecution,
    CompletableFuturesExecution,
    DedicatedForkPoolExecution,
    ThreadPoolExecution,
  }

  private void callback(final Map<String, Object> context) {
    int totalPrimes = 0, totalPrimesCounts = 0, totalPages = 0, totalPageCounts = 0;
    long totalLength = 0;
    long duration = ((System.nanoTime() / 1000000) - ((long) context.remove("startTs"))) / 1000;
    for (Map.Entry<String, Object> entry : context.entrySet()) {
      if (entry.getValue() instanceof Triple<?, ?, ?>) {

        @SuppressWarnings("unchecked")
        final int n = ((Triple<Integer, Integer, Integer>) entry.getValue()).getRight();
        totalPrimesCounts++;
        totalPrimes += n;
      } else if (entry.getValue() instanceof Pair<?, ?>) {

        @SuppressWarnings("unchecked")
        final int n = ((Pair<String, Integer>) entry.getValue()).getRight();
        totalPages += n > 0 ? 1 : 0;
        totalPageCounts++;
        totalLength += n;
      }
    }
    context.put("duration", duration);
    logger.info(
        "duration: {} s | totalPrimesCounts: {} | totalPrimes: {} | totalPages: {} | totalPageCounts: {} | totalLength: {} ",
        new Object[] { duration, totalPrimesCounts, totalPrimes, totalPages, totalPageCounts, totalLength });
  }

}
