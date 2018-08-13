package org.aprestos.labs.challenges;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

public final class OddMagicSquare {

	@Test
	public void test() {

		int[][] u = new int[][] { { 4, 8, 2 }, { 4, 5, 7 }, { 6, 1, 6 } };
		int[][] au = new int[][] { { 4, 8, 4 }, { 4, 5, 7 }, { 6, 1, 6 } };
		int[][] u2 = new int[][] { { 4, 8, 2, 1, 3 }, { 4, 5, 7, 8, 4 }, { 6, 1, 6, 5, 1 }, { 4, 5, 7, 1, 2 },
				{ 6, 1, 6, 4, 9 } };

		Assert.assertArrayEquals(rotate(u), new int[][] { { 2, 7, 6 }, { 8, 5, 1 }, { 4, 4, 6 } });
		Assert.assertArrayEquals(rotate(u2), new int[][] { { 3, 4, 1, 2, 9 }, { 1, 8, 5, 1, 4 }, { 2, 7, 6, 7, 6 },
				{ 8, 5, 1, 5, 1 }, { 4, 4, 6, 4, 6 } });

		Assert.assertEquals(findEdgeCentralOne(new int[][] { { 2, 1, 6 }, { 8, 5, 1 }, { 4, 4, 6 } }), 0);
		Assert.assertEquals(findEdgeCentralOne(new int[][] { { 2, 3, 6 }, { 8, 5, 1 }, { 4, 4, 6 } }), 3);
		Assert.assertEquals(findEdgeCentralOne(u2), 3);
		
		Assert.assertArrayEquals(turn(u), new int[][] { { 2,8, 4 }, { 7, 5, 4 }, { 6, 1, 6 } });
		Assert.assertEquals(2, distance(u, au));
		
		createSolutions(3);
		createSolutions(5);
		
		Assert.assertEquals(1, getClosestMagicSquare(new int[][] { { 4, 9, 2 }, { 3, 5, 7 }, { 8, 1, 5 } }));

	}

	private Pair<Integer,Object> transform2MagicSquare2(int[][] m) {
    
    if( 0 == m.length%2 )
      throw new IllegalArgumentException("can only solve odd squares");
      
    int orientation = findEdgeCentralOne(m);
    
    if (0 > orientation) // there is no startPoint
      orientation = findSmallerEdgeCentralPoint(m);

    if (0 < orientation)
      m = rotateNtimes(4 - orientation, m);
   
    int[][] r = new int[m.length][m.length];
    int pos = m.length / 2;
    int curval = 0;
    int cost = 0;
     
    if(1 != m[0][m.length / 2]) 
      cost = m[0][m.length / 2] - 1;

    r[0][m.length / 2] = 1;
    
    for(int i=1;i < (m.length*m.length); i++) {
      curval = i;
      int nextval = curval+1;
      pos = nextPosition2(pos, r);
      int new_row = pos / m.length;
      int new_column = pos % m.length;
      int newval = m[new_row][new_column];
      if(newval != nextval) 
        cost += Math.abs(nextval-newval);

      r[new_row][new_column] = nextval;
    }
    
    return Pair.of(cost, r);

  }
	
	private int findSmallerEdgeCentralPoint(int[][] m) {
	  
	  int n = m.length;
	  int r = -1;
	  int minval = n*n;
	  
	  // north
	  int val = m[0][n / 2];
	  if(val < minval) {
	    minval = val;
	    r = 0;
	  }
	  
	  // west
	  val = m[n/2][0];
	  if(val < minval) {
      minval = val;
      r = 1;
    }

	  // south
	  val = m[n-1][n/2];
	  if(val < minval) {
      minval = val;
      r = 2;
    }

	  // east
	  val = m[n/2][n-1];
	     if(val < minval) {
      minval = val;
      r = 3;
    }

    return r;
	}
	
/*	private Pair<Integer,Object> transform2MagicSquare(int[][] m) {
	  
	  if( 0 == m.length%2 )
	    throw new IllegalArgumentException("can only solve odd squares");
	    
	  int cost = 0;
		int orientation = findEdgeCentralOne(m);
		if (0 > orientation) {// there is no startPoint
			m = findValueAndSwapIt(1, m.length / 2, m);
			cost++;
		}
		else if (0 < orientation)
			m = rotateNtimes(4 - orientation, m);
		
		int pos = m.length / 2;
		int curval = 0;
		for(int i=1;i < (m.length*m.length); i++) {
		  curval = i;
		  int nextval = curval+1;
		  pos = nextPosition(pos, m);
		  int new_row = pos / m.length;
      int new_column = pos % m.length;
		  int newval = m[new_row][new_column];
		  if(newval != nextval) {
		    m = findValueAndSwapItOtherwiseSetIt(nextval, pos, m);
		    cost++;
		  }
		}
		
		return Pair.of(cost, m);

	}*/

	
/*	private int nextPosition(int position, int[][] m) {
    int r = -1;

    int row = position / m.length;
    int column = position % m.length;

    int next_row = row - 1;
    int next_column = column + 1;

    boolean changing = true;
    while (changing) {
      if (next_row < 0 && next_column == m.length) {
        next_row = m.length - 1;
        next_column = column;
      } else if (next_column == m.length) {
        next_column = 0;
      } else if (next_row < 0) {
        next_row = m.length - 1;
      }
      else if ( m[row][column] > m[next_row][next_column] ) {
        next_row = row + 1;
        next_column = column;
      }
      else
        changing = false;
    }
    r = m.length * next_row + next_column;
    return r;
  }*/
	
