package org.aprestos.labs.challenges;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Tests {

  private int cost(int[][] s) {
    int cost = 0;

    int border_n = s[0][0] + s[0][1] + s[0][2];
    int border_w = s[0][0] + s[1][0] + s[2][0];
    int border_s = s[2][0] + s[2][1] + s[2][2];
    int border_e = s[0][2] + s[1][2] + s[2][2];
    int diag = s[0][0] + s[1][1] + s[2][2];

    if (border_n == border_w && border_s == border_e && (border_n != diag || border_s != diag)) {
      int diff = border_n - border_s;
      if (0 < diff)
        s[2][2] += Math.abs(diff);
      else
        s[0][0] += Math.abs(diff);

      cost = 1;
    } else {
      int initial_01 = s[0][1], initial_10 = s[1][0], initial_21 = s[2][1], initial_12 = s[1][2];

      // s[0][0] = s[0]
      s[1][0] = s[1][1] + s[2][2] - s[2][0];
      s[0][1] = s[1][0] + s[2][0] - s[0][2];

      s[1][2] = s[0][0] + s[1][1] - s[0][2];
      s[2][1] = s[1][2] + s[0][2] - s[2][0];

      cost = Math.abs(initial_01 - s[0][1]) + Math.abs(initial_10 - s[1][0]) + Math.abs(initial_21 - s[2][1])
          + Math.abs(initial_12 - s[1][2]);
    }

    return cost;
  }

  @Test
  public void magicSquare() throws Exception {
    int[][] s = { { 4, 8, 2 }, { 4, 5, 7 }, { 6, 1, 6 } };
    Assert.assertEquals(4, cost(s));

    int[][] s2 = { { 4, 9, 2 }, { 3, 5, 7 }, { 8, 1, 5 } };
    Assert.assertEquals(1, cost(s2));

    int[][] s3 = { { 4, 5, 8 }, { 2, 4, 1 }, { 1, 9, 7 } };

    Assert.assertEquals(14, cost(s3));

  }

}
