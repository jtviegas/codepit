package org.aprestos.labs.lang.java8.lambdas;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class FunctionalInterfaces {

  @Test
  public void test() {

    List<String> o = new ArrayList<String>();
    o.add("saDDADSDsadASFDAFRSF");
    o.add("saDDADSDsadASFDAFRSFsaddsadsD");
    // bifunction
    Sorter<String, String, Integer> comparator = (first, second) -> Integer.compare(first.length(), second.length());

    ToBeSorted<String, Integer> o2 = new ToBeSorted<String, Integer>(o);
    o2.sortMeOrKindOf(comparator);
  }

}

@FunctionalInterface
interface Sorter<F, S, R> {
  abstract R sortIt(F first, S second);
}

class ToBeSorted<F, R> {

  private List<F> s = new ArrayList<F>();

  ToBeSorted(List<F> v) {
    s.addAll(v);
  }

  void sortMeOrKindOf(Sorter<F, F, R> sorter) {

    for (int i = 0; i < s.size(); i += 2)
      System.out.println(sorter.sortIt(s.get(i), s.get(i + 1)).toString());
  }

}
