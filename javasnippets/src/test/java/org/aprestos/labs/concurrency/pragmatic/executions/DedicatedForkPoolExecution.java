package org.aprestos.labs.concurrency.pragmatic.executions;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class DedicatedForkPoolExecution extends AbstractExecution {

  public DedicatedForkPoolExecution(double blockingCoefficient, List<Callable<Void>> tasks, Callable<Void> callback) {
    super(blockingCoefficient, tasks, callback);
  }

  @Override
  public void execute() {
    logger.info("[execute|in] poolsize: {}", poolSize);

    ForkJoinPool pool = null;
    try {
      pool = new ForkJoinPool(poolSize);

      List<Future<Void>> futures = pool.invokeAll(tasks, 1000, TimeUnit.SECONDS);
      futures.parallelStream().forEach(p -> {
        try {
          p.get();
        } catch (Exception e) {
          logger.error("oopps on checking futures", e);
        }
      });
      logger.info("[execute] all threads work terminated");
      callback.call();
    } catch (Exception e) {
      logger.error("[execute] ops", e);
    } finally {
      try {
        if (null != pool) {
          pool.shutdown();
          logger.info("[execute] pool has shutdown");
        }
      } catch (Exception ignore) {
      }

    }

    logger.info("[execute|out]");
  }

}
