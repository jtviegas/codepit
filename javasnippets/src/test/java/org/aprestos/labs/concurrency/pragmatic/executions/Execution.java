package org.aprestos.labs.concurrency.pragmatic.executions;

import org.apache.commons.lang3.tuple.Pair;

public interface Execution {

  Pair<Integer, Integer> execute();

}