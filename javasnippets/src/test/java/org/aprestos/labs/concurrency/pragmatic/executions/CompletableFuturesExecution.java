package org.aprestos.labs.concurrency.pragmatic.executions;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.apache.commons.lang3.tuple.Pair;
import org.aprestos.labs.concurrency.pragmatic.works.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompletableFuturesExecution implements Execution {

  private static final Logger logger = LoggerFactory.getLogger(CompletableFuturesExecution.class);

  private Work work;

  private final int poolSize;

  private final Supplier<Pair<Integer, Integer>> consumer;

  public CompletableFuturesExecution(double blockingCoefficient, Work work) {

    if (1 < blockingCoefficient || 0 > blockingCoefficient)
      throw new RuntimeException("blockingCoefficient should be in between [0.0,1.0]");
    // one decimal only
    blockingCoefficient = ((int) (blockingCoefficient / 0.1)) * 0.1;
    poolSize = (int) (blockingCoefficient == 1.0 ? Runtime.getRuntime().availableProcessors() * 10
        : Runtime.getRuntime().availableProcessors() / (1 - blockingCoefficient));
    this.work = work;
    this.consumer = new DefaultExecutionOutcomeConsumer(work);
  }

  @Override
  public Pair<Integer, Integer> execute() {
    logger.info("[execute|in] poolsize: {}", poolSize);

    ForkJoinPool pool = null;
    try {
      pool = new ForkJoinPool(poolSize);

      for (final Callable<Void> w : work.getWork()) {
        CompletableFuture.runAsync(new Runnable() {
          @Override
          public void run() {
            try {
              w.call();
            } catch (Exception e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }
        }, pool);
      }

      // CompletableFuture.allOf(cfs)

      List<Future<Void>> results = pool.invokeAll(work.getWork(), 1000, TimeUnit.SECONDS);
      for (final Future<Void> f : results)
        f.get();
      logger.info("all threads work terminated");

    } catch (Exception e) {
      logger.error("ops", e);
    } finally {
      try {
        if (null != pool)
          pool.shutdown();
      } catch (Exception ignore) {
      }
    }

    Pair<Integer, Integer> outcome = this.consumer.get();
    logger.info("[execute|out] {}", outcome);
    return outcome;
  }

}
