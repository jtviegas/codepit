package org.aprestos.labs.assorted;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Example {

  private int doSomething(int[][] s) {
    int cost = 0;

    return cost;
  }

  @Test
  public void test() throws Exception {
    int[][] s = { { 4, 8, 2 }, { 4, 5, 7 }, { 6, 1, 6 } };
    Assert.assertEquals(4, doSomething(s));
  }

}
