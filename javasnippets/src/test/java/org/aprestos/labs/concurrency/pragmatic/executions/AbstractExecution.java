package org.aprestos.labs.concurrency.pragmatic.executions;

import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractExecution implements Execution {

  protected final Logger logger = LoggerFactory.getLogger(this.getClass());

  protected final List<Callable<Void>> tasks;

  protected final int poolSize;

  protected final Callable<Void> callback;

  protected long startTs;

  public AbstractExecution(double blockingCoefficient, List<Callable<Void>> tasks, Callable<Void> callback) {
    if (1 < blockingCoefficient || 0 > blockingCoefficient)
      throw new RuntimeException("blockingCoefficient should be in between [0.0,1.0]");
    // one decimal only
    blockingCoefficient = ((int) (blockingCoefficient / 0.1)) * 0.1;
    poolSize = (int) (blockingCoefficient == 1.0 ? Runtime.getRuntime().availableProcessors() * 10
        : Runtime.getRuntime().availableProcessors() / (1 - blockingCoefficient));
    this.tasks = tasks;
    this.callback = callback;
  }

  public abstract void execute();

}