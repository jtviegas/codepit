package org.aprestos.labs.concurrency.pragmatic.tasks;

import java.util.Map;

public interface Task {

  void run(Map<String,Object> context);

}