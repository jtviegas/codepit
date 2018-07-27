package org.aprestos.labs.assorted;

import org.junit.Test;

public class DoSomething {

  @Test
  public void test_00() {

    int[][] u = new int[][] { { 4, 8, 2 }, { 4, 5, 7 }, { 6, 1, 6 } };

    System.out.println(createMagicSquare(3));

  }

  private int nextPosition(int n, int position, int val, int[][] m) {
    int r = -1;

    int row = position / 3;
    int column = position % n;

    int next_row = row - 1;
    int next_column = column + 1;

    boolean changing = true;
    while (changing) {
      if (next_row < 0 && next_column == n) {
        next_row = n-1;
        next_column = column;
      } else if ( next_column == n ) {
        next_column = 0;
      }
      else if( next_row < 0 ) {
        next_row = n - 1;
      }
      else if (0 != (m[next_row][next_column]) && val > m[next_row][next_column]) {
        next_row = row + 1;
        next_column = column;
      }
      else
        changing = false;
    }
    r = n * next_row + next_column;
    return r;
  }

  public int[][] createMagicSquare(int n) {
    if (n % 2 == 0)
      throw new RuntimeException("we can only create odd squares");

    int[][] r = new int[n][n];

    int value = 0;
    int column = n / 2;
    int row = 0;
    r[row][column] = ++value;
    int position = n * row + column;

    do {
      position = nextPosition(n, position, value, r);
      r[position / 3][position % n] = ++value;
    } while ((n*n) > value);

    return r;
  }

}
