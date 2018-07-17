package org.aprestos.labs.concurrency.pragmatic;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
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
    int totalLength = 0, totalPages = 0;
    for (Map.Entry<String, Object> entry : context.entrySet()) {
      @SuppressWarnings("unchecked")
      final int n = ((Pair<String, Integer>) entry.getValue()).getRight();
      totalPages += n > 0 ? 1 : 0;
      totalLength += n;
    }
    logger.info("totalPages: {} | totalLength: {}", new Object[] { totalPages, totalLength });
  }

  private void callbackCountPrimes(final Map<String, Object> context) {
    int totalPrimes = 0;
    for (Map.Entry<String, Object> entry : context.entrySet()) {
      @SuppressWarnings("unchecked")
      final int n = ((Triple<Integer, Integer, Integer>) entry.getValue()).getRight();
      totalPrimes += n;
    }
    logger.info("totalPrimes: {}", new Object[] { totalPrimes });
  }

  @Test
  public void test_15000CountPrimes_ThreadPoolExecution() throws Exception {

    final Map<String, Object> context = new HashMap<String, Object>();
    new ThreadPoolExecution(0.1, CountPrimes.createTasks(15000, 5, context), new Callable<Void>() {
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
  public void test_15000CountPrimes_DedicatedForkPoolExecution() throws Exception {

    final Map<String, Object> context = new HashMap<String, Object>();
    new DedicatedForkPoolExecution(0.1, CountPrimes.createTasks(15000, 5, context), new Callable<Void>() {
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
  public void test_15000CountPrimes_CompletableFuturesExecution() throws Exception {

    final Map<String, Object> context = new HashMap<String, Object>();
    new CompletableFuturesExecution(0.1, CountPrimes.createTasks(15000, 5, context), new Callable<Void>() {
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

}
