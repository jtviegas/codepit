package org.aprestos.labs.concurrency.pragmatic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class BalancedListRandomPartitioning<T> implements Function<List<T>, List<List<T>>> {

  private final int n;

  Random rand = new Random();

  List<Integer> indexes = new ArrayList<Integer>();

  public BalancedListRandomPartitioning(int parts) {
    this.n = parts;
  }

  private int nextIndex(int size) {
    int r;
    do {
      r = rand.nextInt(size);
    } while (indexes.contains(r));

    indexes.add(r);
    return r;
  }

  @Override
  public List<List<T>> apply(List<T> t) {

    int size = t.size();
    List<List<T>> r = new ArrayList<List<T>>(n);
    for (int i = 0; i < n; i++)
      r.add(new ArrayList<T>());

    for (int i = 0; i < size; i++)
      r.get(i % n).add(t.get(nextIndex(size)));

    return r;
  }

}
