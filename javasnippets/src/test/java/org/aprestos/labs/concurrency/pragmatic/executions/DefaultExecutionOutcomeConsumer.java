package org.aprestos.labs.concurrency.pragmatic.executions;

import java.util.function.Supplier;

import org.apache.commons.lang3.tuple.Pair;
import org.aprestos.labs.concurrency.pragmatic.works.Work;

public class DefaultExecutionOutcomeConsumer implements Supplier<Pair<Integer, Integer>> {

  private Work work;

  DefaultExecutionOutcomeConsumer(Work work) {
    this.work = work;
  }

  @Override
  public Pair<Integer, Integer> get() {
    int outcome = 0;
    for (int i = 0; i < work.getIterations(); i++) {
      if (work.getContext(i).containsKey("outcome")) {
        outcome += (Integer) work.getContext(i).get("outcome");
      }
    }
    return Pair.of(work.getIterations(), outcome);
  }

}
