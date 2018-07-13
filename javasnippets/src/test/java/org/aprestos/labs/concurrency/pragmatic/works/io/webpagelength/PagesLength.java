package org.aprestos.labs.concurrency.pragmatic.works.io.webpagelength;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aprestos.labs.concurrency.pragmatic.tasks.Task;
import org.aprestos.labs.concurrency.pragmatic.tasks.io.webpagelength.GetPageLength;
import org.aprestos.labs.concurrency.pragmatic.works.Work;

public class PagesLength implements Work {

  private final List<Map<String, Object>> contexts = new ArrayList<Map<String, Object>>();

  private final int iterations;

  private final Task task;

  public PagesLength(int iterations) {
    this.iterations = iterations;
    this.task = new GetPageLength();
    for (int i = 0; i < iterations; i++)
      contexts.add(new HashMap<String, Object>());
  }

  @Override
  public Map<String, Object> getContext(int index) {
    return contexts.get(index);
  }

  @Override
  public int getIterations() {
    return iterations;
  }

  @Override
  public Task getTask() {
    return task;
  }

}
