package org.aprestos.labs.concurrency.pragmatic.tasks;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public interface Task extends Callable<Void> {

  static final AtomicInteger idGenerator = new AtomicInteger();

  String getId();

  void setContext(Map<String, Object> context);

}