	private int nextPosition2(int position, int[][] m) {
    int r = -1;

    int row = position / m.length;
    int column = position % m.length;

    int next_row = row - 1;
    int next_column = column + 1;

    boolean changing = true;
    while (changing) {
      if (next_row < 0 && next_column == m.length) {
        next_row = m.length - 1;
        next_column = column;
      } else if (next_column == m.length) {
        next_column = 0;
      } else if (next_row < 0) {
        next_row = m.length - 1;
      }
      else if ( 0 != m[next_row][next_column] && m[row][column] > m[next_row][next_column] ) {
        next_row = row + 1;
        next_column = column;
      }
      else
        changing = false;
    }
    r = m.length * next_row + next_column;
    return r;
  }

	private int findValuePosition(int val, int[][] m) {
    int r = -1;
    for (int i = 0; i < m.length; i++) {
      for (int j = 0; j < m.length; j++) {
        if (val == m[i][j]) {
          r = (m.length * i + j);
          break;
        }
      }
    }
    
    return r;
  }
	
	private int[][] findValueAndSwapIt(int val, int pos, int[][] m) {
		boolean found = false;
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m.length; j++) {
				if (val == m[i][j]) {
					found = true;
					int x = pos / m.length;
					int y = pos % m.length;
					int val0 = m[x][y];
					m[x][y] = val;
					m[i][j] = val0;
					break;
				}
			}
		}
		if (!found)
			throw new IllegalArgumentException("couldn't find the value to swap position");

		return m;
	}
	
	private int[][] findValueAndSwapItOtherwiseSetIt(int val, int pos, int[][] m) {
    boolean found = false;
    
    int x = pos / m.length;
    int y = pos % m.length;
          
    for (int i = 0; i < m.length; i++) {
      for (int j = 0; j < m.length; j++) {
        if (val == m[i][j]) {
          found = true;
          int val0 = m[x][y];
          m[x][y] = val;
          m[i][j] = val0;
          break;
        }
      }
    }
    if (!found) 
      m[x][y] = val;

    return m;
  }

	private int findEdgeCentralOne(int[][] o) {

		int r = -1;
		int n = o.length;

		int endIndex = n - 1;
		if (1 == o[0][n / 2]) {
			r = 0; // north
		} else if (1 == o[n / 2][0]) {
			r = 1; // west
		} else if (1 == o[endIndex][n / 2]) {
			r = 2; // south
		} else if (1 == o[n / 2][endIndex]) {
			r = 3; // east
		}

		return r;
	}
	
	
	
	
	private int getClosestMagicSquare(int[][] m) {
	  
	  int minDist = Integer.MAX_VALUE;
	  int[][][] magicSquares = createSolutions(m.length);
	  for( int i=0; i < magicSquares.length; i++) {
	    int dist = distance(m, magicSquares[i]);
	    if(minDist > dist)
	      minDist = dist;
	  }
	  
	  return minDist;
	}
	
	
	private int[][][] createSolutions(int n) {

    int [][][] r = new int[8][n][n];
    
    int[][] m = createMagicSquare(n);
    int index = 0;
    r[index++] = m;
    r[index++] = turn(m);
    
    int[][] o = m;
    for(int i=0; i < 3; i++) {
      o = rotate(o);
      r[index++] = o;
      r[index++] = turn(o);
    }

    return r;
  }

	
	private int distance(int[][] a, int[][] b) {
	  if(a.length != b.length || a[0].length != b[0].length)
	    throw new IllegalArgumentException("arrays must have same size");
	    
	  int r = 0;
    for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < a.length; j++) {
        r += Math.abs(a[i][j] - b[i][j]);
      }
    }

    return r;
  }
	
	
	private int[][] copy(int[][] o) {
		int[][] r = new int[o.length][o[0].length];

		for (int i = 0; i < o.length; i++) {
			for (int j = 0; j < o.length; j++) {
				r[i][j] = o[i][j];
			}
		}

		return r;
	}

	private int[][] rotateNtimes(int n, int[][] m) {
		for (int i = 0; i < n; i++) {
			m = rotate(m);
		}
		return m;
	}

	private int[][] rotate(int[][] m) {
		int n = m[0].length;
		int[][] d = copy(m);
		for (int i = 0; i < n / 2; i++) {
			rotateLayer(i, m, d);
		}
		return d;
	}
	
	private int[][] turn(int[][] m) {
    int n = m.length;
    int[][] d = new int[n][n];
    for (int i = 0; i < n ; i++) {
      int k = 0;
      for(int j = n-1; j >=0 ; j--) {
        d[i][k++]=m[i][j];
      }
    }
    return d;
  }

	private int[][] rotateLayer(int layer, int[][] source, int[][] destin) {

		int len = source.length;

		int startIndex = layer;
		int endIndex = len - startIndex - 1;

		for (int i = startIndex, j = endIndex; i <= endIndex; i++, j--) {
			destin[j][startIndex] = source[startIndex][i]; // N -> W
			destin[endIndex][j] = source[j][startIndex]; // W -> S
			destin[i][endIndex] = source[endIndex][j]; // S -> E
			destin[startIndex][i] = source[i][endIndex]; // E -> N
		}

		return destin;
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
			position = nextPosition2(position, r);
			r[position / n][position % n] = ++value;
		} while ((n * n) > value);

		return r;
	}
	
	
	


}
