package org.aprestos.labs.concurrency.pragmatic.assorted;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class BalancedListHalfPartitioning<T> implements Function<List<T>, List<List<T>>> {

  @Override
  public List<List<T>> apply(List<T> t) {
    List<List<T>> r = new ArrayList<List<T>>();

    List<T> one = new ArrayList<T>();
    List<T> two = new ArrayList<T>();

    for (int i = 0, j = t.size() - 1; i <= j; i++, j--) {

      if (i == j) {
        if (new Random().nextBoolean())
          one.add(t.get(i));
        else
          two.add(t.get(i));
      } else {
        one.add(t.get(i));
        two.add(t.get(j));
      }

    }

    r.add(one);
    r.add(two);

    return r;
  }

}
