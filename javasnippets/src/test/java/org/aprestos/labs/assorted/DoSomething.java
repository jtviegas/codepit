package org.aprestos.labs.assorted;

import org.junit.Test;

public class DoSomething {

  @Test
  public void test_00() {

    int[][] u = new int[][] { { 4, 8, 2 }, { 4, 5, 7 }, { 6, 1, 6 } };

    System.out.println(createMagicSquare(3));

  }

  
  
  private int findFirstElement(int [][] m) {
    
    int n = m[0].length;
    int northpos = -1, pos = -1;
    
    for (int i =0; i<n; i++) {
      for(int j = 0; j<n; j++) {
        int val = m[i][j];
        if( 1 == val ) {
          
        }
      }
    }
    
    if( i == 0 && n/2] )
      return 1;
    else if( 1 == m[n/2][0] )
      return 2;
    else if ( 1 == m[n-1][n/2] )
      return 3;
    else if ( 1 == m[n/2][n-1] )
      return 4;
      
    return 0;
  }
  
  private int[][] rotate(int[][] m){
    int n = m[0].length;
    int[][] r = new int[n][n];
    
    for( int i=0; i<n; i++ ) {
      System.arraycopy(m[i], 0, r[i], 0, n);
    }

    
    
    return r;
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
