package org.aprestos.labs.concurrency.pragmatic.executions;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class ThreadPoolExecution extends AbstractExecution {

  public ThreadPoolExecution(double blockingCoefficient, List<Callable<Void>> tasks, Callable<Void> callback) {
    super(blockingCoefficient, tasks, callback);
  }

  @Override
  public void execute() {
    logger.info("[execute|in] poolsize: {}", poolSize);
    ExecutorService pool = null;
    try {
      pool = Executors.newFixedThreadPool(poolSize,
          new ThreadFactoryBuilder().setDaemon(true).setNameFormat("ThreadPoolExecution-pool-%d").build());

      pool.invokeAll(tasks, 300, TimeUnit.SECONDS);

      logger.info("[execute] all threads have terminated");
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
