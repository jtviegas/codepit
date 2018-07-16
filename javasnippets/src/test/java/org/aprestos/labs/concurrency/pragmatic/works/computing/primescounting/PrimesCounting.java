package org.aprestos.labs.concurrency.pragmatic.works.computing.primescounting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aprestos.labs.concurrency.pragmatic.tasks.Task;
import org.aprestos.labs.concurrency.pragmatic.tasks.computing.primescounting.CountPrimes;
import org.aprestos.labs.concurrency.pragmatic.works.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrimesCounting implements Work {
  private static final Logger logger = LoggerFactory.getLogger(PrimesCounting.class);

  private List<Map<String, Object>> contexts = new ArrayList<Map<String, Object>>();

  private Task task;

  public PrimesCounting(int n, int partitions) {
    logger.debug("[PrimesCounting|in] {}|{}", n, partitions);

    if (partitions > n)
      throw new RuntimeException("partitions have to be smaller or equal than the numbers");

    task = new CountPrimes();

    int range = n / partitions;
    int first = 0, last = 0;
    for (int i = 0; i < n; i++) {
      if (0 != i && 0 == i % range) {
        last = i - 1;
        contexts.add(createContext(first, last));
        first = i;
      } else {
        if (i == n - 1) {
          // last one
          contexts.add(createContext(first, i));
        }
      }
    }
    logger.debug("[PrimesCounting|out] contexts: {}", contexts.toString());
  }

  private Map<String, Object> createContext(int first, int last) {
    Map<String, Object> r = new HashMap<String, Object>();
    r.put("first", first);
    r.put("last", last);
    return r;
  }

  @Override
  public Task getTask() {
    return task;
  }

  @Override
  public int getIterations() {
    return contexts.size();
  }

  @Override
  public Map<String, Object> getContext(int index) {
    return contexts.get(index);
  }

}
