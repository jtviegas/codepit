package org.aprestos.labs.concurrency.pragmatic.executions;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.apache.commons.lang3.tuple.Pair;
import org.aprestos.labs.concurrency.pragmatic.works.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class ThreadPoolExecution implements Execution {

  private static final Logger logger = LoggerFactory.getLogger(ThreadPoolExecution.class);

  private Work work;

  private final int poolSize;

  private final Supplier<Pair<Integer, Integer>> consumer;

  public ThreadPoolExecution(double blockingCoefficient, Work work) {
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
    final ExecutorService executorPool = Executors.newFixedThreadPool(poolSize,
        new ThreadFactoryBuilder().setDaemon(true).setNameFormat("ThreadPoolExecution-pool-%d").build());
    try {
      final List<Future<Void>> results = executorPool.invokeAll(work.getWork(), 1000, TimeUnit.SECONDS);
      executorPool.shutdown();
      for (final Future<Void> f : results)
        f.get();
      logger.info("all threads have shutdown");
    } catch (Exception e) {
      logger.error("ops", e);
      if (!executorPool.isTerminated())
        try {
          logger.info("executorPool not yet terminated...");
          executorPool.shutdownNow();
        } catch (Exception e1) {
          logger.error("ops", e1);
        }
    }

    Pair<Integer, Integer> outcome = this.consumer.get();
    logger.info("[execute|out] {}", outcome);
    return outcome;
  }

}
