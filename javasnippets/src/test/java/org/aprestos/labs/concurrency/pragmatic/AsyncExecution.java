package org.aprestos.labs.concurrency.pragmatic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class AsyncExecution<A, R> {

  private final int poolSize, numOfParts;

  public AsyncExecution(int poolSize, int numOfParts) {
    this.poolSize = poolSize;
    this.numOfParts = numOfParts;
  }

  public List<R> execute(final Function<List<A>, R> function, final List<A> arguments)
      throws InterruptedException, ExecutionException {
    List<Callable<R>> partitions = getPartitions(function, arguments);

    final ExecutorService executorPool = Executors.newFixedThreadPool(poolSize);
    final List<Future<R>> resultFromParts = executorPool.invokeAll(partitions, 10000, TimeUnit.SECONDS);
    executorPool.shutdown();
    List<R> result = new ArrayList<R>();
    for (final Future<R> f : resultFromParts)
      result.add(f.get());

    return result;
  }

  private List<Callable<R>> getPartitions(final Function<List<A>, R> function, final List<A> arguments) {
    List<Callable<R>> partitions = new ArrayList<Callable<R>>();

    int chunkSize = arguments.size() / numOfParts;
    int start = 0, end = 0;

    while (end < arguments.size() - 1) {
      end = (start + chunkSize - 1) < arguments.size() ? (start + chunkSize - 1) : arguments.size() - 1;

      final List<A> args = arguments.subList(start, end);

      partitions.add(new Callable<R>() {
        @Override
        public R call() throws Exception {
          return function.apply(args);
        }
      });
      start = end + 1;
    }

    return partitions;
  }

}
