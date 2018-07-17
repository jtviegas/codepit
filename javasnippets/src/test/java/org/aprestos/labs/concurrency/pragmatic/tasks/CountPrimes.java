package org.aprestos.labs.concurrency.pragmatic.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountPrimes implements Task {
  private static final Logger logger = LoggerFactory.getLogger(CountPrimes.class);

  private Map<String, Object> context;

  private final String id;

  public CountPrimes() {
    id = Integer.toString(idGenerator.getAndIncrement());
  }

  @Override
  public void setContext(Map<String, Object> context) {
    this.context = context;
  }

  @Override
  public String getId() {
    return id;
  }

  private boolean isPrime(int o) {
    boolean r = true;

    if (o <= 1)
      return false;

    for (int i = 2; i <= Math.sqrt(o); i++) {
      if (0 == o % i) {
        r = false;
        break;
      }
    }

    return r;
  }

  @Override
  public Void call() throws Exception {
    logger.trace("[call|in]");

    @SuppressWarnings({ "unchecked" })
    Triple<Integer, Integer, Integer> args = (Triple<Integer, Integer, Integer>) context.get(id);

    int first = args.getLeft();
    int last = args.getMiddle();
    int outcome = 0;

    for (int i = first; i <= last; i++)
      outcome += isPrime(i) ? 1 : 0;

    logger.debug("[call] primes in [{},{}]: {}", new Object[] { first, last, outcome });
    context.put(id, Triple.of(first, last, outcome));
    logger.trace("[call|out]");
    return null;
  }

  public static List<Callable<Void>> createTasks(int n, int partitions, final Map<String, Object> context) {
    logger.trace("[createTasks|in] {}|{}", n, partitions);

    if (partitions > n)
      throw new RuntimeException("partitions have to be smaller or equal than the numbers");

    final List<Callable<Void>> tasks = new ArrayList<Callable<Void>>();

    BiFunction<Pair<Integer, Integer>, Map<String, Object>, Task> taskCreator = new BiFunction<Pair<Integer, Integer>, Map<String, Object>, Task>() {
      @Override
      public Task apply(final Pair<Integer, Integer> range, final Map<String, Object> context) {
        final Task task = new CountPrimes();
        context.put(task.getId(), Triple.of(range.getLeft(), range.getRight(), 0));
        task.setContext(context);
        return task;
      }
    };

    int range = n / partitions;
    int first = 0, last = 0;
    for (int i = 0; i < n; i++) {
      if (0 != i && 0 == i % range) {
        last = i - 1;
        tasks.add(taskCreator.apply(Pair.of(first, last), context));
        first = i;
      } else {
        if (i == n - 1) {
          // last one
          tasks.add(taskCreator.apply(Pair.of(first, i), context));
        }
      }
    }
    logger.trace("[createTasks|out] tasks: {}", tasks.toString());
    return tasks;
  }

}
