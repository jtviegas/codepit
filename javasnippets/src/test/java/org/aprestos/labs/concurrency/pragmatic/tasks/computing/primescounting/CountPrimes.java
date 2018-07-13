package org.aprestos.labs.concurrency.pragmatic.tasks.computing.primescounting;

import java.util.Map;

import org.aprestos.labs.concurrency.pragmatic.tasks.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountPrimes implements Task {
  private static final Logger logger = LoggerFactory.getLogger(CountPrimes.class);

  @Override
  public void run(Map<String, Object> context) {

    int first = (Integer) context.get("first");
    int last = (Integer) context.get("last");
    int outcome = 0;

    for (int i = first; i <= last; i++)
      outcome += isPrime(i) ? 1 : 0;

    logger.debug("primes in [{},{}]: {}", new Object[] { first, last, outcome });
    context.put("outcome", outcome);
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
}
