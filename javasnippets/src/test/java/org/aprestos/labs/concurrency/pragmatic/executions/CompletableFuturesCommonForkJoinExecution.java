package org.aprestos.labs.concurrency.pragmatic.executions;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CompletableFuturesCommonForkJoinExecution extends AbstractExecution {

  public CompletableFuturesCommonForkJoinExecution(double blockingCoefficient, List<Callable<Void>> tasks,
      Callable<Void> callback) {
    super(blockingCoefficient, tasks, callback);
    System.setProperty("java.util.concurrent.ForkJooinPool.common.parallelism", Integer.toString(poolSize));
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
      })).collect(Collectors.toList());

      CompletableFuture.allOf(wrappedTasks.toArray(new CompletableFuture<?>[] {})).thenRunAsync(new Runnable() {
        @Override
        public void run() {
          try {
            callback.call();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      });
      ForkJoinPool.commonPool().awaitQuiescence(1000, TimeUnit.SECONDS);

      logger.info("[execute] all threads have terminated");
    } catch (Exception e) {
      logger.error("[execute] ops", e);
    } finally {
      logger.info("[execute|out]");
    }
  }

}
