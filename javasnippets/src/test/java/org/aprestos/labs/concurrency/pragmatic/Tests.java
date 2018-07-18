package org.aprestos.labs.concurrency.pragmatic;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.aprestos.labs.concurrency.pragmatic.executions.CompletableFuturesCommonForkJoinExecution;
import org.aprestos.labs.concurrency.pragmatic.executions.CompletableFuturesExecution;
import org.aprestos.labs.concurrency.pragmatic.executions.DedicatedForkPoolExecution;
import org.aprestos.labs.concurrency.pragmatic.executions.ThreadPoolExecution;
import org.aprestos.labs.concurrency.pragmatic.tasks.CountPrimes;
import org.aprestos.labs.concurrency.pragmatic.tasks.GetPageLength;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Tests {

  private static final Logger logger = LoggerFactory.getLogger(Tests.class);

  @Test
  public void test_00AA_inertia_breaker() throws Exception {
    Assert.assertTrue(true);
  }

  private void callbackPageLength(final Map<String, Object> context) {
    int totalLength = 0, totalPages = 0, totalCounts = 0;
    long duration = (System.nanoTime() / 1000000) - ((long) context.remove("startTs"));
    for (Map.Entry<String, Object> entry : context.entrySet()) {
      @SuppressWarnings("unchecked")
      final int n = ((Pair<String, Integer>) entry.getValue()).getRight();
      totalPages += n > 0 ? 1 : 0;
      totalCounts++;
      totalLength += n;
    }
    logger.info("duration: {} | totalCounts: {} | totalPages: {} | totalLength: {}",
        new Object[] { duration, totalCounts, totalPages, totalLength });
  }

  private void callbackCountPrimes(final Map<String, Object> context) {
    int totalPrimes = 0, totalCounts = 0;
    long duration = (System.nanoTime() / 1000000) - ((long) context.remove("startTs"));
    for (Map.Entry<String, Object> entry : context.entrySet()) {
      @SuppressWarnings("unchecked")
      final int n = ((Triple<Integer, Integer, Integer>) entry.getValue()).getRight();
      totalCounts++;
      totalPrimes += n;
    }
    logger.info("duration: {} | totalCounts: {} | totalPrimes: {}",
        new Object[] { duration, totalCounts, totalPrimes });
  }

  @Test
  public void test_100000CountPrimes_ThreadPoolExecution() throws Exception {

    final Map<String, Object> context = new HashMap<String, Object>();
    context.put("startTs", System.nanoTime() / 1000000);
    new ThreadPoolExecution(0.1, CountPrimes.createTasks(100000, 10, context), new Callable<Void>() {
      @Override
      public Void call() throws Exception {
        callbackCountPrimes(context);
        return null;
      }
    }).execute();
    ;
    Assert.assertTrue(true);
  }

  @Test
  public void test_100000CountPrimes_DedicatedForkPoolExecution() throws Exception {

    final Map<String, Object> context = new HashMap<String, Object>();
    context.put("startTs", System.nanoTime() / 1000000);
    new DedicatedForkPoolExecution(0.1, CountPrimes.createTasks(100000, 1, context), new Callable<Void>() {
      @Override
      public Void call() throws Exception {
        callbackCountPrimes(context);
        return null;
      }
    }).execute();
    ;
    Assert.assertTrue(true);
  }

  @Test
  public void test_100000CountPrimes_CompletableFuturesExecution() throws Exception {

    final Map<String, Object> context = new HashMap<String, Object>();
    context.put("startTs", System.nanoTime() / 1000000);
    new CompletableFuturesExecution(0.1, CountPrimes.createTasks(100000, 10, context), new Callable<Void>() {
      @Override
      public Void call() throws Exception {
        callbackCountPrimes(context);
        return null;
      }
    }).execute();
    ;
    Assert.assertTrue(true);
  }

  @Test
  public void test_100000CountPrimes_CompletableFuturesCommonForkJoinExecution() throws Exception {

    final Map<String, Object> context = new HashMap<String, Object>();
    context.put("startTs", System.nanoTime() / 1000000);
    new CompletableFuturesCommonForkJoinExecution(0.1, CountPrimes.createTasks(100000, 10, context),
        new Callable<Void>() {
          @Override
          public Void call() throws Exception {
            callbackCountPrimes(context);
            return null;
          }
        }).execute();
    ;
    Assert.assertTrue(true);
  }

  @Test
  public void test_500pagesLength_ThreadPoolExecution() throws Exception {

    final Map<String, Object> context = new HashMap<String, Object>();
    context.put("startTs", System.nanoTime() / 1000000);
    new ThreadPoolExecution(1.0, GetPageLength.createTasks(500, context), new Callable<Void>() {
      @Override
      public Void call() throws Exception {
        callbackPageLength(context);
        return null;
      }
    }).execute();
    ;
    Assert.assertTrue(true);
  }

  @Test
  public void test_500pagesLength_DedicatedForkPoolExecution() throws Exception {

    final Map<String, Object> context = new HashMap<String, Object>();
    context.put("startTs", System.nanoTime() / 1000000);
    new DedicatedForkPoolExecution(1.0, GetPageLength.createTasks(500, context), new Callable<Void>() {
      @Override
      public Void call() throws Exception {
        callbackPageLength(context);
        return null;
      }
    }).execute();
    ;
    Assert.assertTrue(true);
  }

  @Test
  public void test_500pagesLength_CompletableFuturesExecution() throws Exception {

    final Map<String, Object> context = new HashMap<String, Object>();
    context.put("startTs", System.nanoTime() / 1000000);
    new CompletableFuturesExecution(1.0, GetPageLength.createTasks(500, context), new Callable<Void>() {
      @Override
      public Void call() throws Exception {
        callbackPageLength(context);
        return null;
      }
    }).execute();
    ;
    Assert.assertTrue(true);
  }

  @Test
  public void test_500pagesLength_CompletableFuturesCommonForkJoinExecution() throws Exception {

    final Map<String, Object> context = new HashMap<String, Object>();
    context.put("startTs", System.nanoTime() / 1000000);
    new CompletableFuturesCommonForkJoinExecution(1.0, GetPageLength.createTasks(500, context), new Callable<Void>() {
      @Override
      public Void call() throws Exception {
        callbackPageLength(context);
        return null;
      }
    }).execute();
    ;
    Assert.assertTrue(true);
  }

}
