package org.aprestos.labs.concurrency.pragmatic.works;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.aprestos.labs.concurrency.pragmatic.tasks.Task;

public interface Work {

  default List<Callable<Void>> getWork() {
    List<Callable<Void>> r = new ArrayList<Callable<Void>>();

    final Task task = getTask();

    for (int i = 0; i < getIterations(); i++) {
      final Map<String, Object> context = getContext(i);
      r.add(new Callable<Void>() {
        @Override
        public Void call() throws Exception {
          task.run(context);
          return null;
        }
      });
    }

    return r;
  }

  Task getTask();

  int getIterations();

  Map<String, Object> getContext(int index);

}