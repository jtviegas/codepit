package org.aprestos.labs.concurrency.pragmatic.executions;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class CompletableFuturesExecution extends AbstractExecution {

  private final ExecutorService pool;

  public CompletableFuturesExecution(double blockingCoefficient, List<Callable<Void>> tasks, Callable<Void> callback) {
    super(blockingCoefficient, tasks, callback);
    pool = Executors.newFixedThreadPool(poolSize,
        new ThreadFactoryBuilder().setDaemon(true).setNameFormat("CompletableFuturesExecution-pool-%d").build());
  }

  @Override
  public void execute() {
    logger.info("[execute|in] poolsize: {}", poolSize);
    try {

      List<CompletableFuture<Void>> wrappedTasks = tasks.stream().map(t -> CompletableFuture.runAsync(new Runnable() {
        @Override
        public void run() {
          try {
            t.call();
          } catch (Exception e) {
            e.printStackTrace();
          }

        }
      }, pool)).collect(Collectors.toList());

      CompletableFuture<Void> futures = CompletableFuture.allOf(wrappedTasks.toArray(new CompletableFuture<?>[] {}))
          .thenRunAsync(new Runnable() {
            @Override
            public void run() {
              try {
                callback.call();
              } catch (Exception e) {
                e.printStackTrace();
              }
            }
          }, pool);

      futures.join();
      logger.info("[execute] all threads have terminated");
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
